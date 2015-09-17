package ch.vd.demaut.cucumber.steps.definitions;

import cucumber.api.java.fr.Alors;
import cucumber.api.java.fr.Lorsque;

/**
 * Step definitions pour la fonctionnalité "Attacher des annexes"
 */
public class SaisirDonneePersoStepDefinitions extends StepDefinitions {

    // ********************************************************* Fields


    // ********************************************************* Technical
    // methods


    // ********************************************************* Before

    // ********************************************************* Given


    // ********************************************************* When

    @Lorsque("^l´utilisateur saisit son nom \"([^\"]*)\"$")
    public void l_utilisateur_saisit_son_nom(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }

    // ********************************************************* Then

    @Alors("^le système Demaut \"([^\"]*)\" les données personnelles$")
    public void le_système_Demaut_les_données_personnelles(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }


}
