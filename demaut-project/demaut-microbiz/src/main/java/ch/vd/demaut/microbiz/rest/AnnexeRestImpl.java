package ch.vd.demaut.microbiz.rest;

import java.io.File;
import java.io.IOException;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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

    @SuppressWarnings("unused")
	@Autowired
    private DemandeAutorisationService demandeAutorisationService;

    // TODO Processor Camel
    @Value("${user}")
    private String user;

    @Override
    @GET
    @Path("/annexes/all")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public String fetchAnnexes() throws JsonProcessingException {

        LOGGER.info("fetchAnnexes");

        ObjectWriter viewWriter = new ObjectMapper().writer();
        return viewWriter.writeValueAsString("Annexes a lister !");
    }

    @Override
    @POST
    @Path("/annexe/multipart")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response storeMultipart(@Multipart("annexeFile") File file, @Multipart("annexeFileName") String annexeFileName,
                                 @Multipart("annexeFileSize") String annexeFileSize, @Multipart("annexeFileType") String annexeFileType,
                                 @Multipart("annexeType") String annexeType) throws IOException {

        LOGGER.info("storeMultipart");
        //String fileName = file.getName();
        //byte[] fileStream = IOUtils.toByteArray(new FileInputStream(file));

        return !StringUtils.isEmpty(annexeFileName) && !StringUtils.isEmpty(annexeFileSize) && !StringUtils.isEmpty(annexeFileType) &&
                file != null && !StringUtils.isEmpty(annexeType)
                // TODO implement annexeFileType
                //&& this.demandeAutorisationService.attacherUneAnnexe(new Annexe(TypeAnnexe.CV, name, IOUtils.toByteArray(new FileInputStream(file))))
                ? Response.ok(Json.newObject().put("response", true)).build()
                : Response.notModified().build();
    }
}
