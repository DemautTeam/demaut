package ch.vd.demaut.poc.microbiz;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/camel")
public interface MainRest {

    @GET
    @Path("/main")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response mainData();
}
