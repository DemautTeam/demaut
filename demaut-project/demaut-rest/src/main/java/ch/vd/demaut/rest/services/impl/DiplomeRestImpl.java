package ch.vd.demaut.rest.services.impl;

import ch.vd.demaut.domain.config.TypeProgres;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandeur.Pays;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.*;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.rest.json.commons.RestUtils;
import ch.vd.demaut.rest.services.AbstractRestService;
import ch.vd.demaut.services.demandeurs.donneesProf.DonneesProfessionnellesService;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@CrossOriginResourceSharing(allowAllOrigins = true)
@Path("/diplomes")
public class DiplomeRestImpl extends AbstractRestService {

    public static final DateTimeFormatter SHORT_DATE_PARSER = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private static final Logger LOGGER = LoggerFactory.getLogger(DiplomeRestImpl.class);

    private DonneesProfessionnellesService donneesProfessionnellesService;

    public void setDonneesProfessionnellesService(DonneesProfessionnellesService donneesProfessionnellesService) {
        this.donneesProfessionnellesService = donneesProfessionnellesService;
    }

    @GET
    @Path("/typeDiplomesList")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesTypesDeDiplomes() throws Exception {

        LOGGER.info("listerLesTypesDeDiplomes");

        // Altrenative:
        List<TypeDiplomeAccepte> diplomeAcceptes = buildListeTypesDiplomesAcceptesSansProgresSOA();
        // Autre altrenative:
        //List<VcType> diplomeAcceptes = buildListeTypesDiplomesAcceptesAvecProgresSOA(uriInfo);
        return RestUtils.buildRef(diplomeAcceptes);
    }

    private List<TypeDiplomeAccepte> buildListeTypesDiplomesAcceptesSansProgresSOA() {
        return Arrays.asList(TypeDiplomeAccepte.values());
    }

    @GET
    @Path("/typeFormationsList")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesTitresFormations(@QueryParam("typeDiplome") String typeDiplomeId) throws Exception {

        LOGGER.info("listerLesTitresFormations " + typeDiplomeId);

        TypeDiplomeAccepte typeDiplomeAccepte = TypeDiplomeAccepte.getTypeById(Integer.parseInt(typeDiplomeId));

        // Altrenative:
        List<?> titreFormations = buildListeTitresFormationsSansProgresSOA(typeDiplomeAccepte);
        // Autre altrenative:
        //List<VcType> titreFormations = buildListeTitresFormationsAvecProgresSOA(uriInfo, typeDiplomeAccepte);
        return RestUtils.buildRef(titreFormations);
    }

    @GET
    @Path("/typeFormationsAll")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesTitresFormationsAll() throws Exception {

        LOGGER.info("listerLesTitresFormationsAll");

        List<TypeProgres> typeFormationsAll = new ArrayList<>();
        typeFormationsAll.addAll(Arrays.<TypeProgres>asList(TitreFormationApprofondieProgres.values()));
        typeFormationsAll.addAll(Arrays.<TypeProgres>asList(TitreFormationComplementaireProgres.values()));
        typeFormationsAll.addAll(Arrays.<TypeProgres>asList(TitreFormationInitialeProgres.values()));
        typeFormationsAll.addAll(Arrays.<TypeProgres>asList(TitreFormationPostgradeProgres.values()));
        return RestUtils.buildRef(typeFormationsAll);
    }

    private List<?> buildListeTitresFormationsSansProgresSOA(TypeDiplomeAccepte typeDiplomeAccepte) {
        switch (typeDiplomeAccepte) {
            case D_FORMATION_APPROFONDIE:
                return Arrays.asList(TitreFormationApprofondieProgres.values());
            case D_FORMATION_COMPLEMENTAIRE:
                return Arrays.asList(TitreFormationComplementaireProgres.values());
            case D_FORMATION_INITIALE:
                return Arrays.asList(TitreFormationInitialeProgres.values());
            case D_POSTGRADE:
                return Arrays.asList(TitreFormationPostgradeProgres.values());
            default:
                return new ArrayList<>();
        }
    }

