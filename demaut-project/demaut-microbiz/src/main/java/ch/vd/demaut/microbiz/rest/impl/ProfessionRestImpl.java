package ch.vd.demaut.microbiz.rest.impl;

import java.util.Arrays;
import java.util.List;

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

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.microbiz.progreSoa.ProgreSoaService;
import ch.vd.demaut.microbiz.rest.ProfessionRest;
import ch.vd.demaut.microbiz.rest.RestUtils;
import ch.vd.demaut.services.demandeurs.donneesProf.DonneesProfessionnellesService;
import ch.vd.ses.referentiel.demaut_1_0.VcType;

@CrossOriginResourceSharing(allowOrigins = { "*" }, allowCredentials = true, maxAge = 3600, allowHeaders = {
        "Content-Type", "X-Requested-With" }, exposeHeaders = { "Access-Control-Allow-Origin" })
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

        // Altrenative:
        List<Profession> professions = buildListeProfessionsSansProgresSOA();

        // Altrenative SOA:
        // List<VcType> typesAnnexe =
        // buildListeProfessionsAvecProgresSOA(uriInfo);
        // TODO filtrer la liste selon Universitaire ou non
        return RestUtils.forgeResponseList(professions);
    }

    /**
     * Méthode qui renvoie la liste des professions sans passer par le WS
     * ProgresSOA
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
        List<VcType> lesProfessionsDeLaSante = progreSoaService.listeSOAProfession(path).getVcList().getVc();
        return lesProfessionsDeLaSante;
    }

    @Override
    @GET
    @Path("/donnees/{demandeReference}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response afficherDonneesPro(@Context UriInfo uriInfo, @PathParam("demandeReference") String demandeReference)
            throws Exception {

        LOGGER.info("afficherDonneesProfession " + demandeReference);

        ReferenceDeDemande referenceDeDemande = new ReferenceDeDemande(demandeReference);
        Profession profession = donneesProfessionnellesService.afficherDonneesProfession(referenceDeDemande);

        String path = uriInfo != null ? uriInfo.getBaseUri().getPath() : null;
        List<VcType> lesProfessionsDeLaSante = progreSoaService.listeSOAProfession(path).getVcList().getVc();
        return RestUtils.forgeResponseString(String.valueOf(CollectionUtils.find(lesProfessionsDeLaSante,
                new BeanPropertyValueEqualsPredicate("libl", profession.name()))));
    }

    @Override
    @GET
    @Path("/donnees/{demandeReference}/{idProfession}/{codeGln}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response renseignerDonneesPro(@Context UriInfo uriInfo,
            @PathParam("demandeReference") String demandeReference, @PathParam("idProfession") String idProfession,
            @PathParam("codeGln") String codeGln) throws Exception {

        LOGGER.info("afficherDonneesProfession " + demandeReference);

        ReferenceDeDemande referenceDeDemande = new ReferenceDeDemande(demandeReference);
        CodeGLN codeGLN = new CodeGLN(codeGln);
        Login login = new Login("admin@admin"); // TODO get login

        String path = uriInfo != null ? uriInfo.getBaseUri().getPath() : null;
        List<VcType> lesProfessions = progreSoaService.listeSOAProfession(path).getVcList().getVc();
        Profession profession = (Profession) CollectionUtils.find(lesProfessions,
                new BeanPropertyValueEqualsPredicate("id", idProfession));

        ReferenceDeDemande renseignerDonneesProfession = donneesProfessionnellesService
                .renseignerDonneesProfession(login, referenceDeDemande, profession, codeGLN);
        return RestUtils.forgeResponseString(renseignerDonneesProfession.getValue());
    }
}
