package ch.vd.demaut.cucumber.steps.definitions;

import ch.vd.demaut.commons.bdd.AccepteOuRefuse;
import ch.vd.demaut.cucumber.converteurs.commons.*;
import ch.vd.demaut.cucumber.converteurs.donneesperso.NomConverter;
import ch.vd.demaut.cucumber.steps.ActivitesFuturesSteps;
import ch.vd.demaut.domain.demandeur.Email;
import ch.vd.demaut.domain.demandeur.Localite;
import ch.vd.demaut.domain.demandeur.Telephone;
import ch.vd.demaut.domain.demandeur.donneesPerso.Nom;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.NPAProfessionnel;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.Voie;
import cucumber.api.Transform;
import cucumber.api.java.fr.Alors;
import cucumber.api.java.fr.Etantdonné;
import cucumber.api.java.fr.Lorsque;

/**
 * Step definitions pour la "Saisie d'activités futures"
 */
public class ActivitesFuturesStepDefinitions extends StepDefinitions {


    // ********************************************************* Fields

    private ActivitesFuturesSteps activitesFuturesSteps;

    // ********************************************************* Given

    @Etantdonné("^une activité future valide en cours de saisie par l´utilisateur$")
    public void init_activite_future() throws Throwable {
        activitesFuturesSteps.initValeursValidesPourActiviteFuture();
    }

    @Etantdonné("^le code NPA \"([^\"]*)\" renseigné par l´utilisateur$")
    public void le_code_NPA_npa_renseigné_par_l_utilisateur(@Transform(NPAProfessionnelConverter.class) NPAProfessionnel npaProfessionnel){
        activitesFuturesSteps.renseignerNPA(npaProfessionnel);
    }

    @Etantdonné("^le numéro de téléphone ([^\"]*) professionnel, le téléphone ([^\"]*) mobile et le numéro de fax ([^\"]*) renseignés par l´utilisateur$")
    public void numeros_de_tel(@Transform(TelephoneConverter.class) Telephone tel,
                               @Transform(TelephoneConverter.class) Telephone mobile,
                               @Transform(TelephoneConverter.class) Telephone fax) {
        activitesFuturesSteps.renseignerTelephoneProf(tel);
        activitesFuturesSteps.renseignerTelephoneMobile(mobile);
        activitesFuturesSteps.renseignerFax(fax);
    }

    @Etantdonné("^l´email ([^\"]*) renseignés par l´utilisateur$")
    public void l_email_email_renseignés_par_l_utilisateur(@Transform(EmailConverter.class) Email email)  {
        activitesFuturesSteps.renseignerEmail(email);
    }


    @Etantdonné("^le nom de l´établissement ([^\\\"]*) renseignés par l´utilisateur$")
    public void le_nom_de_l_établissement_nom_renseignés_par_l_utilisateur(@Transform(NomConverter.class) Nom
                                                                                       nomEtablissement){
        activitesFuturesSteps.renseignerNomEtablissement(nomEtablissement);
    }

    @Etantdonné("^les champs requis nom ([^\\\"]*), adresse ([^\\\"]*), npa ([^\\\"]*), localité ([^\\\"]*), téléphone " +
            "([^\\\"]*), email ([^\\\"]*), renseignés par l´utilisateur$")
    public void noom_adresse_npa_localité_La(@Transform(NomConverter.class) Nom nom,
                                             @Transform(VoieConverter.class) Voie voie,
                                             @Transform(NPAProfessionnelConverter.class) NPAProfessionnel npa,
                                             @Transform(LocaliteConverter.class) Localite localite,
                                             @Transform(TelephoneConverter.class) Telephone telephone,
                                             @Transform(EmailConverter.class) Email email) {
        activitesFuturesSteps.renseignerNomEtablissement(nom);
        activitesFuturesSteps.renseignerVoie(voie);
        activitesFuturesSteps.renseignerNPA(npa);
        activitesFuturesSteps.renseignerLocalite(localite);
        activitesFuturesSteps.renseignerTelephoneProf(telephone);
        activitesFuturesSteps.renseignerEmail(email);
    }


    // ********************************************************* When

    @Lorsque("^le demandeur ajoute cette activité future$")
    public void le_demandeur_valide_ses_activités_futures() throws Throwable {
        activitesFuturesSteps.creerActiviteFuture();
        activitesFuturesSteps.ajouterActiviteFutureCourante();
    }

    // ********************************************************* Then

    @Alors("^le système Demaut (accepte|refuse) d´ajouter cette activité future à la demande en cours$")
    public void le_système_Demaut_action_ce_code_NPA_en_tant_que_NPA_Suisse_avec_caractères(AccepteOuRefuse action) {
        activitesFuturesSteps.verifieAcceptationActiviteFuture(action);
    }

    @Alors("^le système Demaut (accepte|refuse) les numéros de télephone$")
    public void le_système_Demaut_action_le_numéro_de_télephone_qui_requiert_minimum_digits(AccepteOuRefuse action){
        activitesFuturesSteps.verifieAcceptationActiviteFuture(action);
    }

    @Alors("^le système Demaut (accepte|refuse) le nom de l'établissement$")
    public void le_système_Demaut_action_le_nom_de_l_établissement(AccepteOuRefuse action) {
        activitesFuturesSteps.verifieAcceptationActiviteFuture(action);
    }

    @Alors("^le système Demaut (accepte|refuse) le formulaires d'activité futures$")
    public void le_système_Demaut_action_le_formulaires_d_activité_futures(AccepteOuRefuse action) {
        activitesFuturesSteps.verifieAcceptationActiviteFuture(action);
    }


    // ********************************************************* Setters (for Injection)

    public void setActivitesFuturesSteps(ActivitesFuturesSteps activitesFuturesSteps) {
        this.activitesFuturesSteps = activitesFuturesSteps;
    }

}
