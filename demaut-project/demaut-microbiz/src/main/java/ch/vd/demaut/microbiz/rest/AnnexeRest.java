package ch.vd.demaut.microbiz.rest;

import com.fasterxml.jackson.core.JsonProcessingException;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;

@Path("/annexes")
public interface AnnexeRest {

    @GET
    @Path("/typesList/{profession}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    Response listerLesTypesAnnexes(String professionId) throws Exception;

    @GET
    @Path("/lister/{demandeReference}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    Response listerLesAnnexes(String demandeReference) throws JsonProcessingException;

    @GET
    @Path("/afficher/{demandeReference}/{annexeFileName}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @RolesAllowed("USER")
    Response afficherUneAnnexe(String demandeReference, String annexeFileName) throws JsonProcessingException;

    @POST
    @Path("/attacher")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    Response attacherUneAnnexe(String demandeReference, File file, String annexeFileName, String annexeFileSize, String annexeFileType, String annexeType) throws IOException;

    @GET
    @Path("/supprimer/{demandeReference}/{annexeFileName}/{annexeType}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    Response supprimerUneAnnexe(String demandeReference, String annexeFileName, String annexeType) throws JsonProcessingException;
}
