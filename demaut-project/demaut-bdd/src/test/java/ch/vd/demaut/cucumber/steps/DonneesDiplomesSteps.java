package ch.vd.demaut.cucumber.steps;

import ch.vd.demaut.domain.demandes.autorisation.ProfessionDeLaSante;
import ch.vd.demaut.domain.demandes.autorisation.StatutDemandeAutorisation;

public class DonneesDiplomesSteps {

    // ********************************************************* Static fields

    // ********************************************************* Fields
    private DemandeAutorisationSteps demandeAutorisationSteps;

    private DonneesProfessionnellesSteps donneesProfessionnellesSteps;

    // ********************************************************* Technical methods

    public DemandeAutorisationSteps getDemandeAutorisationSteps() {
        return demandeAutorisationSteps;
    }

    public void setDemandeAutorisationSteps(DemandeAutorisationSteps demandeAutorisationSteps) {
        this.demandeAutorisationSteps = demandeAutorisationSteps;
    }

    public void verifieAucuneDemandeEnCours(ProfessionDeLaSante profession, StatutDemandeAutorisation statut) {
        demandeAutorisationSteps.verifieAucuneDemandeEnCours(profession, statut);
    }

    public DonneesProfessionnellesSteps getDonneesProfessionnellesSteps() {
        return donneesProfessionnellesSteps;
    }

    public void setDonneesProfessionnellesSteps(DonneesProfessionnellesSteps donneesProfessionnellesSteps) {
        this.donneesProfessionnellesSteps = donneesProfessionnellesSteps;
    }

    // ********************************************************* Methods


}
