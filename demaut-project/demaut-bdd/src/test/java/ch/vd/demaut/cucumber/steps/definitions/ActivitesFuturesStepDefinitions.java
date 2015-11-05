package ch.vd.demaut.cucumber.steps.definitions;

import ch.vd.demaut.commons.bdd.AccepteOuRefuse;
import ch.vd.demaut.cucumber.converteurs.commons.NPAProfessionnelConverter;
import ch.vd.demaut.cucumber.steps.ActivitesFuturesSteps;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.NPAProfessionnel;
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
    
    // ********************************************************* Setters (for Injection)
    
    public void setActivitesFuturesSteps(ActivitesFuturesSteps activitesFuturesSteps) {
        this.activitesFuturesSteps = activitesFuturesSteps;
    }
    

}
