package ch.vd.demaut.microbiz.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ch.vd.demaut.domain.demandes.autorisation.ProfessionDeLaSante;
import ch.vd.demaut.microbiz.progreSoa.ProgreSoaService;
import ch.vd.ses.referentiel.demaut_1_0.RefListType;

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

    @Autowired
    private ProgreSoaService progreSoaService;

    // TODO Processor Camel
    @Value("${user}")
    private String user;


    @Override
    @GET
    @Path("/professionsList")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesProfessionsDeLaSante() throws Exception {

        LOGGER.info("listerLesProfessionsDeLaSante");

        // TODO mettre en cache la liste des professions
        List<RefListType> lesProfessionsDeLaSante = progreSoaService.listeSOAProfession().getRefList().getRefListType();
        // TODO filtrer la liste selon Universitaire ou non
        return RestUtils.forgeResponseList(Response.Status.OK, lesProfessionsDeLaSante);
    }

    @Override
    @GET
    @Path("/donnees/{demandeReference}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response afficherDonneesProfession(@PathParam("demandeReference") String demandeReference) throws Exception {

        LOGGER.info("afficherDonneesProfession " + demandeReference);

        ProfessionDeLaSante professionDeLaSante = ProfessionDeLaSante.Medecin; // TODO demandeAutorisationService.afficherDonneesProfession(demandeReference);
        List<RefListType> lesProfessionsDeLaSante = progreSoaService.listeSOAProfession().getRefList().getRefListType();
        return RestUtils.forgeResponseString(Response.Status.OK,
                String.valueOf(CollectionUtils.find(lesProfessionsDeLaSante, new BeanPropertyValueEqualsPredicate("libl", professionDeLaSante.name()))));
    }

    @Override
    @GET
    @Path("/donnees/{demandeReference}/{idProfession}/{codeGln}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response renseignerDonneesProfession(@PathParam("demandeReference") String demandeReference,
                                                @PathParam("idProfession") String idProfession,
                                                @PathParam("codeGln") String codeGln) throws Exception {
        //ReferenceDeDemande ref = new ReferenceDeDemande(demandeReference);
        
        //TODO: Call the service
//        String renseignerDonneesProfession = demandeAutorisationService.renseignerDonneesProfession(demandeReference, idProfession, codeGln);
//        return !StringUtils.isEmpty(idProfession) && !StringUtils.isEmpty(renseignerDonneesProfession)
//        		? RestUtils.forgeResponseString(Response.Status.OK, renseignerDonneesProfession)
//        				: RestUtils.forgeResponseNoContent();
        
        return RestUtils.forgeResponseTrue();

    }
}
