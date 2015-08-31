package ch.vd.demaut.microbiz.rest;

import java.io.File;
import java.io.IOException;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ch.vd.pee.microbiz.core.utils.Json;

@Service
@Path("/services")
public class AnnexeRestImpl implements AnnexeRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnexeRestImpl.class);

//    @Autowired
//    private DemandeAutorisationService demandeAutorisationService;

    // TODO Processor Camel
    @Value("${user}")
    private String user;

    @Override
    @POST
    @Path("/annexe/multipart")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response storeMultipart(@Multipart("name") String name, @Multipart("size") String size, @Multipart("type") String type,
                                   @Multipart(value = "file", type = MediaType.APPLICATION_OCTET_STREAM) File file) throws IOException {

        LOGGER.info("storeMultipart");

        return !StringUtils.isEmpty(name) && !StringUtils.isEmpty(size) && !StringUtils.isEmpty(type) && file != null
                // TODO implement attacherUneAnnexe
                //&& this.demandeAutorisationService.attacherUneAnnexe(new Annexe(TypeAnnexe.CV, name, IOUtils.toByteArray(new FileInputStream(file))))
                ? Response.ok(Json.newObject().put("response", true)).build()
                : Response.notModified().build();
    }
}
