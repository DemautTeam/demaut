package ch.vd.demaut.microbiz.rest;

import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.AnnexeMetadata;
import ch.vd.demaut.microbiz.progreSoa.PorgreSoaService;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
import ch.vd.ses.referentiel.demaut_1_0.RefListType;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
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
import java.util.Collection;
import java.util.List;

@CrossOriginResourceSharing(
        allowOrigins = {"*"},
        allowCredentials = true,
        maxAge = 3600,
        allowHeaders = {"Content-Type", "X-Requested-With"},
        exposeHeaders = {"Access-Control-Allow-Origin"}
)
@Service
@Path("/annexes")
public class AnnexeRestImpl implements AnnexeRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnexeRestImpl.class);

    @Autowired
    private DemandeAutorisationService demandeAutorisationService;

    @Autowired
    private PorgreSoaService porgreSoaService;

    // TODO Processor Camel
    @Value("${user}")
    private String user;

    @Override
    @GET
    @Path("/typesList/{profession}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesTypesAnnexes(@PathParam("profession") String profession) throws Exception {

        LOGGER.info("listerLesTypesAnnexes " + profession);

        List<RefListType> lesTypesAnnexes = porgreSoaService.listeSOATypesAnnexes().getRefList().getRefListType();
        // TODO filtrer la liste selon profession (liste ordinaire VS simplifiée)
        return RestUtils.forgeResponseList(Response.Status.OK, lesTypesAnnexes);
    }

    @Override
    @GET
    @Path("/lister/{demandeReference}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesAnnexes(@PathParam("demandeReference") String demandeReference) throws JsonProcessingException {

        LOGGER.info("listerLesAnnexes " + demandeReference);

        Collection<AnnexeMetadata> lesAnnexes = demandeAutorisationService.listerLesAnnexeMetadatas(demandeReference);
        return RestUtils.forgeResponseList(Response.Status.OK, lesAnnexes);
    }

    @Override
    @GET
    @Path("/afficher/{demandeReference}/{annexeFileName}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @RolesAllowed("USER")
    public Response afficherUneAnnexe(@PathParam("demandeReference") String demandeReference,
                                      @PathParam("annexeFileName") String annexeFileName) throws JsonProcessingException {

        LOGGER.info("afficherUneAnnexe " + annexeFileName);

        Annexe annexe = demandeAutorisationService.afficherUneAnnexe(demandeReference, annexeFileName);
        return !StringUtils.isEmpty(demandeReference) && !StringUtils.isEmpty(annexeFileName) && annexe != null && annexe.getContenuAnnexe() != null
                ? RestUtils.forgeResponseStream(Response.Status.OK, annexe.getContenuAnnexe())
                : RestUtils.forgeResponseNoContent();
    }

    @Override
    @POST
    @Path("/attacher")
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
                ? RestUtils.forgeResponseString(Response.Status.OK, String.valueOf(attacherUneAnnexe))
                : RestUtils.forgeResponseNoContent();
    }

    @Override
    @GET
    @Path("/supprimer/{demandeReference}/{annexeFileName}/{annexeType}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response supprimerUneAnnexe(@PathParam("demandeReference") String demandeReference,
                                       @PathParam("annexeFileName") String annexeFileName,
                                       @PathParam("annexeType") String annexeType) throws JsonProcessingException {

        LOGGER.info("supprimerUneAnnexe " + annexeFileName);

        boolean supprimerUneAnnexe = this.demandeAutorisationService.supprimerUneAnnexe(demandeReference, annexeFileName, annexeType);
        return !StringUtils.isEmpty(demandeReference) && !StringUtils.isEmpty(annexeFileName) && !StringUtils.isEmpty(annexeType)
                ? RestUtils.forgeResponseString(Response.Status.OK, String.valueOf(supprimerUneAnnexe))
                : RestUtils.forgeResponseNoContent();
    }
}
