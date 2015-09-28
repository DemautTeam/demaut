package ch.vd.demaut.cucumber.steps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.vd.demaut.domain.demandeur.donneesPerso.DonneesPersonnelles;
import ch.vd.demaut.domain.demandeur.donneesPerso.DonneesPersonnellesValidateur;

@SuppressWarnings("unused")
//TOD: Remove this suppress warnings once implemented
public class DonneesPersonnellesSteps {
    // ********************************************************* Static fields
    private final Logger logger = LoggerFactory.getLogger(getClass());

    // ********************************************************* Fields
    private DonneesPersonnellesValidateur donneesPersonnellesValidateur;

    private DemandeAutorisationSteps demandeAutorisationSteps;
    private DonneesPersonnelles donneesPersonnelles;

    public void setDemandeAutorisationSteps(DemandeAutorisationSteps demandeAutorisationSteps) {
        this.demandeAutorisationSteps = demandeAutorisationSteps;
    }


}
