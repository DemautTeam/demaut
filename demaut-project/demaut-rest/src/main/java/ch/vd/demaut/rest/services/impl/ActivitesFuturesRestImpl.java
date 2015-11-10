package ch.vd.demaut.rest.services.impl;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandeur.Email;
import ch.vd.demaut.domain.demandeur.Localite;
import ch.vd.demaut.domain.demandeur.Pays;
import ch.vd.demaut.domain.demandeur.Telephone;
import ch.vd.demaut.domain.demandeur.donneesPerso.Adresse;
import ch.vd.demaut.domain.demandeur.donneesPerso.DateDeNaissance;
import ch.vd.demaut.domain.demandeur.donneesPerso.Genre;
import ch.vd.demaut.domain.demandeur.donneesPerso.Nom;
import ch.vd.demaut.domain.demandeur.donneesPerso.Prenom;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.ActiviteFuture;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.ActiviteFutureFK;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.Complement;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.Etablissement;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.ListeDesActivitesFutures;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.NPAProfessionnel;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.SiteInternet;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.TypeActivite;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.TypePratiqueLamal;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.Voie;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.envisagee.ActiviteEnvisagee;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.envisagee.DatePrevueDebut;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.envisagee.Superviseur;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.envisagee.TauxActiviteEnDemiJournee;
import ch.vd.demaut.rest.json.commons.RestUtils;
import ch.vd.demaut.rest.services.ActivitesFuturesRest;
import ch.vd.demaut.services.demandeurs.donneesProf.activites.ActivitesFuturesService;

/**
 * Implementation du service REST {@link ActivitesFuturesRest}
 */
public class ActivitesFuturesRestImpl implements ActivitesFuturesRest {

    // ********************************************************* Static fields
    private final Logger logger = LoggerFactory.getLogger(getClass());

    // ********************************************************* Injected fields
    private ActivitesFuturesService activitesFuturesService;

    // ********************************************************* Implem Methodes REST
    @Override
    public Response listerLesActivitesFutures(ReferenceDeDemande referenceDeDemande) {
        ListeDesActivitesFutures listeDesActivitesFutures = activitesFuturesService
                .listerLesActivitesFutures(referenceDeDemande);
        return RestUtils.buildJSonResponse(listeDesActivitesFutures.listerActivitesFutures());
    }

    @Override
    public Response ajouterActiviteFuture(ReferenceDeDemande referenceDeDemande, //
            String nomEtablissementStr, //
            String voieStr, //
            String complementStr, //
            String localiteStr, //
            String npaProfessionnelStr, //
            String telephoneProfStr, //
            String telephoneMobileStr, //
            String faxStr, //
            String emailStr, //
            String siteInternetStr, //
            String typePratiqueLamalStr, //
            String typeActiviteStr, //
            Integer nombreJourParSemaine, //
            String datePrevueDebutStr, //
            String superviseurStr) {
        logger.debug("/ajouter - Reference de la demande : {}", referenceDeDemande);

        Nom nomEtablissement = new Nom(nomEtablissementStr);
        Voie voie = new Voie(voieStr);
        Complement complement = new Complement(complementStr);
        Localite localite = new Localite(localiteStr);
        NPAProfessionnel npaProfessionnel = new NPAProfessionnel(npaProfessionnelStr);
        Telephone telephoneProf = new Telephone(telephoneProfStr);
        Telephone telephoneMobile = StringUtils.isEmpty(telephoneMobileStr) ? null : new Telephone(telephoneMobileStr);
        Telephone fax = StringUtils.isEmpty(faxStr) ? null : new Telephone(faxStr);
        Email email = new Email(emailStr);
        SiteInternet siteInternet = new SiteInternet(siteInternetStr);
        TypePratiqueLamal typePratiqueLamal = TypePratiqueLamal.valueOf(typePratiqueLamalStr);
        TypeActivite typeActivite = TypeActivite.valueOf(typeActiviteStr);
        TauxActiviteEnDemiJournee tauxActiviteEnDemiJournee = new TauxActiviteEnDemiJournee(nombreJourParSemaine);
        DatePrevueDebut datePrevueDebut = new DatePrevueDebut(
                DiplomeRestImpl.SHORT_DATE_PARSER.parseLocalDate(datePrevueDebutStr));
        Superviseur superviseur = StringUtils.isEmpty(superviseurStr) ? null : new Superviseur(superviseurStr);
        
        Etablissement etablissement = new Etablissement(nomEtablissement, voie, complement, localite, npaProfessionnel, telephoneProf, telephoneMobile, fax, email, siteInternet);
        ActiviteEnvisagee activiteEnvisagee = new ActiviteEnvisagee(typeActivite, tauxActiviteEnDemiJournee, datePrevueDebut, superviseur);
        ActiviteFuture activiteFuture = new ActiviteFuture(etablissement, typePratiqueLamal, activiteEnvisagee);
        
        activitesFuturesService.ajouterActiviteFuture(referenceDeDemande, activiteFuture);
        
        return RestUtils.buildJSonResponse(true);
    }

    @Override
    public Response supprimerActiviteFuture(@FormParam("referenceDeDemande") ReferenceDeDemande referenceDeDemande,
            @FormParam("activiteFutureFK") ActiviteFutureFK activiteFutureFK) {
        logger.debug("/supprimer - Reference de la demande : {} ActiviteFK : {}", referenceDeDemande, activiteFutureFK);
        activitesFuturesService.supprimerActiviteFuture(referenceDeDemande, activiteFutureFK);
        return RestUtils.buildJSonResponse("OK");
    }

    // ********************************************************* Setters for injection
    public void setActivitesFuturesService(ActivitesFuturesService activitesFuturesService) {
        this.activitesFuturesService = activitesFuturesService;
    }

}
