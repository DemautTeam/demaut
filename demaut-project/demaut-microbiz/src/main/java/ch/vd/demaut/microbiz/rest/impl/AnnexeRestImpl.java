package ch.vd.demaut.microbiz.rest.impl;

import ch.vd.demaut.domain.annexes.AnnexeMetadata;
import ch.vd.demaut.domain.annexes.ContenuAnnexe;
import ch.vd.demaut.domain.annexes.NomFichier;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.microbiz.progreSoa.ProgreSoaService;
import ch.vd.demaut.microbiz.rest.AnnexeRest;
import ch.vd.demaut.microbiz.rest.RestUtils;
import ch.vd.demaut.services.annexes.AnnexesService;
import ch.vd.ses.referentiel.demaut_1_0.RefListType;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private AnnexesService annexesService;

    @Autowired
    private ProgreSoaService progreSoaService;

    @Override
    @GET
    @Path("/typesList/{profession}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesTypesAnnexes(@PathParam("profession") String professionId) throws Exception {

        LOGGER.info("listerLesTypesAnnexes " + professionId);

        List<RefListType> lesTypesAnnexes = progreSoaService.listeSOATypesAnnexes().getRefList().getRefListType();
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

        ReferenceDeDemande referenceDeDemande = new ReferenceDeDemande(demandeReference);

        Collection<AnnexeMetadata> annexesMetadata = annexesService.listerLesAnnexeMetadatas(referenceDeDemande);
        return RestUtils.forgeResponseList(Response.Status.OK, annexesMetadata);
    }

    @Override
    @GET
    @Path("/afficher/{demandeReference}/{annexeFileName}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @RolesAllowed("USER")
    public Response afficherUneAnnexe(@PathParam("demandeReference") String demandeReference,
                                      @PathParam("annexeFileName") String annexeFileName) throws JsonProcessingException {

        LOGGER.info("afficherUneAnnexe " + annexeFileName);

        ReferenceDeDemande referenceDeDemande = new ReferenceDeDemande(demandeReference);
        NomFichier nomFichier = new NomFichier(annexeFileName);

        ContenuAnnexe contenuAnnexe = annexesService.recupererContenuAnnexe(referenceDeDemande, nomFichier);
        return RestUtils.forgeResponseStream(Response.Status.OK, contenuAnnexe.getContenu());
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

        ReferenceDeDemande referenceDeDemande = new ReferenceDeDemande(demandeReference);
        NomFichier nomFichier = new NomFichier(annexeFileName);
        TypeAnnexe typeAnnexe = TypeAnnexe.valueOf(annexeType);

        annexesService.attacherUneAnnexe(referenceDeDemande, file, nomFichier, typeAnnexe);
        return RestUtils.forgeResponseTrue();
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

        ReferenceDeDemande referenceDeDemande = new ReferenceDeDemande(demandeReference);
        NomFichier nomFichier = new NomFichier(annexeFileName);
        TypeAnnexe typeAnnexe = TypeAnnexe.valueOf(annexeType);

        annexesService.supprimerUneAnnexe(referenceDeDemande, nomFichier);

        return RestUtils.forgeResponseTrue();
    }
}