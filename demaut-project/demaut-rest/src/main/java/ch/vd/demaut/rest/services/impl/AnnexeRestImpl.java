package ch.vd.demaut.rest.services.impl;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.vd.demaut.domain.annexes.AnnexeFK;
import ch.vd.demaut.domain.annexes.AnnexeMetadata;
import ch.vd.demaut.domain.annexes.ContenuAnnexe;
import ch.vd.demaut.domain.annexes.NomFichier;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.rest.json.commons.RestUtils;
import ch.vd.demaut.rest.services.AbstractRestService;
import ch.vd.demaut.services.annexes.AnnexesService;

@CrossOriginResourceSharing(allowAllOrigins = true)
@Path("/annexes")
public class AnnexeRestImpl extends AbstractRestService {

    private static final Logger logger = LoggerFactory.getLogger(AnnexeRestImpl.class);

    private AnnexesService annexesService;

    public void setAnnexesService(AnnexesService annexesService) {
        this.annexesService = annexesService;
    }

    @GET
    @Path("/typesList")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesTypesAnnexe() throws Exception {

        logger.info("listerLesTypesAnnexes");

        // Altrenative:
        List<TypeAnnexe> typesAnnexe = buildListeTypesAnnexesSansProgresSOA();

        // Autre altrenative:
        // List<VcType> typesAnnexe = buildListeTypesAnnexesAvecProgresSOA(uriInfo);

        return RestUtils.buildJSonResponse(typesAnnexe);
    }

    @GET
    @Path("/typesObligatoiresList")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesTypeAnnexesObligatoires(@QueryParam("referenceDeDemande") String referenceDeDemandeStr) {

        Login login = getLogin();

        logger.info("listerLesTypeAnnexesObligatoires pour : " + login.getValue() + ", referenceDeDemande="
                + referenceDeDemandeStr);

        ReferenceDeDemande referenceDeDemande = new ReferenceDeDemande(referenceDeDemandeStr);

        Collection<TypeAnnexe> typeAnnexes = annexesService.listerLesTypeAnnexesObligatoires(referenceDeDemande);
        return RestUtils.buildJSonResponse(typeAnnexes);
    }

    @GET
    @Path("/annexesSaisis")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesAnnexes(@QueryParam("referenceDeDemande") String referenceDeDemandeStr) {

        Login login = getLogin();

        logger.info("listerLesAnnexes pour : " + login.getValue() + ", referenceDeDemande=" + referenceDeDemandeStr);

        ReferenceDeDemande referenceDeDemande = new ReferenceDeDemande(referenceDeDemandeStr);

        Collection<AnnexeMetadata> annexesMetadata = annexesService.listerLesAnnexeMetadatas(referenceDeDemande);

        return RestUtils.buildJSonResponse(annexesMetadata);
    }

    @GET
    @Path("/afficher")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @RolesAllowed("USER")
    public Response afficherUneAnnexe(@QueryParam("referenceDeDemande") String referenceDeDemandeStr,
            @QueryParam("annexeFileName") String annexeFileName) {

        Login login = getLogin();

        logger.info("afficherUneAnnexe pour : " + login.getValue() + ", referenceDeDemande=" + referenceDeDemandeStr
                + ", annexeFileName=" + annexeFileName);

        ReferenceDeDemande referenceDeDemande = new ReferenceDeDemande(referenceDeDemandeStr);
        AnnexeFK annexeFK = buildAnnexeFK(annexeFileName);

        ContenuAnnexe contenuAnnexe = annexesService.recupererContenuAnnexe(referenceDeDemande, annexeFK);
        return RestUtils.BuildStream(contenuAnnexe.getContenu());
    }

    @POST
    @Path("/attacher")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response attacherUneAnnexe(@Multipart("referenceDeDemande") String referenceDeDemandeStr,
            @Multipart("annexeFile") File file, @Multipart("annexeFileName") String annexeFileName,
            @Multipart("annexeFileSize") String annexeFileSize, @Multipart("annexeFileType") String annexeFileType) {

        Login login = getLogin();

        logger.info("attacherUneAnnexe pour : " + login.getValue() + ", referenceDeDemande=" + referenceDeDemandeStr
                + ", annexeFileName=" + annexeFileName);

        ReferenceDeDemande referenceDeDemande = new ReferenceDeDemande(referenceDeDemandeStr);
        AnnexeFK annexeFK = buildAnnexeFK(annexeFileName);

        annexesService.attacherUneAnnexe(referenceDeDemande, file, annexeFK.getNomFichier());
        return RestUtils.buildJSonResponse(true);
    }

    @GET
    @Path("/supprimer")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response supprimerUneAnnexe(@QueryParam("referenceDeDemande") String referenceDeDemandeStr,
            @QueryParam("annexeFileName") String annexeFileName) {

        Login login = getLogin();

        logger.info("supprimerUneAnnexe pour : " + login.getValue() + ", referenceDeDemande=" + referenceDeDemandeStr
                + ", annexeFileName=" + annexeFileName);

        ReferenceDeDemande referenceDeDemande = new ReferenceDeDemande(referenceDeDemandeStr);
        AnnexeFK annexeFK = buildAnnexeFK(annexeFileName);

        annexesService.supprimerUneAnnexe(referenceDeDemande, annexeFK);
        return RestUtils.buildJSonResponse(true);
    }

    // ********************************************************* Methodes privees

    /**
     * Méthode qui renvoie la liste des types d'annexe sans passer par le WS ProgresSOA
     */
    private List<TypeAnnexe> buildListeTypesAnnexesSansProgresSOA() {
        return Arrays.asList(TypeAnnexe.values());
    }

    private AnnexeFK buildAnnexeFK(String annexeFileName) {
        NomFichier nomFichier = new NomFichier(annexeFileName);
        return new AnnexeFK(nomFichier);
    }
}