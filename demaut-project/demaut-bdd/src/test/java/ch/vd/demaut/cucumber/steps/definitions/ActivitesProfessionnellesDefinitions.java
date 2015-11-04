package ch.vd.demaut.cucumber.steps.definitions;

import ch.vd.demaut.cucumber.converteurs.commons.NPAConverter;
import ch.vd.demaut.cucumber.steps.ActivitesProfessionnellesSteps;
import ch.vd.demaut.domain.demandeur.donneesPerso.NPA;
import cucumber.api.Transform;
import cucumber.api.java.fr.Etantdonné;

/**
 * Step definitions pour la "Saisie d'activités professionnelles"
 */
public class ActivitesProfessionnellesDefinitions extends StepDefinitions {


    // ********************************************************* Fields

    private ActivitesProfessionnellesSteps activitesProfessionnellesSteps;

    // ********************************************************* Technical
    // methods

    public void setActivitesProfessionnellesSteps(ActivitesProfessionnellesSteps activitesProfessionnellesSteps) {
        this.activitesProfessionnellesSteps = activitesProfessionnellesSteps;
    }

    // ********************************************************* Given



    @Etantdonné("^un NPA prof. de format numérique et de (\\d+) caractères$")
    public void un_NPA_prof_de_format_numérique_et_de_caractères(@Transform(NPAConverter.class) NPA npa) throws Throwable {
        activitesProfessionnellesSteps.initDemandeur();
        //activitesProfessionnellesSteps.setNPA(npa);


    }


}
