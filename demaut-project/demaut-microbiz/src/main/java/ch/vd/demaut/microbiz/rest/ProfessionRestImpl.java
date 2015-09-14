package ch.vd.demaut.microbiz.rest;

import ch.vd.demaut.domain.demandes.autorisation.ProfessionDeLaSante;
import ch.vd.demaut.microbiz.progreSoa.PorgreSoaService;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
import ch.vd.ses.referentiel.demaut_1_0.RefListType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    @Path("/professionsList")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesProfessionsDeLaSante() throws Exception {

        LOGGER.info("listerLesProfessionsDeLaSante");

        // TODO mettre en cache la liste des professions
        List<RefListType> lesProfessionsDeLaSante = porgreSoaService.listeSOAProfession().getRefList().getRefListType();
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
        List<RefListType> lesProfessionsDeLaSante = porgreSoaService.listeSOAProfession().getRefList().getRefListType();
        return RestUtils.forgeResponseString(Response.Status.OK,
                String.valueOf(CollectionUtils.find(lesProfessionsDeLaSante, new BeanPropertyValueEqualsPredicate("libl", professionDeLaSante.name()))));
    }
}
