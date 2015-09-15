package ch.vd.demaut.microbiz.rest;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import ch.vd.demaut.domain.annexes.AnnexeMetadata;
import ch.vd.demaut.domain.annexes.ContenuAnnexe;
import ch.vd.demaut.domain.annexes.NomFichier;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.microbiz.progreSoa.PorgreSoaService;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
import ch.vd.ses.referentiel.demaut_1_0.RefListType;

@CrossOriginResourceSharing(
        allowOrigins = {"*"},
        allowCredentials = true,
        maxAge = 3600,
        allowHeaders = {"Content-Type", "X-Requested-With"},
        exposeHeaders = {"Access-Control-Allow-Origin"}
)
@Service
@Path("/annexes")
public class AnnexeRestImpl implements AnnexeRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnexeRestImpl.class);

    @Autowired
    private DemandeAutorisationService demandeAutorisationService;

    @Autowired
    private PorgreSoaService porgreSoaService;

    // TODO Processor Camel
    @Value("${user}")
    private String user;

    @Override
    @GET
    @Path("/typesList/{profession}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesTypesAnnexes(@PathParam("profession") String profession) throws Exception {

        LOGGER.info("listerLesTypesAnnexes " + profession);

        List<RefListType> lesTypesAnnexes = porgreSoaService.listeSOATypesAnnexes().getRefList().getRefListType();
        // TODO filtrer la liste selon profession (liste ordinaire VS simplifiée)
        return RestUtils.forgeResponseList(Response.Status.OK, lesTypesAnnexes);
    }

    @Override
    @GET
    @Path("/lister/{demandeReference}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesAnnexes(@PathParam("demandeReference") String ref) throws JsonProcessingException {

        LOGGER.info("listerLesAnnexes " + ref);
        
        ReferenceDeDemande referenceDeDemande = new ReferenceDeDemande(ref);
        
        Collection<AnnexeMetadata> annexesMetadata = demandeAutorisationService.listerLesAnnexesMetadatas(referenceDeDemande);
        
        return RestUtils.forgeResponseList(Response.Status.OK, annexesMetadata);
    }

    @Override
    @GET
    @Path("/afficher/{demandeReference}/{annexeFileName}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @RolesAllowed("USER")
    public Response afficherUneAnnexe(@PathParam("demandeReference") String demandeReference,
                                      @PathParam("annexeFileName") String annexeFileName) throws JsonProcessingException {

        LOGGER.info("afficherUneAnnexe " + annexeFileName);

        ReferenceDeDemande ref = new ReferenceDeDemande(demandeReference);
        NomFichier nomFichier = new NomFichier(annexeFileName);

        ContenuAnnexe contenuAnnexe = demandeAutorisationService.recupererContenuAnnexe(ref, nomFichier);
        
        Response response = RestUtils.forgeResponseStream(Response.Status.OK, contenuAnnexe.getContenu());
        
        return response;

    }

    @Override
    @POST
    @Path("/attacher")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response attacherUneAnnexe(@Multipart("demandeReference") String demandeReference,
                                      @Multipart("annexeFile") File file,
                                      @Multipart("annexeFileName") String annexeFileName,
                                      @Multipart("annexeFileSize") String annexeFileSize,
                                      @Multipart("annexeFileType") String annexeFileType,
                                      @Multipart("annexeType") String annexeType) throws IOException {

        LOGGER.info("attacherUneAnnexe " + annexeFileName);
        
        ReferenceDeDemande ref = new ReferenceDeDemande(demandeReference);
        NomFichier nomFichier = new NomFichier(annexeFileName);
        TypeAnnexe typeAnnexe = TypeAnnexe.valueOf(annexeType);
        
        demandeAutorisationService.attacherUneAnnexe(ref, file, nomFichier, typeAnnexe);
        
        Response response = RestUtils.forgeResponseTrue();
        
        return response;
    }

    @Override
    @GET
    @Path("/supprimer/{demandeReference}/{annexeFileName}/{annexeType}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response supprimerUneAnnexe(@PathParam("demandeReference") String demandeReference,
                                       @PathParam("annexeFileName") String annexeFileName,
                                       @PathParam("annexeType") String annexeType) throws JsonProcessingException {

        LOGGER.info("supprimerUneAnnexe " + annexeFileName);

        ReferenceDeDemande ref = new ReferenceDeDemande(demandeReference);
        NomFichier nomFichier = new NomFichier(annexeFileName);

        demandeAutorisationService.supprimerUneAnnexe(ref, nomFichier);
        
        Response response = RestUtils.forgeResponseTrue();
        
        return response;
    }
    
}
