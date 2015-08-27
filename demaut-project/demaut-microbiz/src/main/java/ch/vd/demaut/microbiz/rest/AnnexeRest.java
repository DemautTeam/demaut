package ch.vd.demaut.microbiz.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;

@Service
@Path("/services")
public interface AnnexeRest {

    @GET
    @Path("/main")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    Response mainData() throws JsonProcessingException;

    @POST
    @Path("/annexe/multipart")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    Response storeMultipart(@Multipart("name") String name, @Multipart("size") String size, @Multipart("type") String type,
                            @Multipart(value = "file", type = MediaType.APPLICATION_OCTET_STREAM) File file) throws IOException;
}
