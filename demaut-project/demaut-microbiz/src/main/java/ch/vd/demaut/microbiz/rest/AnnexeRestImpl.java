package ch.vd.demaut.microbiz.rest;

import ch.vd.pee.microbiz.core.utils.Json;
import ch.vd.demaut.poc.domain.AnnexeService;
import ch.vd.demaut.poc.jpa.entity.Annexe;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.commons.io.IOUtils;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Service
@Path("/services")
public class AnnexeRestImpl implements AnnexeRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnexeRestImpl.class);

    @Autowired
    private AnnexeService annexeService;

    // TODO Processor Camel
    @Value("${user}")
    private String user;

    @SuppressWarnings("serial")
	@Override
    @GET
    @Path("/main")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response mainData() throws JsonProcessingException {
        // TODO variables à renseigner dans un buildInfo.cfg compilé
        LOGGER.info("mainData");
        ObjectWriter viewWriter = new ObjectMapper().writer();
        return Response.ok(
                Json.newObject().put("main",
                    viewWriter.writeValueAsString(
                        new HashMap<String, String>(){{
                            put("user", user);
                        }}
                    ))
        ).build();
    }

    @Override
    @GET
    @Path("/annexes/all")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response fetchAnnexes() throws JsonProcessingException {

        LOGGER.info("fetchAnnexes");

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

        LOGGER.info("fetchAnnexeByName");

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

        LOGGER.info("fetchAnnexeBinary");

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

        LOGGER.info("storeAnnexe");

        return this.annexeService.storeAnnexe(annexe)
                ? Response.ok(Json.newObject().put("response", true)).build()
                : Response.notModified().build();
    }

    @Override
    @POST
    @Path("/annexe/multipart")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response storeMultipart(@Multipart("name") String name, @Multipart("size") String size, @Multipart("type") String type,
                                   @Multipart(value = "file", type = MediaType.APPLICATION_OCTET_STREAM) File file) throws IOException {

        LOGGER.info("storeMultipart");

        return  !StringUtils.isEmpty(name) && !StringUtils.isEmpty(size) && !StringUtils.isEmpty(type) && file != null &&
                this.annexeService.storeAnnexe(new Annexe(name, Long.parseLong(size), type, IOUtils.toByteArray(new FileInputStream(file))))
                ? Response.ok(Json.newObject().put("response", true)).build()
                : Response.notModified().build();
    }
}
