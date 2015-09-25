package ch.vd.demaut.cucumber.steps;

import ch.vd.demaut.domain.demandes.autorisation.ProfessionDeLaSante;
import ch.vd.demaut.domain.demandes.autorisation.StatutDemandeAutorisation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DonneesDiplomesSteps {

    // ********************************************************* Static fields
    private static final Logger LOGGER = LoggerFactory.getLogger(DonneesDiplomesSteps.class);

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
