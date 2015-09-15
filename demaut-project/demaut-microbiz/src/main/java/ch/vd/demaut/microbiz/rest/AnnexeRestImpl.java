package ch.vd.demaut.microbiz.rest;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import ch.vd.demaut.domain.annexes.AnnexeMetadata;
import ch.vd.demaut.domain.annexes.ContenuAnnexe;
import ch.vd.demaut.domain.annexes.NomFichier;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.microbiz.progreSoa.PorgreSoaService;
import ch.vd.demaut.microbiz.progreSoa.RefRoot;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
import ch.vd.pee.microbiz.core.utils.Json;

@Service
@Path("/services")
public class AnnexeRestImpl implements AnnexeRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnexeRestImpl.class);

    private static final ObjectWriter viewWriter = new ObjectMapper().writer();

    @Autowired
    private DemandeAutorisationService demandeAutorisationService;

    @Autowired
    private PorgreSoaService porgreSoaService;

    // TODO Processor Camel
    @Value("${user}")
    private String user;

    @Override
    @GET
    @Path("/annexes/typesList/{profession}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesTypesAnnexes(@PathParam("profession") String profession) throws Exception {

        LOGGER.info("listerLesTypesAnnexes " + profession);

        RefRoot lesTypesAnnexes = porgreSoaService.listeSOATypesAnnexes();
        
        // TODO filtrer la liste selon profession (liste ordinaire VS simplifiée)
        return Response.ok(Json.newObject().put("response", viewWriter.writeValueAsString(lesTypesAnnexes))).build();
    }

    @Override
    @GET
    @Path("/annexes/lister/{demandeReference}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesAnnexes(@PathParam("demandeReference") String ref) throws JsonProcessingException {

        LOGGER.info("listerLesAnnexes " + ref);
        
        ReferenceDeDemande referenceDeDemande = new ReferenceDeDemande(ref);
        
        Collection<AnnexeMetadata> annexesMetadata = demandeAutorisationService.listerLesAnnexesMetadatas(referenceDeDemande);
        
        return Response.ok(Json.newObject().put("response", viewWriter.writeValueAsString(annexesMetadata))).build();
    }

    @Override
    @GET
    @Path("/annexes/afficher/{demandeReference}/{annexeFileName}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @RolesAllowed("USER")
    public Response afficherUneAnnexe(@PathParam("demandeReference") String demandeReference,
                                      @PathParam("annexeFileName") String annexeFileName) {

        LOGGER.info("afficherUneAnnexe " + annexeFileName);

        ReferenceDeDemande ref = new ReferenceDeDemande(demandeReference);
        NomFichier nomFichier = new NomFichier(annexeFileName);

        ContenuAnnexe contenuAnnexe = demandeAutorisationService.recupererContenuAnnexe(ref, nomFichier);
        
        Response response = buildStreamResponse(contenuAnnexe);
        
        return response;
        
    }

	private Response buildStreamResponse(ContenuAnnexe contenuAnnexe) {
		return Response.ok(contenuAnnexe.getContenu(), MediaType.APPLICATION_OCTET_STREAM_TYPE).build();
	}

    @Override
    @POST
    @Path("/annexes/attacher")
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
        
        Response response = buildResponseJsonTrue();
        
        return response;
        
    }

    @Override
    @GET
    @Path("/annexes/supprimer/{demandeReference}/{annexeFileName}/{annexeType}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response supprimerUneAnnexe(@PathParam("demandeReference") String demandeReference,
                                       @PathParam("annexeFileName") String annexeFileName,
                                       @PathParam("annexeType") String annexeType) throws JsonProcessingException {

        LOGGER.info("supprimerUneAnnexe " + annexeFileName);

        ReferenceDeDemande ref = new ReferenceDeDemande(demandeReference);
        NomFichier nomFichier = new NomFichier(annexeFileName);

        demandeAutorisationService.supprimerUneAnnexe(ref, nomFichier);
        
        Response response = buildResponseJsonTrue();
        
        return response;
    }
    
	private Response buildResponseJsonTrue() throws JsonProcessingException {
		Object json =  Json.newObject().put("response", viewWriter.writeValueAsString(true));
        Response response = Response.ok(json).build();
        return response;

	}

}
