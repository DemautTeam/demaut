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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import ch.vd.demaut.domain.annexes.*;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.microbiz.progreSoa.ProgreSoaService;
import ch.vd.demaut.microbiz.rest.RestUtils;
import ch.vd.demaut.services.annexes.AnnexesService;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
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

import com.fasterxml.jackson.core.JsonProcessingException;

import ch.vd.demaut.domain.annexes.AnnexeFK;
import ch.vd.demaut.domain.annexes.AnnexeMetadata;
import ch.vd.demaut.domain.annexes.ContenuAnnexe;
import ch.vd.demaut.domain.annexes.NomFichier;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.progreSoa.services.ProgreSoaService;
import ch.vd.demaut.rest.commons.json.RestUtils;
import ch.vd.demaut.services.annexes.AnnexesService;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
import ch.vd.ses.referentiel.demaut_1_0.VcType;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@CrossOriginResourceSharing(allowAllOrigins = true)
@Service("annexeRestImpl")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Path("/annexes")
public class AnnexeRestImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnexeRestImpl.class);

    @Autowired
    private DemandeAutorisationService demandeAutorisationService;

    @Autowired
    private AnnexesService annexesService;

    @Autowired
    private ProgreSoaService progreSoaService;

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
    @Path("/lister")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesAnnexes() throws JsonProcessingException {

        Login login = new Login(RestUtils.fetchCurrentUserToken(httpHeaders));

        LOGGER.info("listerLesAnnexes pour : " + login.getValue());

        Collection<AnnexeMetadata> annexesMetadata = annexesService.listerLesAnnexeMetadatas(login);

        return RestUtils.buildJSon(annexesMetadata);
    }

    // Methode avec @PathParam doit être inchangée
    @GET
    @Path("/afficher")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @RolesAllowed("USER")
    public Response afficherUneAnnexe(@QueryParam("annexeFileName") String annexeFileName,
                                      @QueryParam("annexeType") String annexeTypeIdStr)
            throws JsonProcessingException {

        Login login = new Login(RestUtils.fetchCurrentUserToken(httpHeaders));

        LOGGER.info("afficherUneAnnexe pour : " + login.getValue() + ", annexeFileName=" + annexeFileName + ", annexeTypeIdStr=" + annexeTypeIdStr);

        AnnexeFK annexeFK = buildAnnexeFK(annexeFileName, annexeTypeIdStr);

        ContenuAnnexe contenuAnnexe = annexesService.recupererContenuAnnexe(login, annexeFK);
        return RestUtils.BuildStream(contenuAnnexe.getContenu());
    }

    @POST
    @Path("/attacher")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response attacherUneAnnexe(@Multipart("annexeFile") File file,
                                      @Multipart("annexeFileName") String annexeFileName,
                                      @Multipart("annexeFileSize") String annexeFileSize,
                                      @Multipart("annexeFileType") String annexeFileType,
                                      @Multipart("annexeType") String annexeTypeIdStr) throws IOException {

        Login login = new Login(RestUtils.fetchCurrentUserToken(httpHeaders));

        LOGGER.info("attacherUneAnnexe pour : " + login.getValue() + ", annexeFileName=" + annexeFileName + ", annexeTypeIdStr=" + annexeTypeIdStr);

        AnnexeFK annexeFK = buildAnnexeFK(annexeFileName, annexeTypeIdStr);

        annexesService.attacherUneAnnexe(login, file, annexeFK.getNomFichier(), annexeFK.getTypeAnnexe());
        return RestUtils.buildJSon(Arrays.asList(true));
    }

    @GET
    @Path("/supprimer")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response supprimerUneAnnexe(@QueryParam("annexeFileName") String annexeFileName,
                                       @QueryParam("annexeType") String annexeTypeIdStr) throws JsonProcessingException {

        Login login = new Login(RestUtils.fetchCurrentUserToken(httpHeaders));

        LOGGER.info("supprimerUneAnnexe pour : " + login.getValue() + ", annexeFileName=" + annexeFileName + ", annexeTypeIdStr=" + annexeTypeIdStr);

        AnnexeFK annexeFK = buildAnnexeFK(annexeFileName, annexeTypeIdStr);

        annexesService.supprimerUneAnnexe(login, annexeFK);
        return RestUtils.buildJSon(Arrays.asList(true));
    }

    private AnnexeFK buildAnnexeFK(String annexeFileName, String annexeTypeIdStr) {
        NomFichier nomFichier = new NomFichier(annexeFileName);
        Integer annexeTypeId = Integer.valueOf(annexeTypeIdStr);
        TypeAnnexe typeAnnexe = TypeAnnexe.getTypeById(annexeTypeId);
        return new AnnexeFK(nomFichier, typeAnnexe);
    }
}