package ch.vd.demaut.microbiz.rest;

import com.fasterxml.jackson.core.JsonProcessingException;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;

@Path("/services")
public interface AnnexeRest {

    @GET
    @Path("/annexes/all")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    String fetchAnnexes() throws JsonProcessingException;

    @POST
    @Path("/annexe/multipart")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    Response storeAnnexe(String demandeReference, File file, String annexeFileName, String annexeFileSize, String annexeFileType, String annexeType) throws IOException;

    @GET
    @Path("/annexe/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    Response deleteAnnexe(String demandeReference, String annexeFileName, String annexeType) throws JsonProcessingException;
}
