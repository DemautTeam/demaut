package ch.vd.demaut.cucumber.steps.definitions;

import ch.vd.demaut.cucumber.steps.DonneesProfessionnellesSteps;
import cucumber.api.PendingException;
import cucumber.api.java.fr.Alors;
import cucumber.api.java.fr.Etantdonné;
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

    @Etantdonné("^aucune demande de profession \"([^\"]*)\" en cours de saisie à l´état \"([^\"]*)\"$")
    public void aucune_demande_de_profession_en_cours_de_saisie_à_l_état(String arg1, String arg2) throws Throwable {
        // Express the Regexp above with the code you wish you had
        throw new PendingException();
    }

    @Etantdonné("^un demandeur de profession \"([^\"]*)\"$")
    public void un_demandeur_de_profession(String profession) throws Throwable {
        // Express the Regexp above with the code you wish you had
        throw new PendingException();
    }

    // ********************************************************* When

    @Lorsque("^que l´utilisateur initialise une demande de profession \"([^\"]*)\"$")
    public void que_l_utilisateur_initialise_une_demande_de_profession(String arg1) throws Throwable {
        // Express the Regexp above with the code you wish you had
        throw new PendingException();
    }

    // ********************************************************* Then


    @Alors("^le système Demaut \"([^\"]*)\" les données professionnelles")
    public void le_système_Demaut_les_données_professionnelles(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }


    @Alors("^le système Demaut crée la demande avec les données professionnelles$")
    public void le_système_Demaut_crée_la_demande_avec_les_données_professionnelles() throws Throwable {
        // Express the Regexp above with the code you wish you had
    }

    @Etantdonné("^une demande de profession \"([^\"]*)\" en cours de saisie à l´état \"([^\"]*)\"$")
    public void une_demande_de_profession_en_cours_de_saisie_à_l_état(String arg1, String arg2) throws Throwable {
        // Express the Regexp above with the code you wish you had
        throw new PendingException();
    }
}
