package ch.vd.demaut.cucumber.steps;

import ch.vd.demaut.commons.bdd.AccepteOuRefuse;
import ch.vd.demaut.domain.demandeur.activiteProf.ActiviteProfessionnelleEnvisage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActivitesProfessionnellesSteps {

    // ********************************************************* Static fields
    private final Logger logger = LoggerFactory.getLogger(getClass());

    // ********************************************************* Fields

    private ActiviteProfessionnelleEnvisage currentDonnees;

    private AccepteOuRefuse actionActuelle;
    private DemandeAutorisationSteps demandeAutorisationSteps;

    // *********************************************** Technical Methods

    public DemandeAutorisationSteps getDemandeAutorisationSteps() {
        return demandeAutorisationSteps;
    }

    public void setDemandeAutorisationSteps(DemandeAutorisationSteps demandeAutorisationSteps) {
        this.demandeAutorisationSteps = demandeAutorisationSteps;
    }

    public void initDemandeur() {


    }




    //************************************************************



}
