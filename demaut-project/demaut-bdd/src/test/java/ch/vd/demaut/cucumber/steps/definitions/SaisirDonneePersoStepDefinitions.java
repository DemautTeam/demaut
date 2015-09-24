package ch.vd.demaut.cucumber.steps.definitions;

import ch.vd.demaut.commons.utils.FileMockHelper;
import ch.vd.demaut.cucumber.converters.commons.AccepteOuRefuse;
import ch.vd.demaut.cucumber.converters.commons.OuiNonConverter;
import ch.vd.demaut.cucumber.converters.utilisateurs.LoginConverter;
import ch.vd.demaut.cucumber.converteurs.annexes.ListeDesAnnexesConverter;
import ch.vd.demaut.cucumber.steps.DemandeAutorisationSteps;
import ch.vd.demaut.domain.annexes.*;
import ch.vd.demaut.domain.demandes.autorisation.ProfessionDeLaSante;
import ch.vd.demaut.domain.utilisateurs.Login;
import cucumber.api.DataTable;
import cucumber.api.Transform;
import cucumber.api.java.fr.Alors;
import cucumber.api.java.fr.Etantdonné;
import cucumber.api.java.fr.Lorsque;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

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
