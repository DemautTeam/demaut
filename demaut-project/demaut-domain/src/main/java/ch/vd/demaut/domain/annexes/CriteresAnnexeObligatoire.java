package ch.vd.demaut.domain.annexes;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.exception.TypeAnnexeNotFoundException;

/**
 * Regroupe l'ensemble des criteres pour décider si annexe obligatoire ou pas
 *
 */
@ValueObject
public class CriteresAnnexeObligatoire extends BaseValueObject {

    // ********************************************************* Fields
    private ProcedureAnnexe procedureAnnexe;

    private Profession profession;

    private boolean hasTitresOuDiplomesEtrangers;

    private boolean estEtranger;

    // ********************************************************* Constructor
    public CriteresAnnexeObligatoire(ProcedureAnnexe procedureAnnexe, Profession profession,
            boolean hasTitresOuDiplomesEtrangers, boolean estEtranger) {
        this.procedureAnnexe = procedureAnnexe;
        this.profession = profession;
        this.hasTitresOuDiplomesEtrangers = hasTitresOuDiplomesEtrangers;
        this.estEtranger = estEtranger;
    }

    // ********************************************************* Metier
    boolean estObligatoire(TypeAnnexe typeAnnexe) {
        switch (typeAnnexe) {
        case CV:
            return estCVObligatoire();
        case Diplome:
            return estDiplomeObligatoire();
        case Titre:
            return estTitreObligatoire();
        case Equivalence:
            return estEquivalenceObligatoire();
        case CertificatDeTravail:
            return estCertificatTravailObligatoire();
        case ExtraitCasierJudiciaire:
            return estExtraitCasierObligatoire();
        case AttestationBonneConduite:
            return estAttestationBonneConduiteObligatoire();
        case CertificatMedical:
            return estCertificatMedicalObligatoire();
        case AutorisationPratiquer:
            return estAutorisationPratiquerObligatoire();
        case ResponsabiliteCivile:
            return true;
        case PieceIdentite:
            return true;
        case Recusation:
            // TODO : True Si le demandeur ne pratique pas au titre de la Lamal
            return false;
        case AttestationNiveauFrancais:
            // TODO : True Si le demandeur n’est pas de langue maternelle
            // Française
            return false;
        case SpecimenSignature:
            // TODO : True Si "A confirmer" par Olivier
            return false;
        case Aucun:
            // TODO: Virer le type Aucun utiliser pour garder le lien avec
            // l'Annexe
            return false;
        default:
            throw new TypeAnnexeNotFoundException();
        }

    }

    // ********************************************************* Privées
    private boolean estAutorisationPratiquerObligatoire() {
        return procedureAnnexe.iSimplifiee();
    }

    private boolean estCertificatMedicalObligatoire() {
        return procedureAnnexe.isOrdinaire();
    }

    private boolean estAttestationBonneConduiteObligatoire() {
        if (procedureAnnexe.iSimplifiee()) {
            return true;
        }
        if (profession.estNonUniversitaire()) {
            return false;
        }
        if (!estEtranger) {
            return false;
        }
        if (profession.equals(Profession.Chiropraticien)) {
            return false;
        }
        return true;
    }

    private boolean estExtraitCasierObligatoire() {
        return procedureAnnexe.isOrdinaire();
    }

    private boolean estCertificatTravailObligatoire() {
        if (procedureAnnexe.iSimplifiee()) {
            return false;
        }
        if (profession.equals(Profession.Medecin)) {
            return false;
        }
        return true;
    }

    private boolean estEquivalenceObligatoire() {
        return hasTitresOuDiplomesEtrangers;
    }

    private boolean estDiplomeObligatoire() {
        return true;
    }

    private boolean estTitreObligatoire() {
        if (!profession.estUniversitaire()) {
            return false;
        }
        if (profession.equals(Profession.Medecin)) {
            return estDiplomeObligatoire();
        }
        if (profession.equals(Profession.Chiropraticien)) {
            return estDiplomeObligatoire();
        }
        return false;
    }

    private boolean estCVObligatoire() {
        return procedureAnnexe.isOrdinaire();
    }
}
