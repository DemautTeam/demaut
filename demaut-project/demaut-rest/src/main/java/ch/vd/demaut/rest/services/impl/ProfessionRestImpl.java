package ch.vd.demaut.rest.services.impl;

import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.progreSoa.services.ProgreSoaService;
import ch.vd.demaut.rest.commons.json.RestUtils;
import ch.vd.demaut.services.demandeurs.donneesProf.DonneesProfessionnellesService;
import ch.vd.ses.referentiel.demaut_1_0.VcType;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.Arrays;
import java.util.List;

@CrossOriginResourceSharing(allowAllOrigins = true)
@Service("professionRestImpl")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Path("/profession")
public class ProfessionRestImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfessionRestImpl.class);

    @Autowired
    private ProgreSoaService progreSoaService;

    @Autowired
    private DonneesProfessionnellesService donneesProfessionnellesService;

    @Context
    private UriInfo uriInfo;

    @Context
    private HttpHeaders httpHeaders;

    @GET
    @Path("/professionsList")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesProfessionsDeLaSante() throws Exception {

        LOGGER.info("listerLesProfessionsDeLaSante");

        // Altrenative:
        List<Profession> professions = buildListeProfessionsSansProgresSOA();

        // Altrenative SOA:
        // List<VcType> typesAnnexe = buildListeProfessionsAvecProgresSOA(uriInfo);
        // TODO filtrer la liste selon Universitaire ou non
        return RestUtils.buildRef(professions);
    }

    /**
     * Méthode qui renvoie la liste des professions sans passer par le WS ProgresSOA
     */
    private List<Profession> buildListeProfessionsSansProgresSOA() {
        return Arrays.asList(Profession.values());
    }

    /**
     * Cette méthode sera peut-etre utilisé suivant les futures décisions prises
     * TODO: Quoiqu'il en soit virer progreSoaService du projet microbiz et le
     * mettre dans Service (qui correspond a la facade REST)
     *
     * @throws Exception
     */
    @SuppressWarnings("unused")
    private List<VcType> buildListeProfessionsAvecProgresSOA(UriInfo uriInfo) throws Exception {
        // TODO mettre en cache la liste des professions
        String path = uriInfo != null ? uriInfo.getBaseUri().getPath() : null;
        return progreSoaService.listeSOAProfession(path).getVcList().getVc();
    }

    @GET
    @Path("/donnees/")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response afficherDonneesProfession() throws Exception {

        Login login = new Login(RestUtils.fetchCurrentUserToken(httpHeaders));

        LOGGER.info("afficherDonneesProfession " + login.getValue());

        Profession profession = donneesProfessionnellesService.afficherDonneesProfession(login);
        CodeGLN codeGLN = donneesProfessionnellesService.recupererDonneesProfessionnelles(login).getCodeGLN();
        return RestUtils.buildJSon(Arrays.asList(profession, codeGLN));
    }
}
