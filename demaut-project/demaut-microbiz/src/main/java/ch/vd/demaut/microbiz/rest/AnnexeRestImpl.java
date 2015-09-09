package ch.vd.demaut.microbiz.rest;

import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.microbiz.progreSoa.AnnexetypesList;
import ch.vd.demaut.microbiz.progreSoa.PorgreSoaService;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
import ch.vd.pee.microbiz.core.utils.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Service
@Path("/services")
public class AnnexeRestImpl implements AnnexeRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnexeRestImpl.class);

    private static final ObjectWriter viewWriter = new ObjectMapper().writer();

    @Autowired
    private DemandeAutorisationService demandeAutorisationService;

    @Autowired
    private PorgreSoaService porgreSoaService;

    // TODO Processor Camel
    @Value("${user}")
    private String user;

    @Override
    @GET
    @Path("/annexes/typesList/{profession}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesTypesAnnexes(@PathParam("profession") String profession) throws Exception {

        LOGGER.info("listerLesTypesAnnexes " + profession);

        AnnexetypesList lesTypesAnnexes = porgreSoaService.listerLesTypesAnnexes();
        // TODO filtrer la liste selon profession
        return Response.ok(Json.newObject().put("response", viewWriter.writeValueAsString(lesTypesAnnexes))).build();
    }

    @Override
    @GET
    @Path("/annexes/lister/{demandeReference}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesAnnexes(@PathParam("demandeReference") String demandeReference) throws JsonProcessingException {

        LOGGER.info("listerLesAnnexes " + demandeReference);

        Collection<Annexe> lesAnnexes = demandeAutorisationService.listerLesAnnexes(demandeReference);
        return Response.ok(Json.newObject().put("response", viewWriter.writeValueAsString(lesAnnexes))).build();
    }

    @Override
    @GET
    @Path("/annexes/afficher/{demandeReference}/{annexeFileName}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @RolesAllowed("USER")
    public Response afficherUneAnnexe(@PathParam("demandeReference") String demandeReference,
                                      @PathParam("annexeFileName") String annexeFileName) {

        LOGGER.info("afficherUneAnnexe " + annexeFileName);

        Annexe annexe = demandeAutorisationService.afficherUneAnnexe(demandeReference, annexeFileName);
        return !StringUtils.isEmpty(demandeReference) && !StringUtils.isEmpty(annexeFileName) && annexe != null && annexe.getContenu().getContenu() != null
                ? Response.ok(annexe.getContenu().getContenu(), MediaType.APPLICATION_OCTET_STREAM_TYPE).build()
                : Response.noContent().build();
    }

    @Override
    @POST
    @Path("/annexes/attacher")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response attacherUneAnnexe(@Multipart("demandeReference") String demandeReference,
                                      @Multipart("annexeFile") File file,
                                      @Multipart("annexeFileName") String annexeFileName,
                                      @Multipart("annexeFileSize") String annexeFileSize,
                                      @Multipart("annexeFileType") String annexeFileType,
                                      @Multipart("annexeType") String annexeType) throws IOException {

        LOGGER.info("attacherUneAnnexe " + annexeFileName);

        boolean attacherUneAnnexe = this.demandeAutorisationService.attacherUneAnnexe(demandeReference, file, annexeFileName, annexeFileSize, annexeFileType, annexeType);
        return !StringUtils.isEmpty(demandeReference) && !StringUtils.isEmpty(annexeFileName) && !StringUtils.isEmpty(annexeType) && file != null
                ? Response.ok(Json.newObject().put("response", viewWriter.writeValueAsString(attacherUneAnnexe))).build()
                : Response.notModified().build();
    }

    @Override
    @GET
    @Path("/annexes/supprimer/{demandeReference}/{annexeFileName}/{annexeType}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response supprimerUneAnnexe(@PathParam("demandeReference") String demandeReference,
                                       @PathParam("annexeFileName") String annexeFileName,
                                       @PathParam("annexeType") String annexeType) throws JsonProcessingException {

        LOGGER.info("supprimerUneAnnexe " + annexeFileName);

        boolean supprimerUneAnnexe = this.demandeAutorisationService.supprimerUneAnnexe(demandeReference, annexeFileName, annexeType);
        return !StringUtils.isEmpty(demandeReference) && !StringUtils.isEmpty(annexeFileName) && !StringUtils.isEmpty(annexeType)
                ? Response.ok(Json.newObject().put("response", viewWriter.writeValueAsString(supprimerUneAnnexe))).build()
                : Response.noContent().build();
    }
}
