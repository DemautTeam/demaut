package ch.vd.demaut.cucumber.steps.definitions;

import ch.vd.demaut.cucumber.steps.DonneesProfessionnellesSteps;
import cucumber.api.PendingException;
import cucumber.api.java.fr.Alors;
import cucumber.api.java.fr.Lorsque;

/**
 * Step definitions pour la fonctionnalité "Saisir les DonneePerso"
 */
public class SaisirDonneeProfStepDefinitions extends StepDefinitions {

    // ********************************************************* Fields
    private DonneesProfessionnellesSteps donneesProfessionnellesSteps;

    // ********************************************************* Technical methods


    // ********************************************************* Before

    // ********************************************************* Given


    // ********************************************************* When

    // ********************************************************* Then

    @Alors("^le système Demaut \"([^\"]*)\" les données professionnelles")
    public void le_système_Demaut_les_données_professionnelles(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }


    @Alors("^le système Demaut crée la demande avec les données professionnelles$")
    public void le_système_Demaut_crée_la_demande_avec_les_données_professionnelles() throws Throwable {
        // Express the Regexp above with the code you wish you had
    }
}
