package ch.vd.pee.microbiz.poc.microbiz;

import ch.vd.pee.microbiz.poc.jpa.entity.Annexe;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/services")
public interface AnnexeRest {

    @GET
    @Path("/main")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    Response mainData();

    @GET
    @Path("/annexes/all")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    Response fetchAnnexes() throws JsonProcessingException;

    @GET
    @Path("/annexe/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    Response fetchAnnexeByName(@PathParam("name") String name) throws JsonProcessingException;

    @GET
    @Path("/annexe/binary/{name}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @RolesAllowed("USER")
    Response fetchAnnexeBinary(@PathParam("name") String name);

    @POST
    @Path("/annexe/store")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    Response storeAnnexe(Annexe annexe);
}
