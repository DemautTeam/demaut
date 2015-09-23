package ch.vd.demaut.microbiz.rest.impl;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.ProfessionDeLaSante;
import ch.vd.demaut.domain.demandeurs.donneesProf.CodeGLN;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.microbiz.progreSoa.ProgreSoaService;
import ch.vd.demaut.microbiz.rest.ProfessionRest;
import ch.vd.demaut.microbiz.rest.RestUtils;
import ch.vd.demaut.services.demandeurs.donneesProf.DonneesProfessionnellesService;
import ch.vd.ses.referentiel.demaut_1_0.VcType;
import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@CrossOriginResourceSharing(
        allowOrigins = {"*"},
        allowCredentials = true,
        maxAge = 3600,
        allowHeaders = {"Content-Type", "X-Requested-With"},
        exposeHeaders = {"Access-Control-Allow-Origin"}
)
@Service("professionRestImpl")
@Path("/profession")
public class ProfessionRestImpl implements ProfessionRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfessionRestImpl.class);

    @Inject
    private DonneesProfessionnellesService donneesProfessionnellesService;

    @Inject
    private ProgreSoaService progreSoaService;

    @Override
    @GET
    @Path("/professionsList")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesProfessionsDeLaSante(@Context UriInfo uriInfo) throws Exception {

        LOGGER.info("listerLesProfessionsDeLaSante");

        // TODO mettre en cache la liste des professions
        String path = uriInfo != null ? uriInfo.getBaseUri().getPath() : null;
        List<VcType> lesProfessionsDeLaSante = progreSoaService.listeSOAProfession(path).getVcList().getVc();
        // TODO filtrer la liste selon Universitaire ou non
        return RestUtils.forgeResponseList(Response.Status.OK, lesProfessionsDeLaSante);
    }

    @Override
    @GET
    @Path("/donnees/{demandeReference}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response afficherDonneesProfession(@Context UriInfo uriInfo,
                                              @PathParam("demandeReference") String demandeReference) throws Exception {

        LOGGER.info("afficherDonneesProfession " + demandeReference);

        ReferenceDeDemande referenceDeDemande = new ReferenceDeDemande(demandeReference);
        ProfessionDeLaSante professionDeLaSante = donneesProfessionnellesService.afficherDonneesProfession(referenceDeDemande);

        String path = uriInfo != null ? uriInfo.getBaseUri().getPath() : null;
        List<VcType> lesProfessionsDeLaSante = progreSoaService.listeSOAProfession(path).getVcList().getVc();
        return RestUtils.forgeResponseString(Response.Status.OK,
                String.valueOf(CollectionUtils.find(lesProfessionsDeLaSante, new BeanPropertyValueEqualsPredicate("libl", professionDeLaSante.name()))));
    }

    @Override
    @GET
    @Path("/donnees/{demandeReference}/{idProfession}/{codeGln}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response renseignerDonneesProfession(@Context UriInfo uriInfo,
                                                @PathParam("demandeReference") String demandeReference,
                                                @PathParam("idProfession") String idProfession,
                                                @PathParam("codeGln") String codeGln) throws Exception {

        LOGGER.info("afficherDonneesProfession " + demandeReference);

        ReferenceDeDemande referenceDeDemande = new ReferenceDeDemande(demandeReference);
        CodeGLN codeGLN = new CodeGLN(codeGln);
        Login login = new Login("admin@admin");  // TODO get login

        String path = uriInfo != null ? uriInfo.getBaseUri().getPath() : null;
        List<VcType> lesProfessionsDeLaSante = progreSoaService.listeSOAProfession(path).getVcList().getVc();
        ProfessionDeLaSante professionDeLaSante = (ProfessionDeLaSante) CollectionUtils.find(lesProfessionsDeLaSante, new BeanPropertyValueEqualsPredicate("id", idProfession));

        ReferenceDeDemande renseignerDonneesProfession = donneesProfessionnellesService.renseignerDonneesProfession(login, referenceDeDemande, professionDeLaSante, codeGLN);
        return RestUtils.forgeResponseString(Response.Status.OK, renseignerDonneesProfession.getValue());
    }
}
