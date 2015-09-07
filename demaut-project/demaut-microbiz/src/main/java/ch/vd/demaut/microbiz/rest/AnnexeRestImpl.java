package ch.vd.demaut.microbiz.rest;

import java.io.File;
import java.io.IOException;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
import ch.vd.pee.microbiz.core.utils.Json;

@Service
@Path("/services")
public class AnnexeRestImpl implements AnnexeRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnexeRestImpl.class);

    private static final ObjectWriter viewWriter = new ObjectMapper().writer();

    @SuppressWarnings("unused")
	@Autowired
    private DemandeAutorisationService demandeAutorisationService;

    // TODO Processor Camel
    @Value("${user}")
    private String user;

    @Override
    @GET
    @Path("/annexes/lister/{demandeReference}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesAnnexes(@PathParam("demandeReference") String demandeReference) throws JsonProcessingException {

        LOGGER.info("listerLesAnnexes " + demandeReference);

        ObjectWriter viewWriter = new ObjectMapper().writer();
        // TODO implement lister annexes
        return Response.ok(Json.newObject().put("response", viewWriter.writeValueAsString(demandeReference))).build();
    }

    @Override
    @GET
    @Path("/annexes/affichier/{demandeReference}/{annexeFileName}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @RolesAllowed("USER")
    public Response affichierUneAnnexe(@PathParam("demandeReference") String demandeReference,
                                       @PathParam("annexeFileName") String annexeFileName) {

        LOGGER.info("affichierUneAnnexe " + annexeFileName);

        return !StringUtils.isEmpty(demandeReference) && !StringUtils.isEmpty(annexeFileName)
                // TODO implement affichier annexe
                //&& this.demandeAutorisationService.affichierUneAnnexe(new Annexe(TypeAnnexe.CV, name, IOUtils.toByteArray(new FileInputStream(file))))
                ? Response.ok("annexeFileName".getBytes(), MediaType.APPLICATION_OCTET_STREAM_TYPE).build()
                : Response.noContent().build();
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

        return !StringUtils.isEmpty(demandeReference) && !StringUtils.isEmpty(annexeFileName) &&
                !StringUtils.isEmpty(annexeFileSize) && !StringUtils.isEmpty(annexeFileType) &&
                !StringUtils.isEmpty(annexeType) && file != null
                // TODO implement attacher annexe
                //&& this.demandeAutorisationService.attacherUneAnnexe(new Annexe(TypeAnnexe.CV, name, IOUtils.toByteArray(new FileInputStream(file))))
                ? Response.ok(Json.newObject().put("response", viewWriter.writeValueAsString(annexeFileName))).build()
                : Response.notModified().build();
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

        return !StringUtils.isEmpty(demandeReference) && !StringUtils.isEmpty(annexeFileName) && !StringUtils.isEmpty(annexeType)
                // TODO implement supprimer annexe
                //&& this.demandeAutorisationService.supprimerUneAnnexe(new Annexe(TypeAnnexe.CV, name, IOUtils.toByteArray(new FileInputStream(file))))
                ? Response.ok(Json.newObject().put("response", viewWriter.writeValueAsString(annexeFileName))).build()
                : Response.noContent().build();
    }

}
