package ch.vd.demaut.cucumber.steps;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import ch.vd.demaut.cucumber.converters.commons.AccepteOuRefuse;
import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.AnnexeNonValideException;
import ch.vd.demaut.domain.annexes.ListeDesAnnexes;
import ch.vd.demaut.domain.annexes.NomFichier;

public class AnnexesSteps {

    // ********************************************************* Static fields

    // ********************************************************* Fields
    //Beans Initialisés pas Spring Context

    private DemandeAutorisationSteps demandeAutorisationSteps;

    private AccepteOuRefuse actualAcceptationAnnexe;

    // ********************************************************* Technical
    // methods

    public DemandeAutorisationSteps getDemandeAutorisationSteps() {
        return demandeAutorisationSteps;
    }

    public void setDemandeAutorisationSteps(DemandeAutorisationSteps demandeAutorisationSteps) {
        this.demandeAutorisationSteps = demandeAutorisationSteps;
    }

    // ********************************************************* Methods

    public void ajouterAnnexesADemandeEnCours(ListeDesAnnexes listeDesAnnexesInitiales) {
        Collection<Annexe> annexesInit = listeDesAnnexesInitiales.listerAnnexes();
        for (Annexe annexe : annexesInit) {
            demandeAutorisationSteps.getDemandeEnCours().validerEtAttacherAnnexe(annexe);
        }
        assertThat(demandeAutorisationSteps.getDemandeEnCours().listerLesAnnexes()).hasSameSizeAs(annexesInit);
    }

    public void attacherUneAnnexe(Annexe annexe) {
        try {
            demandeAutorisationSteps.getDemandeEnCours().validerEtAttacherAnnexe(annexe);
            accepteAnnexe();
        } catch (AnnexeNonValideException e) {
            refuseAnnexe();
        }
    }


    public void supprimerAnnexe(String nomDuFichier, String typeAnnexe) {
        NomFichier nomFichier = new NomFichier(nomDuFichier);
        demandeAutorisationSteps.getDemandeEnCours().supprimerUneAnnexeParNomFichier(nomFichier);
        accepteAnnexe();
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
