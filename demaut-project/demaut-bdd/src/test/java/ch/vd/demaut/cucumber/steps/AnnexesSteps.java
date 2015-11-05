package ch.vd.demaut.cucumber.steps;

import ch.vd.demaut.commons.bdd.AccepteOuRefuse;
import ch.vd.demaut.commons.exceptions.EntityNotUniqueException;
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

    private DonneesPersonnellesSteps donneesPersonnellesSteps;

    private DonneesProfessionnellesSteps donneesProfessionnellesSteps;

    // ********************************************************* Fields

    private DemandeAutorisation demandeEnCours;

    private AccepteOuRefuse actualAcceptationAnnexe;

    // ********************************************************* Methods


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
        } catch (AnnexeNonValideException | AnnexeNonUniqueException | EntityNotUniqueException e) {
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
    
    //TODO: Mutualiser cela
    public void verifieAcceptationAnnexe(AccepteOuRefuse expectedAcceptationAnnexe) {
        assertThat(actualAcceptationAnnexe).isEqualTo(expectedAcceptationAnnexe);
    }

    // ***************************** **************************** Technical
    // methods

    public void setDemandeAutorisationSteps(DemandeAutorisationSteps demandeAutorisationSteps) {
        this.demandeAutorisationSteps = demandeAutorisationSteps;
    }
    
    public DemandeAutorisationSteps getDemandeAutorisationSteps() {
        return demandeAutorisationSteps;
    }
    
    public void setDonneesPersonnellesSteps(DonneesPersonnellesSteps donneesPersonnellesSteps) {
        this.donneesPersonnellesSteps = donneesPersonnellesSteps;
    }
    
    public DonneesPersonnellesSteps getDonneesPersonnellesSteps() {
        return donneesPersonnellesSteps;
    }

    public DonneesProfessionnellesSteps getDonneesProfessionnellesSteps() {
        return donneesProfessionnellesSteps;
    }
    
    public void setDonneesProfessionnellesSteps(DonneesProfessionnellesSteps donneesProfessionnellesSteps) {
        this.donneesProfessionnellesSteps = donneesProfessionnellesSteps;
    }
    
    // ********************************************************* Private methods
    
    private void accepteAnnexe() {
        actualAcceptationAnnexe = AccepteOuRefuse.accepte;
    }

    private void refuseAnnexe() {
        actualAcceptationAnnexe = AccepteOuRefuse.refuse;
    }
}
