package ch.vd.pee.microbiz.poc.microbiz;

import ch.vd.pee.microbiz.core.utils.Json;
import ch.vd.pee.microbiz.poc.domain.AnnexeService;
import ch.vd.pee.microbiz.poc.jpa.entity.Annexe;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Service
@Path("/services")
public class AnnexeRestImpl implements AnnexeRest {

    @Autowired
    private AnnexeService annexeService;

    @Override
    @GET
    @Path("/main")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response mainData() {
        return Response.ok(Json.newObject().put("response", "mainData")).build();
    }

    @Override
    @GET
    @Path("/annexes/all")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response fetchAnnexes() throws JsonProcessingException {
        ObjectWriter viewWriter = new ObjectMapper().writer();
        List<Annexe> annexes = this.annexeService.fetchAnnexes();
        return annexes != null && !annexes.isEmpty()
                ? Response.ok(Json.newObject().put("response", viewWriter.writeValueAsString(annexes))).build()
                : Response.noContent().build();
    }

    @Override
    @GET
    @Path("/annexe/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response fetchAnnexeByName(@PathParam("name") String name) throws JsonProcessingException {
        ObjectWriter viewWriter = new ObjectMapper().writer();
        Annexe annexe = this.annexeService.fetchAnnexeByName(name);
        return annexe != null
                ? Response.ok(Json.newObject().put("response", viewWriter.writeValueAsString(annexe))).build()
                : Response.noContent().build();
    }

    @Override
    @GET
    @Path("/annexe/binary/{name}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @RolesAllowed("USER")
    public Response fetchAnnexeBinary(@PathParam("name") String name) {
        Annexe annexe = this.annexeService.fetchAnnexeByName(name);
        return annexe != null
                ? Response.ok(annexe.getFile(), MediaType.APPLICATION_OCTET_STREAM_TYPE).build()
                : Response.noContent().build();
    }

    @Override
    @POST
    @Path("/annexe/store")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response storeAnnexe(Annexe annexe) {
        return this.annexeService.storeAnnexe(annexe)
                ? Response.ok(Json.newObject().put("response", true)).build()
                : Response.notModified().build();
    }
}
