package ch.vd.demaut.cucumber.steps.definitions;

import ch.vd.demaut.cucumber.converteurs.utilisateurs.LoginConverter;
import ch.vd.demaut.cucumber.steps.DemandeAutorisationSteps;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandes.autorisation.StatutDemandeAutorisation;
import ch.vd.demaut.domain.utilisateurs.Login;
import cucumber.api.Transform;
import cucumber.api.java.fr.Alors;
import cucumber.api.java.fr.Lorsque;

/**
 * Step definitions pour la fonctionnalité "Creation d'une Demande"
 */
public class CreationDemandeStepDefinitions extends StepDefinitions {

    // ********************************************************* Fields

    private DemandeAutorisationSteps demandeAutorisationSteps;

    // ********************************************************* Technical methods

    public DemandeAutorisationSteps getDemandeAutorisationSteps() {
        return demandeAutorisationSteps;
    }

    public void setDemandeAutorisationSteps(DemandeAutorisationSteps demandeAutorisationSteps) {
        this.demandeAutorisationSteps = demandeAutorisationSteps;
    }

    // ********************************************************* Given

    // ********************************************************* When
    @Lorsque("^l´utilisateur initialise une demande de profession \"([^\"]*)\"$")
    public void lutilisateur_initialise_une_demande_de_profession(Profession profession) throws Throwable {
        demandeAutorisationSteps.initialiserDemandeEnCours(profession);
    }

    // ********************************************************* Then

    @Alors("^le système Demaut crée la demande avec les caractéristiques \\[état: \"([^\"]*)\", utilisateur: \"([^\"]*)\", type: \"([^\"]*)\"\\]$")
    public void le_système_Demaut_crée_la_demande_avec_les_caractéristiques_état_utilisateur_type(
            StatutDemandeAutorisation statut,
            @Transform(LoginConverter.class) Login login,
            Profession profession) throws Throwable {
        demandeAutorisationSteps.verifieDemandeCree(profession, statut, login);
    }
}
