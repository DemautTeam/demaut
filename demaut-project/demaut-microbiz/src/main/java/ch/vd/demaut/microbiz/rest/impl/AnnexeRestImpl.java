package ch.vd.demaut.microbiz.rest.impl;

import ch.vd.demaut.domain.annexes.AnnexeMetadata;
import ch.vd.demaut.domain.annexes.ContenuAnnexe;
import ch.vd.demaut.domain.annexes.NomFichier;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
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
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
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

@CrossOriginResourceSharing(allowOrigins = {"*"}, allowCredentials = true, maxAge = 3600, allowHeaders = {
        "Content-Type", "X-Requested-With"}, exposeHeaders = {"Access-Control-Allow-Origin"})
@Service("annexeRestImpl")
@Path("/annexes")
public class AnnexeRestImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnexeRestImpl.class);

    @Inject
    private AnnexesService annexesService;

    @Inject
    private ProgreSoaService progreSoaService;

    @GET
    @Path("/typesList")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesTypesAnnexe()
            throws Exception {

        LOGGER.info(">>> listerLesTypesAnnexe");

        // Altrenative:
        List<TypeAnnexe> typesAnnexe = buildListeTypesAnnexesSansProgresSOA();
        
        // Autre altrenative:
        //List<VcType> typesAnnexe = buildListeTypesAnnexesAvecProgresSOA(uriInfo);
        
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
    public Response listerLesAnnexes(@Context UriInfo uriInfo, @PathParam("demandeReference") String demandeReference)
            throws JsonProcessingException {

        LOGGER.info("listerLesAnnexes pour la demandeRef: " + demandeReference);

        ReferenceDeDemande referenceDeDemande = new ReferenceDeDemande(demandeReference);

        Collection<AnnexeMetadata> annexesMetadata = annexesService.listerLesAnnexeMetadatas(referenceDeDemande);
        
        return RestUtils.forgeResponseList(annexesMetadata);
    }

    @GET
    @Path("/afficher/{demandeReference}/{annexeFileName}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @RolesAllowed("USER")
    public Response afficherUneAnnexe(@Context UriInfo uriInfo, @PathParam("demandeReference") String demandeReference,
                                      @PathParam("annexeFileName") String annexeFileName) throws JsonProcessingException {

        LOGGER.info("afficherUneAnnexe " + annexeFileName);

        ReferenceDeDemande referenceDeDemande = new ReferenceDeDemande(demandeReference);
        NomFichier nomFichier = new NomFichier(annexeFileName);

        ContenuAnnexe contenuAnnexe = annexesService.recupererContenuAnnexe(referenceDeDemande, nomFichier);
        return RestUtils.forgeResponseStream(contenuAnnexe.getContenu());
    }

    @POST
    @Path("/attacher")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response attacherUneAnnexe(@Context UriInfo uriInfo, @Multipart("demandeReference") String demandeReference,
                                      @Multipart("annexeFile") File file, @Multipart("annexeFileName") String annexeFileName,
                                      @Multipart("annexeFileSize") String annexeFileSize, @Multipart("annexeFileType") String annexeFileType,
                                      @Multipart("annexeType") String annexeTypeIdStr) throws IOException {

        LOGGER.info("attacherUneAnnexe " + annexeFileName);

        ReferenceDeDemande referenceDeDemande = new ReferenceDeDemande(demandeReference);
        NomFichier nomFichier = new NomFichier(annexeFileName);
        Integer annexeTypeId = Integer.valueOf(annexeTypeIdStr);
        TypeAnnexe typeAnnexe = TypeAnnexe.getTypeById(annexeTypeId);

        annexesService.attacherUneAnnexe(referenceDeDemande, file, nomFichier, typeAnnexe);
        return RestUtils.forgeResponseTrue();
    }

    @GET
    @Path("/supprimer/{demandeReference}/{annexeFileName}/{annexeType}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response supprimerUneAnnexe(@Context UriInfo uriInfo, @PathParam("demandeReference") String demandeReference,
                                       @PathParam("annexeFileName") String annexeFileName, @PathParam("annexeType") String annexeType)
            throws JsonProcessingException {

        LOGGER.info("supprimerUneAnnexe " + annexeFileName);

        ReferenceDeDemande referenceDeDemande = new ReferenceDeDemande(demandeReference);
        NomFichier nomFichier = new NomFichier(annexeFileName);

        annexesService.supprimerUneAnnexe(referenceDeDemande, nomFichier);
        return RestUtils.forgeResponseTrue();
    }
}