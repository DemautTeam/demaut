package ch.vd.demaut.cucumber.steps.definitions;

import ch.vd.demaut.cucumber.converteurs.donneespro.CodeGLNConverter;
import ch.vd.demaut.cucumber.steps.DemandeAutorisationSteps;
import ch.vd.demaut.cucumber.steps.DonneesProfessionnellesSteps;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import cucumber.api.Transform;
import cucumber.api.java.fr.Lorsque;

/**
 * Step definitions pour la fonctionnalité "Saisir la Profession"
 */
public class SaisirProfessionStepDefinitions extends StepDefinitions {

    // ********************************************************* Fields
    private DonneesProfessionnellesSteps donneesProfessionnellesSteps;

    // ********************************************************* Technical
    // methods

    public DonneesProfessionnellesSteps getDonneesProfessionnellesSteps() {
        return donneesProfessionnellesSteps;
    }

    public void setDonneesProfessionnellesSteps(DonneesProfessionnellesSteps donneesProfessionnellesSteps) {
        this.donneesProfessionnellesSteps = donneesProfessionnellesSteps;
    }

    public DemandeAutorisationSteps getDemandeAutorisationSteps() {
        return donneesProfessionnellesSteps.getDemandeAutorisationSteps();
    }

    // ********************************************************* Before

    // ********************************************************* Given

    // ********************************************************* When

    @Lorsque("^l´utilisateur saisit le code gln \"([^\"]*)\"$")
    public void l_utilisateur_saisit_code_gln(@Transform(CodeGLNConverter.class) CodeGLN gln) throws Throwable {
        
    }

    // ********************************************************* Then

}
