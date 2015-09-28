package ch.vd.demaut.microbiz.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/diplomes")
public interface DiplomeRest {

    @GET
    @Path("/typeDiplomesList")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    Response listerLesTypesDeDiplomes(UriInfo uriInfo) throws Exception;

    @GET
    @Path("/typesList/{profession}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    Response listerLesTitresFormations(@Context UriInfo uriInfo, @PathParam("typeDiplome") String typeDiplome) throws Exception;

    @GET
    @Path("/paysList")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    Response listerLesPays(@Context UriInfo uriInfo) throws Exception;
}
