package ch.vd.demaut.microbiz.rest.impl;

import ch.vd.demaut.domain.annexes.*;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.microbiz.progreSoa.ProgreSoaService;
import ch.vd.demaut.microbiz.rest.RestUtils;
import ch.vd.demaut.services.annexes.AnnexesService;
import ch.vd.ses.referentiel.demaut_1_0.VcType;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@CrossOriginResourceSharing(allowAllOrigins = true)
@Service("annexeRestImpl")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Path("/annexes")
public class AnnexeRestImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnexeRestImpl.class);

    // permet d'accéder aux propriétés HTTP sans polluer les signatures de méthode...
    // nécessite une scope prototype pour éviter des mélanges de context
    @Context
    private UriInfo uriInfo;

    @Autowired
    private AnnexesService annexesService;

    @Autowired
    private ProgreSoaService progreSoaService;

    @GET
    @Path("/typesList")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesTypesAnnexe() throws Exception {

        LOGGER.info("listerLesTypesAnnexes");

        // Altrenative:
        List<TypeAnnexe> typesAnnexe = buildListeTypesAnnexesSansProgresSOA();

        // Autre altrenative:
        // List<VcType> typesAnnexe =
        // buildListeTypesAnnexesAvecProgresSOA(uriInfo);

        return RestUtils.forgeResponseList(typesAnnexe);
    }

    /**
     * Méthode qui renvoie la liste des types d'annexe sans passer par le WS
     * ProgresSOA
     */
    private List<TypeAnnexe> buildListeTypesAnnexesSansProgresSOA() {
        return Arrays.asList(TypeAnnexe.values());
    }

    /**
     * Cette méthode sera peut-etre utilisé suivant les futures décisions prises
     * TODO: Quoiqu'il en soit virer progreSoaService du projet microbiz et le
     * mettre dans Service (qui correspond a la facade REST)
     *
     * @throws Exception
     */
    @SuppressWarnings("unused")
    private List<VcType> buildListeTypesAnnexesAvecProgresSOA(UriInfo uriInfo) throws Exception {
        String path = uriInfo != null ? uriInfo.getBaseUri().toString() : null;
        return progreSoaService.listeSOATypesAnnexes(path).getVcList().getVc();
    }

    @GET
    @Path("/lister/{demandeReference}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesAnnexes(@PathParam("demandeReference") String demandeReference)
            throws JsonProcessingException {

        LOGGER.info("listerLesAnnexes pour la demandeRef: " + demandeReference);

        ReferenceDeDemande referenceDeDemande = new ReferenceDeDemande(demandeReference);

        Collection<AnnexeMetadata> annexesMetadata = annexesService.listerLesAnnexeMetadatas(referenceDeDemande);

        return RestUtils.forgeResponseList(annexesMetadata);
    }

    @GET
    @Path("/afficher/{demandeReference}/{annexeFileName}/{annexeType}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @RolesAllowed("USER")
    public Response afficherUneAnnexe( @PathParam("demandeReference") String demandeReference,
                                      @PathParam("annexeFileName") String annexeFileName, @PathParam("annexeType") String annexeTypeIdStr)
            throws JsonProcessingException {

        LOGGER.info("afficherUneAnnexe:demandeReference=" + demandeReference + ", annexeFileName=" + annexeFileName + ", annexeTypeIdStr=" + annexeTypeIdStr);

        //TODO: factoriser dans une methode privée
        ReferenceDeDemande referenceDeDemande = new ReferenceDeDemande(demandeReference);
        AnnexeFK annexeFK = buildAnnexeFK(annexeFileName, annexeTypeIdStr);

        ContenuAnnexe contenuAnnexe = annexesService.recupererContenuAnnexe(referenceDeDemande, annexeFK);
        return RestUtils.forgeResponseStream(contenuAnnexe.getContenu());
    }

    @POST
    @Path("/attacher")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response attacherUneAnnexe(@Multipart("demandeReference") String demandeReference,
                                      @Multipart("annexeFile") File file, @Multipart("annexeFileName") String annexeFileName,
                                      @Multipart("annexeFileSize") String annexeFileSize, @Multipart("annexeFileType") String annexeFileType,
                                      @Multipart("annexeType") String annexeTypeIdStr) throws IOException {

        LOGGER.info("attacherUneAnnexe " + annexeFileName);

        ReferenceDeDemande referenceDeDemande = new ReferenceDeDemande(demandeReference);
        AnnexeFK annexeFK = buildAnnexeFK(annexeFileName, annexeTypeIdStr);

        annexesService.attacherUneAnnexe(referenceDeDemande, file, annexeFK.getNomFichier(), annexeFK.getTypeAnnexe());

        return RestUtils.forgeResponseTrue();
    }

    @GET
    @Path("/supprimer/{demandeReference}/{annexeFileName}/{annexeType}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response supprimerUneAnnexe(@PathParam("demandeReference") String demandeReference,
                                       @PathParam("annexeFileName") String annexeFileName, @PathParam("annexeType") String annexeTypeIdStr)
            throws JsonProcessingException {

        LOGGER.info("supprimerUneAnnexe " + annexeFileName);

        ReferenceDeDemande referenceDeDemande = new ReferenceDeDemande(demandeReference);
        AnnexeFK annexeFK = buildAnnexeFK(annexeFileName, annexeTypeIdStr);

        annexesService.supprimerUneAnnexe(referenceDeDemande, annexeFK);

        return RestUtils.forgeResponseTrue();
    }

    private AnnexeFK buildAnnexeFK(String annexeFileName, String annexeTypeIdStr) {
        NomFichier nomFichier = new NomFichier(annexeFileName);
        Integer annexeTypeId = Integer.valueOf(annexeTypeIdStr);
        TypeAnnexe typeAnnexe = TypeAnnexe.getTypeById(annexeTypeId);
        return new AnnexeFK(nomFichier, typeAnnexe);
    }

}