package ch.vd.demaut.cucumber.steps.definitions;

import ch.vd.demaut.cucumber.converters.demandeurs.NomEtPrenomDemandeurConverter;
import ch.vd.demaut.cucumber.steps.DemandeAutorisationSteps;
import ch.vd.demaut.domain.demandes.autorisation.ProfessionDeLaSante;
import ch.vd.demaut.domain.demandes.autorisation.StatutDemandeAutorisation;
import ch.vd.demaut.domain.demandeurs.NomEtPrenomDemandeur;
import cucumber.api.Transform;
import cucumber.api.java.fr.Alors;
import cucumber.api.java.fr.Lorsque;

/**
 * Step definitions pour la fonctionnalité "Attacher des annexes"
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
    @Lorsque("^que le demandeur initialise une demande de type \"([^\"]*)\"$")
    public void que_le_demandeur_initialise_une_demande_de_type(ProfessionDeLaSante profession) throws Throwable {
        demandeAutorisationSteps.initialiserDemandeEnCours(profession);
    }

    // ********************************************************* Then

    @Alors("^le système Demaut crée la demande avec les caractéristiques \\[état: \"([^\"]*)\", demandeur: \"([^\"]*)\", type: \"([^\"]*)\"\\]$")
    public void le_système_Demaut_crée_la_demande_avec_les_caractéristiques_état_demandeur_type(
            StatutDemandeAutorisation statut,
            @Transform(NomEtPrenomDemandeurConverter.class) NomEtPrenomDemandeur nomEtPrenom,
            ProfessionDeLaSante profession) throws Throwable {
        demandeAutorisationSteps.verifieDemandeCree(profession);
    }
}
