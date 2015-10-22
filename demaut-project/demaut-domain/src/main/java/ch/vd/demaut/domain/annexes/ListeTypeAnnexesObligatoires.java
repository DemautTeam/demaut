package ch.vd.demaut.domain.annexes;

import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandeur.Pays;
import ch.vd.demaut.domain.demandeur.donneesPerso.DonneesPersonnelles;
import ch.vd.demaut.domain.demandeur.donneesPerso.Langue;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.domain.demandeur.donneesProf.DonneesProfessionnelles;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.ActiviteFuture;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.ListeDesActivitesFutures;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.TypePratiqueLamal;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.Diplome;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.ListeDesDiplomes;
import ch.vd.demaut.domain.exception.DemandeNotFoundException;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Liste de types d'annexe ({@link Annexe}) obligatoire pour la complétude d'une {@link DemandeAutorisation}
 */
public class ListeTypeAnnexesObligatoires {

    // ********************************************************* Fields

    private final List<TypeAnnexe> typesAnnexe;

    private DemandeAutorisation demandeAutorisation;

    private List<Profession> professionsExigeantAnnexes;

    // ********************************************************* Constructor

    public ListeTypeAnnexesObligatoires() {
        typesAnnexe = new ArrayList<>();
    }

    public ListeTypeAnnexesObligatoires(List<TypeAnnexe> typeAnnexes) {
        this.typesAnnexe = typeAnnexes;
    }

    public ListeTypeAnnexesObligatoires(TypeAnnexe... typeAnnexes) {
        this(Arrays.asList(typeAnnexes));
    }

    public ListeTypeAnnexesObligatoires(DemandeAutorisation demandeAutorisation) {
        this.typesAnnexe = new ArrayList<>();
        this.demandeAutorisation = demandeAutorisation;
        this.professionsExigeantAnnexes = Profession.listerProfessionsExigeantAnnexes();
    }
    // ********************************************************* Methode metier

    public List<TypeAnnexe> listerTypesAnnexeObligatoires() {
        return Collections.unmodifiableList(typesAnnexe);
    }

    public ListeTypeAnnexesObligatoires determinerListeTypeAnnexesObligatoires() {
        if (demandeAutorisation != null) {
            if (professionsExigeantAnnexes.contains(demandeAutorisation.getProfession())) {
                determinerListeTypeAnnexesObligatoiresSelonProfession();
                determinerListeTypeAnnexesObligatoiresSelonDonneesPersonnelles();
                determinerListeTypeAnnexesObligatoiresSelonDonneesProfessionnelles();
                determinerListeTypeAnnexesObligatoiresSelonDonneesDiplomes();
                determinerListeTypeAnnexesObligatoiresSelonDonneesActivitesFutures();
                determinerListeTypeAnnexesObligatoiresSelonDonneesActivitesAnterieures();
                determinerListeTypeAnnexesObligatoiresExtra();
            }
        } else {
            throw new DemandeNotFoundException();
        }
        Collections.sort(typesAnnexe);
        return this;
    }

    private void determinerListeTypeAnnexesObligatoiresExtra() {
        typesAnnexe.add(TypeAnnexe.SpecimenSignature);
    }

    private void determinerListeTypeAnnexesObligatoiresSelonDonneesActivitesAnterieures() {
        // TODO
    }

    private void determinerListeTypeAnnexesObligatoiresSelonDonneesActivitesFutures() {
        DonneesProfessionnelles donneesProfessionnelles = demandeAutorisation.getDonneesProfessionnelles();
        if (donneesProfessionnelles != null) {
            ListeDesActivitesFutures listeDesActivitesFutures = donneesProfessionnelles.getActiviteFutures();
            if (listeDesActivitesFutures != null) {
                for (ActiviteFuture activiteFuture : listeDesActivitesFutures.listerActiviteFutures()) {
                    if (activiteFuture != null && TypePratiqueLamal.Non.equals(activiteFuture.getTypePratiqueLamal())) {
                        typesAnnexe.add(TypeAnnexe.Recusation);
                        break;
                    }
                }
            }
        }
    }

    private void determinerListeTypeAnnexesObligatoiresSelonDonneesDiplomes() {
        DonneesProfessionnelles donneesProfessionnelles = demandeAutorisation.getDonneesProfessionnelles();
        if (donneesProfessionnelles != null) {
            ListeDesDiplomes listeDesDiplomes = donneesProfessionnelles.getListeDesDiplomes();
            if (listeDesDiplomes != null) {
                for (Diplome diplome : listeDesDiplomes.listerDiplomes()) {
                    if (diplome != null && !Pays.Suisse.name().equals(diplome.getPaysObtention().getValue())) {
                        typesAnnexe.add(TypeAnnexe.Equivalence);
                        break;
                    }
                }
            }
        }
    }

    private void determinerListeTypeAnnexesObligatoiresSelonDonneesProfessionnelles() {
        DonneesProfessionnelles donneesProfessionnelles = demandeAutorisation.getDonneesProfessionnelles();
        if (donneesProfessionnelles != null) {
            CodeGLN codeGLN = donneesProfessionnelles.getCodeGLN();
            if (codeGLN == null || StringUtils.isEmpty(codeGLN.getValue())) {
                typesAnnexe.add(TypeAnnexe.Originaux);
            }
        }
    }

    private void determinerListeTypeAnnexesObligatoiresSelonDonneesPersonnelles() {
        DonneesPersonnelles donneesPersonnelles = demandeAutorisation.getDonneesPersonnelles();
        Profession profession = demandeAutorisation.getProfession();
        if (donneesPersonnelles != null) {
            if (!Pays.Suisse.equals(donneesPersonnelles.getNationalite()) &&
                    (Profession.Medecin.equals(profession) || Profession.MedecinDentiste.equals(profession) || Profession.Pharmacien.equals(profession))) {
                typesAnnexe.add(TypeAnnexe.AttestationBonneConduite);
            }

            if (!Langue.SuisseFR.equals(donneesPersonnelles.getLangue())) {
                typesAnnexe.add(TypeAnnexe.AttestationNiveauFrancais);
            }
        }
    }

    private void determinerListeTypeAnnexesObligatoiresSelonProfession() {
        Profession profession = demandeAutorisation.getProfession();
        typesAnnexe.add(TypeAnnexe.CV);
        typesAnnexe.add(TypeAnnexe.Diplome);
        if (Profession.Medecin.equals(profession) || Profession.Chiropraticien.equals(profession)) {
            typesAnnexe.add(TypeAnnexe.Titre);
        }
        if (!Profession.Medecin.equals(profession)) {
            typesAnnexe.add(TypeAnnexe.CertificatDeTravail);
        }
        typesAnnexe.add(TypeAnnexe.ExtraitCasierJudiciaire);
        typesAnnexe.add(TypeAnnexe.CertificatMedical);
        typesAnnexe.add(TypeAnnexe.ResponsabiliteCivile);
        typesAnnexe.add(TypeAnnexe.PieceIdentite);
    }

    // ********************************************************* Getters
}
