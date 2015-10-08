package ch.vd.demaut.cucumber.steps;

import ch.vd.demaut.cucumber.converteurs.commons.AccepteOuRefuse;
import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.AnnexeFK;
import ch.vd.demaut.domain.annexes.ListeDesAnnexes;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.exception.AnnexeIntrouvableException;
import ch.vd.demaut.domain.exception.AnnexeNonUniqueException;
import ch.vd.demaut.domain.exception.AnnexeNonValideException;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class AnnexesSteps {

    // ********************************************************* Static fields
    private DemandeAutorisationSteps demandeAutorisationSteps;

    // ********************************************************* Fields

    private DemandeAutorisation demandeEnCours;

    private AccepteOuRefuse actualAcceptationAnnexe;

    // ********************************************************* Methods


    public void setDemandeAutorisationSteps(DemandeAutorisationSteps demandeAutorisationSteps) {
        this.demandeAutorisationSteps = demandeAutorisationSteps;
    }

    public DemandeAutorisation getDemandeEnCours() {
        if (demandeEnCours == null) {
            this.demandeEnCours = demandeAutorisationSteps.getDemandeEnCours();
        }
        return demandeEnCours;
    }

    public void ajouterAnnexesADemandeEnCours(ListeDesAnnexes listeDesAnnexesInitiales) {
        Collection<Annexe> annexesInit = listeDesAnnexesInitiales.listerAnnexes();
        for (Annexe annexe : annexesInit) {
            getDemandeEnCours().validerEtAttacherAnnexe(annexe);
        }
        assertThat(getDemandeEnCours().listerLesAnnexes()).hasSameSizeAs(annexesInit);
    }

    public void attacherUneAnnexe(Annexe annexe) {
        try {
            getDemandeEnCours().validerEtAttacherAnnexe(annexe);
            accepteAnnexe();
        } catch (AnnexeNonValideException | AnnexeNonUniqueException e) {
            refuseAnnexe();
        }
    }

    public void supprimerAnnexe(AnnexeFK annexeFK) {
        try {
            getDemandeEnCours().supprimerUneAnnexe(annexeFK);
            accepteAnnexe();
        } catch (AnnexeIntrouvableException e) {
            refuseAnnexe();
        }

    }

    // ***************************** **************************** Technical
    // methods

    public void verifieAcceptationAnnexe(AccepteOuRefuse expectedAcceptationAnnexe) {
        assertThat(actualAcceptationAnnexe).isEqualTo(expectedAcceptationAnnexe);
    }

    public void accepteAnnexe() {
        actualAcceptationAnnexe = AccepteOuRefuse.accepte;
    }

    public void refuseAnnexe() {
        actualAcceptationAnnexe = AccepteOuRefuse.refuse;
    }
}
