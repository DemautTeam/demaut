package ch.vd.demaut.rest.services.impl;

import java.io.File;
import java.io.IOException;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import ch.vd.demaut.domain.annexes.AnnexeFK;
import ch.vd.demaut.domain.annexes.AnnexeMetadata;
import ch.vd.demaut.domain.annexes.ContenuAnnexe;
import ch.vd.demaut.domain.annexes.NomFichier;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.rest.commons.json.RestUtils;
import ch.vd.demaut.services.annexes.AnnexesService;

@CrossOriginResourceSharing(allowAllOrigins = true)
@Service("annexeRestImpl")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Path("/annexes")
public class AnnexeRestImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnexeRestImpl.class);

    @Autowired
    private AnnexesService annexesService;

    // permet d'accéder aux propriétés HTTP sans polluer les signatures de méthode...
    // nécessite une scope prototype pour éviter des mélanges de context
    @Context
    private UriInfo uriInfo;

    @Context
    private HttpHeaders httpHeaders;

    @GET
    @Path("/typesList")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesTypesAnnexe() throws Exception {

        LOGGER.info("listerLesTypesAnnexes");

        // Altrenative:
        List<TypeAnnexe> typesAnnexe = buildListeTypesAnnexesSansProgresSOA();

        // Autre altrenative:
        // List<VcType> typesAnnexe = buildListeTypesAnnexesAvecProgresSOA(uriInfo);

        return RestUtils.buildRef(typesAnnexe);
    }

    /**
     * Méthode qui renvoie la liste des types d'annexe sans passer par le WS
     * ProgresSOA
     */
    private List<TypeAnnexe> buildListeTypesAnnexesSansProgresSOA() {
        return Arrays.asList(TypeAnnexe.values());
    }

    @GET
    @Path("/typesObligatoiresList")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesTypeAnnexesObligatoires(@QueryParam("referenceDeDemande") String referenceDeDemandeStr) throws Exception {

        Login login = new Login(RestUtils.fetchCurrentUserToken(httpHeaders));

        LOGGER.info("listerLesTypeAnnexesObligatoires pour : " + login.getValue()+ ", referenceDeDemande=" + referenceDeDemandeStr);

        ReferenceDeDemande referenceDeDemande = new ReferenceDeDemande(referenceDeDemandeStr);

        Collection<TypeAnnexe> typeAnnexes = annexesService.listerLesTypeAnnexesObligatoires(login, referenceDeDemande);
        return RestUtils.buildRef(typeAnnexes);
    }

    @GET
    @Path("/annexesSaisis")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesAnnexes(@QueryParam("referenceDeDemande") String referenceDeDemandeStr) throws JsonProcessingException {

        Login login = new Login(RestUtils.fetchCurrentUserToken(httpHeaders));

        LOGGER.info("listerLesAnnexes pour : " + login.getValue() + ", referenceDeDemande=" + referenceDeDemandeStr);

        ReferenceDeDemande referenceDeDemande = new ReferenceDeDemande(referenceDeDemandeStr);

        Collection<AnnexeMetadata> annexesMetadata = annexesService.listerLesAnnexeMetadatas(login, referenceDeDemande);

        return RestUtils.buildJSon(annexesMetadata);
    }

    @GET
    @Path("/afficher")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @RolesAllowed("USER")
    public Response afficherUneAnnexe(@QueryParam("referenceDeDemande") String referenceDeDemandeStr,
                                      @QueryParam("annexeFileName") String annexeFileName,
                                      @QueryParam("annexeType") String annexeTypeIdStr) throws JsonProcessingException {

        Login login = new Login(RestUtils.fetchCurrentUserToken(httpHeaders));

        LOGGER.info("afficherUneAnnexe pour : " + login.getValue() + ", referenceDeDemande=" + referenceDeDemandeStr + ", annexeFileName=" + annexeFileName + ", annexeTypeIdStr=" + annexeTypeIdStr);

        ReferenceDeDemande referenceDeDemande = new ReferenceDeDemande(referenceDeDemandeStr);
        AnnexeFK annexeFK = buildAnnexeFK(annexeFileName, annexeTypeIdStr);

        ContenuAnnexe contenuAnnexe = annexesService.recupererContenuAnnexe(login, referenceDeDemande, annexeFK);
        return RestUtils.BuildStream(contenuAnnexe.getContenu());
    }

    @POST
    @Path("/attacher")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response attacherUneAnnexe(@Multipart("referenceDeDemande") String referenceDeDemandeStr,
                                      @Multipart("annexeFile") File file,
                                      @Multipart("annexeFileName") String annexeFileName,
                                      @Multipart("annexeFileSize") String annexeFileSize,
                                      @Multipart("annexeFileType") String annexeFileType,
                                      @Multipart("annexeType") String annexeTypeIdStr) throws IOException {

        Login login = new Login(RestUtils.fetchCurrentUserToken(httpHeaders));

        LOGGER.info("attacherUneAnnexe pour : " + login.getValue() + ", referenceDeDemande=" + referenceDeDemandeStr + ", annexeFileName=" + annexeFileName + ", annexeTypeIdStr=" + annexeTypeIdStr);

        ReferenceDeDemande referenceDeDemande = new ReferenceDeDemande(referenceDeDemandeStr);
        AnnexeFK annexeFK = buildAnnexeFK(annexeFileName, annexeTypeIdStr);

        annexesService.attacherUneAnnexe(login, referenceDeDemande, file, annexeFK.getNomFichier());
        return RestUtils.buildJSon(true);
    }

    @GET
    @Path("/supprimer")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response supprimerUneAnnexe(@QueryParam("referenceDeDemande") String referenceDeDemandeStr,
                                       @QueryParam("annexeFileName") String annexeFileName,
                                       @QueryParam("annexeType") String annexeTypeIdStr) throws JsonProcessingException {

        Login login = new Login(RestUtils.fetchCurrentUserToken(httpHeaders));

        LOGGER.info("supprimerUneAnnexe pour : " + login.getValue() + ", referenceDeDemande=" + referenceDeDemandeStr + ", annexeFileName=" + annexeFileName + ", annexeTypeIdStr=" + annexeTypeIdStr);

        ReferenceDeDemande referenceDeDemande = new ReferenceDeDemande(referenceDeDemandeStr);
        AnnexeFK annexeFK = buildAnnexeFK(annexeFileName, annexeTypeIdStr);

        annexesService.supprimerUneAnnexe(login, referenceDeDemande, annexeFK);
        return RestUtils.buildJSon(true);
    }

    private AnnexeFK buildAnnexeFK(String annexeFileName, String annexeTypeIdStr) {
        NomFichier nomFichier = new NomFichier(annexeFileName);
        return new AnnexeFK(nomFichier);
    }
}