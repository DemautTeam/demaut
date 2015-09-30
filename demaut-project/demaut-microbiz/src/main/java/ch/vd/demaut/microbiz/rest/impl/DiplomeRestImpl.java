package ch.vd.demaut.microbiz.rest.impl;

import ch.vd.demaut.domain.config.TypeProgres;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandeur.Pays;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.*;
import ch.vd.demaut.microbiz.progreSoa.ProgreSoaService;
import ch.vd.demaut.microbiz.rest.RestUtils;
import ch.vd.demaut.services.demandeurs.donneesProf.DonneesProfessionnellesService;
import ch.vd.ses.referentiel.demaut_1_0.VcType;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@CrossOriginResourceSharing(allowOrigins = {"*"}, allowCredentials = true, maxAge = 3600, allowHeaders = {
        "Content-Type", "X-Requested-With"}, exposeHeaders = {"Access-Control-Allow-Origin"})
@Service("diplomeRestImpl")
@Path("/diplomes")
public class DiplomeRestImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiplomeRestImpl.class);

    @Inject
    private ProgreSoaService progreSoaService;

    @Inject
    private DonneesProfessionnellesService donneesProfessionnellesService;

    private DateTimeFormatter SHORT_DATE_FORMATTER = DateTimeFormat.forPattern("dd.MM.yyyy");

    @GET
    @Path("/typeDiplomesList")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesTypesDeDiplomes(@Context UriInfo uriInfo) throws Exception {

        LOGGER.info("listerLesTypesDeDiplomes");

        // Altrenative:
        List<TypeDiplomeAccepte> diplomeAcceptes = buildListeTypesDiplomesAcceptesSansProgresSOA();
        // Autre altrenative:
        //List<VcType> diplomeAcceptes = buildListeTypesDiplomesAcceptesAvecProgresSOA(uriInfo);
        return RestUtils.forgeResponseList(diplomeAcceptes);
    }

    private List<TypeDiplomeAccepte> buildListeTypesDiplomesAcceptesSansProgresSOA() {
        return Arrays.asList(TypeDiplomeAccepte.values());
    }

    @GET
    @Path("/typeFormationsList/{typeDiplome}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesTitresFormations(@Context UriInfo uriInfo, @PathParam("typeDiplome") String typeDiplomeId)
            throws Exception {

        LOGGER.info("listerLesTitresFormations " + typeDiplomeId);

        TypeDiplomeAccepte typeDiplomeAccepte = TypeDiplomeAccepte.getTypeById(Integer.parseInt(typeDiplomeId));

        // Altrenative:
        List<?> titreFormations = buildListeTitresFormationsSansProgresSOA(typeDiplomeAccepte);
        // Autre altrenative:
        //List<VcType> titreFormations = buildListeTitresFormationsAvecProgresSOA(uriInfo, typeDiplomeAccepte);
        return RestUtils.forgeResponseList(titreFormations);
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

    @SuppressWarnings("unused")
    private List<VcType> buildListeTitresFormationsAvecProgresSOA(UriInfo uriInfo, TypeDiplomeAccepte typeDiplomeAccepte) throws Exception {
        String path = uriInfo != null ? uriInfo.getBaseUri().toString() : null;
        switch (typeDiplomeAccepte) {
            case D_FORMATION_APPROFONDIE:
                return progreSoaService.listeSOADiplomesFormationApprofondie(path).getVcList().getVc();
            case D_FORMATION_COMPLEMENTAIRE:
                return progreSoaService.listeSOADiplomesFormationComplementaire(path).getVcList().getVc();
            case D_FORMATION_INITIALE:
                return progreSoaService.listeSOADiplomesFormationInitiale(path).getVcList().getVc();
            case D_POSTGRADE:
                return progreSoaService.listeSOADiplomesFormationPostGrade(path).getVcList().getVc();
            default:
                return new ArrayList<>();
        }
    }

    @GET
    @Path("/paysList")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response listerLesPays(@Context UriInfo uriInfo) throws Exception {

        LOGGER.info("listerLesPays");

        // Altrenative:
        List<Pays> paysList = buildListePaysSansProgresSOA();
        // Autre altrenative:
        //List<VcType> paysList = buildListePaysAvecProgresSOA(uriInfo);
        return RestUtils.forgeResponseList(paysList);
    }


    private List<Pays> buildListePaysSansProgresSOA() {
        return Arrays.asList(Pays.values());
    }

    @SuppressWarnings("all")
    @GET
    @Path("/ajouter/{demandeReference}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response ajouterUnDiplome(@Context UriInfo uriInfo, @PathParam("demandeReference") String demandeReference,
                                     @QueryParam("keyDiplome") String keyDiplome, @QueryParam("typeDiplome") String typeDiplomeId,
                                     @QueryParam("typeFormation") String typeFormationId, @QueryParam("dateObtention") String dateObtentionStr,
                                     @QueryParam("paysObtention") String paysObtentionId, @QueryParam("dateReconnaissance") String dateReconnaissanceStr) throws Exception {

        LOGGER.info("ajouterUnDiplome " + keyDiplome);

        ReferenceDeDemande referenceDeDemande = new ReferenceDeDemande(demandeReference);
        ReferenceDeDiplome referenceDeDiplome = new ReferenceDeDiplome(keyDiplome);
        TypeDiplomeAccepte typeDiplomeAccepte = TypeDiplomeAccepte.getTypeById(Integer.parseInt(typeDiplomeId));
        TitreFormation titreFormation = new TitreFormation(convertTypeFormationIdToEnum(typeDiplomeAccepte, typeFormationId).name());
        DateObtention dateObtention = new DateObtention(SHORT_DATE_FORMATTER.parseLocalDate(dateObtentionStr));
        PaysObtention paysObtention = new PaysObtention(Pays.getTypeById(Integer.parseInt(paysObtentionId)).name());
        DateReconnaissance dateReconnaissance = null;
        if (!StringUtils.isEmpty(dateReconnaissanceStr) && !"-".equals(dateReconnaissanceStr)) {
            dateReconnaissance = new DateReconnaissance(SHORT_DATE_FORMATTER.parseLocalDate(dateReconnaissanceStr));
        }

        donneesProfessionnellesService.ajouterUnDiplome(referenceDeDemande, referenceDeDiplome, typeDiplomeAccepte, titreFormation, dateObtention, paysObtention, dateReconnaissance);
        return RestUtils.forgeResponseTrue();
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
    @Path("/supprimer/{demandeReference}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("USER")
    public Response supprimerUnDiplome(@Context UriInfo uriInfo,
                                       @PathParam("demandeReference") String demandeReference,
                                       @QueryParam("keyDiplome") String keyDiplome) throws Exception {

        LOGGER.info("supprimerUnDiplome " + keyDiplome);

        ReferenceDeDemande referenceDeDemande = new ReferenceDeDemande(demandeReference);
        ReferenceDeDiplome referenceDeDiplome = new ReferenceDeDiplome(keyDiplome);
        donneesProfessionnellesService.supprimerUnDiplome(referenceDeDemande, referenceDeDiplome);
        return RestUtils.forgeResponseTrue();
    }
}
