package ch.vd.demaut.cucumber.steps;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import ch.vd.demaut.cucumber.converteurs.commons.AccepteOuRefuse;
import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.AnnexeFK;
import ch.vd.demaut.domain.annexes.ListeDesAnnexes;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.exception.AnnexeIntrouvableException;
import ch.vd.demaut.domain.exception.AnnexeNonUniqueException;
import ch.vd.demaut.domain.exception.AnnexeNonValideException;

public class AnnexesSteps {

    // ********************************************************* Static fields

    // ********************************************************* Fields

    private DemandeAutorisation demandeEnCours;

    private AccepteOuRefuse actualAcceptationAnnexe;

    // ********************************************************* Methods

    public void initialiserDemandeEnCours(DemandeAutorisation demandeEnCours) {
        this.demandeEnCours = demandeEnCours;
    }

    public void ajouterAnnexesADemandeEnCours(ListeDesAnnexes listeDesAnnexesInitiales) {
        Collection<Annexe> annexesInit = listeDesAnnexesInitiales.listerAnnexes();
        for (Annexe annexe : annexesInit) {
            demandeEnCours.validerEtAttacherAnnexe(annexe);
        }
        assertThat(demandeEnCours.listerLesAnnexes()).hasSameSizeAs(annexesInit);
    }

    public void attacherUneAnnexe(Annexe annexe) {
        try {
            demandeEnCours.validerEtAttacherAnnexe(annexe);
            accepteAnnexe();
        } catch (AnnexeNonValideException | AnnexeNonUniqueException e) {
            refuseAnnexe();
        }
    }

    public void supprimerAnnexe(AnnexeFK annexeFK) {
        try {
            demandeEnCours.supprimerUneAnnexe(annexeFK);
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
