package ch.vd.demaut.cucumber.steps.definitions;

import ch.vd.demaut.commons.bdd.AccepteOuRefuse;
import ch.vd.demaut.cucumber.converteurs.activitesFutures.TelephoneMobileConverter;
import ch.vd.demaut.cucumber.converteurs.commons.FaxConverter;
import ch.vd.demaut.cucumber.converteurs.commons.NPAProfessionnelConverter;
import ch.vd.demaut.cucumber.converteurs.donneesperso.TelephonePriveConverter;
import ch.vd.demaut.cucumber.steps.ActivitesFuturesSteps;
import ch.vd.demaut.domain.demandeur.Fax;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.NPAProfessionnel;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.etablissement.TelephoneMobile;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.etablissement.TelephonePrive;
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

    // ********************************************************* Technical
    // methods

    public void setActivitesProfessionnellesSteps(ActivitesFuturesSteps activitesProfessionnellesSteps) {
        this.activitesFuturesSteps = activitesProfessionnellesSteps;
    }

    // ********************************************************* Given


    @Etantdonné("^le code NPA \"([^\"]*)\" renseigné par l´utilisateur$")
    public void le_code_NPA_npa_renseigné_par_l_utilisateur(@Transform(NPAProfessionnelConverter.class) NPAProfessionnel npaProfessionnel) throws Throwable {
        activitesFuturesSteps.initActiviteNPA(npaProfessionnel);
    }

    @Etantdonné("^que tous les autres champs de l´activité future sont remplis et valides$")
    public void que_tous_les_autres_champs_de_l_activité_future_sont_remplis_et_valides() throws Throwable {
        activitesFuturesSteps.initActiviteFutureValide();
    }

    @Etantdonné("^le numéro de téléphone \"([^\"]*)\" professionnel, le téléphone \"([^\"]*)\" mobile et le numéro de fax \"([^\"]*)\" renseigné par l'utilisateur$")
    public void le_numéro_de_téléphone_tel_professionnel_le_téléphone_mobile_mobile_et_le_numéro_de_fax_fax_renseigné_par_l_utilisateur(@Transform(TelephonePriveConverter.class) TelephonePrive tel, @Transform(TelephoneMobileConverter.class) TelephoneMobile mobile, @Transform(FaxConverter.class) Fax fax) throws Throwable {
        activitesFuturesSteps.initActiviteTelephoneProf(tel);
        activitesFuturesSteps.initActiviteTelephoneMobile(mobile);
        activitesFuturesSteps.initActiviteFax(fax);
    }


    // ********************************************************* When

    @Lorsque("^le demandeur ajoute cette activité future$")
    public void le_demandeur_valide_ses_activités_futures() throws Throwable {
        activitesFuturesSteps.ajouterActiviteFutureCourante();
    }

    // ********************************************************* Then

    @Alors("^le système Demaut (accepte|refuse) ce code NPA en tant que NPA Suisse avec quatre caractères$")
    public void le_système_Demaut_action_ce_code_NPA_en_tant_que_NPA_Suisse_avec_caractères(AccepteOuRefuse action) {
        activitesFuturesSteps.verifieAcceptationActiviteFuture(action);
    }

    @Alors("^le système Demaut (accepte|refuse) le numéro de télephone qui requiert minimum 5 digits$")
    public void le_système_Demaut_action_le_numéro_de_télephone_qui_requiert_minimum_digits(AccepteOuRefuse action) throws Throwable {
        activitesFuturesSteps.verifieAcceptationActiviteFuture(action);
    }


    // ********************************************************* Setters (for Injection)

    public void setActivitesFuturesSteps(ActivitesFuturesSteps activitesFuturesSteps) {
        this.activitesFuturesSteps = activitesFuturesSteps;
    }

}