    /**
     * Méthode qui ajoute un diplome du professionnel la liste des titres et formations
     * dateObtention String (format 2015-10-06T22:00:00.000Z)
     * dateReconnaissance String (format 2015-10-06T22:00:00.000Z)
     */
    @SuppressWarnings("all")
    @GET
    @Path("/ajouter")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response ajouterUnDiplome(@QueryParam("referenceDeDemande") String referenceDeDemandeStr,
                                     @QueryParam("referenceDeDiplome") String referenceDeDiplomeStr,
                                     @QueryParam("typeDiplome") String typeDiplomeId,
                                     @QueryParam("typeFormation") String typeFormationId,
                                     @QueryParam("complement") String complement,
                                     @QueryParam("dateObtention") String dateObtentionStr,
                                     @QueryParam("paysObtention") String paysObtentionId,
                                     @QueryParam("dateReconnaissance") String dateReconnaissanceStr) throws Exception {

        Login login = getLogin();

        LOGGER.info("ajouterUnDiplome pour : " + login.getValue() + ", referenceDeDemande=" + referenceDeDemandeStr + ", typeDiplome=" + typeDiplomeId + ", typeFormation=" + typeFormationId);

        ReferenceDeDemande referenceDeDemande = new ReferenceDeDemande(referenceDeDemandeStr);
        ReferenceDeDiplome referenceDeDiplome = new ReferenceDeDiplome(referenceDeDiplomeStr);
        TypeDiplomeAccepte typeDiplomeAccepte = TypeDiplomeAccepte.getTypeById(Integer.parseInt(typeDiplomeId));
        TitreFormation titreFormation = new TitreFormation(convertTypeFormationIdToEnum(typeDiplomeAccepte, typeFormationId).name());
        DateObtention dateObtention = new DateObtention(SHORT_DATE_PARSER.parseLocalDate(dateObtentionStr));
        Pays paysObtention = Pays.getTypeById(Integer.parseInt(paysObtentionId));
        DateReconnaissance dateReconnaissance = null;
        if (!StringUtils.isEmpty(dateReconnaissanceStr) && !"-".equals(dateReconnaissanceStr)) {
            dateReconnaissance = new DateReconnaissance(SHORT_DATE_PARSER.parseLocalDate(dateReconnaissanceStr));
        }

        donneesProfessionnellesService.ajouterUnDiplome(login, referenceDeDemande, referenceDeDiplome, typeDiplomeAccepte,
                titreFormation, complement, dateObtention, paysObtention, dateReconnaissance);
        return RestUtils.buildJSon(true);
    }

    private TypeProgres convertTypeFormationIdToEnum(TypeDiplomeAccepte typeDiplomeAccepte, String typeFormationId) {
        switch (typeDiplomeAccepte) {
            case D_FORMATION_APPROFONDIE:
                return TitreFormationApprofondieProgres.getTypeById(Integer.parseInt(typeFormationId));
            case D_FORMATION_COMPLEMENTAIRE:
                return TitreFormationComplementaireProgres.getTypeById(Integer.parseInt(typeFormationId));
            case D_FORMATION_INITIALE:
                return TitreFormationInitialeProgres.getTypeById(Integer.parseInt(typeFormationId));
            case D_POSTGRADE:
                return TitreFormationPostgradeProgres.getTypeById(Integer.parseInt(typeFormationId));
            default:
                return null;
        }
    }

    @SuppressWarnings("all")
    @GET
    @Path("/supprimer")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response supprimerUnDiplome(@QueryParam("referenceDeDemande") String referenceDeDemandeStr,
                                       @QueryParam("referenceDeDiplome") String referenceDeDiplomeStr) throws Exception {

        Login login = getLogin();

        LOGGER.info("supprimerUnDiplome pour : " + login.getValue() + ", referenceDeDemande=" + referenceDeDemandeStr + ", referenceDeDiplome=" + referenceDeDiplomeStr);

        ReferenceDeDemande referenceDeDemande = new ReferenceDeDemande(referenceDeDemandeStr);
        ReferenceDeDiplome referenceDeDiplome = new ReferenceDeDiplome(referenceDeDiplomeStr);

        donneesProfessionnellesService.supprimerUnDiplome(login, referenceDeDemande, referenceDeDiplome);
        return RestUtils.buildJSon(true);
    }

    @SuppressWarnings("all")
    @GET
    @Path("/diplomesSaisis")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response recupererDiplomesSaisis(@QueryParam("referenceDeDemande") String referenceDeDemandeStr) throws Exception {
        Login login = getLogin();

        LOGGER.info("recupererDiplomesSaisis pour : " + login.getValue() + ", referenceDeDemande=" + referenceDeDemandeStr);

        ReferenceDeDemande referenceDeDemande = new ReferenceDeDemande(referenceDeDemandeStr);

        List<Diplome> diplomesSaisis = donneesProfessionnellesService.recupererDiplomesSaisis(login, referenceDeDemande);
        return RestUtils.buildJSon(diplomesSaisis);
    }
}
