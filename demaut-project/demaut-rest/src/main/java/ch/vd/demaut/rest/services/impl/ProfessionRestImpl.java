package ch.vd.demaut.rest.services.impl;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.rest.json.commons.RestUtils;
import ch.vd.demaut.rest.services.AbstractRestService;
import ch.vd.demaut.services.demandeurs.donneesProf.DonneesProfessionnellesService;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.*;
import java.util.Arrays;
import java.util.List;

@CrossOriginResourceSharing(allowAllOrigins = true)
@Path("/profession")
public class ProfessionRestImpl extends AbstractRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfessionRestImpl.class);

    private DonneesProfessionnellesService donneesProfessionnellesService;

    public void setDonneesProfessionnellesService(DonneesProfessionnellesService donneesProfessionnellesService) {
        this.donneesProfessionnellesService = donneesProfessionnellesService;
    }

    @GET
    @Path("/professionsList")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesProfessionsDeLaSante() throws Exception {

        LOGGER.info("listerLesProfessionsDeLaSante");

        // Alternative:
        List<Profession> professions = buildListeProfessionsSansProgresSOA();

        // Alternative SOA:
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

    @GET
    @Path("/professionsCodeGLNObligatoire")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerProfessionsAvecCodeGLNObligatoire() throws Exception {

        LOGGER.info("listerProfessionsAvecCodeGLNObligatoire");
        
        List<Profession> professionsAvecGLN = donneesProfessionnellesService.listerProfessionsAvecCodeGlnObligatoire();

        return RestUtils.buildRef(professionsAvecGLN);
    }

    @GET
    @Path("/professionDeDemande")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response recupererProfessionDeDemande(@QueryParam("referenceDeDemande") String referenceDeDemandeStr) throws Exception {

        Login login = getLogin();

        LOGGER.info("recuperer Profession de demande " + login.getValue()  + ", referenceDeDemande=" + referenceDeDemandeStr);

        ReferenceDeDemande referenceDeDemande = new ReferenceDeDemande(referenceDeDemandeStr);

        Profession profession = donneesProfessionnellesService.recupererProfessionDeDemande(login, referenceDeDemande);
        CodeGLN codeGLN = donneesProfessionnellesService.recupererDonneesProfessionnelles(login, referenceDeDemande).getCodeGLN();
        if(codeGLN == null){
            return RestUtils.buildJSon(Arrays.asList(profession.getRefProgresID().getId(), null));
        }
        return RestUtils.buildJSon(Arrays.asList(profession.getRefProgresID().getId(), codeGLN.getValue()));
    }

    @GET
    @Path("/updateCodeGLN")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response validerEtRenseignerCodeGLN(@QueryParam("referenceDeDemande") String referenceDeDemandeStr,
                                               @QueryParam("codeGln") String codeGln) throws Exception {

        Login login = getLogin();

        LOGGER.info("validerEtRenseignerCodeGLN " + login.getValue()  + ", codeGln=" + codeGln);

        ReferenceDeDemande referenceDeDemande = new ReferenceDeDemande(referenceDeDemandeStr);
        CodeGLN codeGLN =  StringUtils.isEmpty(codeGln) ? null : new CodeGLN(codeGln);

        donneesProfessionnellesService.validerEtRenseignerCodeGLN(login, referenceDeDemande, codeGLN);
        return RestUtils.buildJSon(true);    
    }
}
