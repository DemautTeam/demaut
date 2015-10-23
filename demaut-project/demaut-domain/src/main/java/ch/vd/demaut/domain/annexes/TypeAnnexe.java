package ch.vd.demaut.domain.annexes;

import java.util.Comparator;

import ch.vd.demaut.domain.config.RefProgresID;
import ch.vd.demaut.domain.config.TypeProgres;
import ch.vd.demaut.domain.exception.TypeAnnexeNotFoundException;

/**
 * Représente le type d'une annexe
 */
public enum TypeAnnexe implements TypeProgres, Comparator<TypeAnnexe> {
    CV(1, "CV"),
    Diplome(2, "Diplôme"),
    Titre(3, "Titre(s) (pour chaque Postgrade)"),
    Equivalence(4, "Equivalence(s)"),
    CertificatDeTravail(5, "Certificats de travail"),
    ExtraitCasierJudiciaire(6, "Extrait de casier judiciaire"),
    AttestationBonneConduite(7, "Attestation de bonne conduite"),
    CertificatMedical(8, "Certificat médical"),
    ResponsabiliteCivile(9, "Responsabilité civile"),
    PieceIdentite(10, "Piece d'identité"),
    AttestationNiveauFrancais(11, "Attestation de niveau de français"),
    AutorisationPratiquer(12, "Autorisation(s) de pratiquer"),
    Recusation(13, "Récusation"),
    SpecimenSignature(14, "Spécimen signature (à transmettre par courrier postal)"),
    Aucun(15, "Aucun");

    private RefProgresID refProgresID;

    private String libl;

    private TypeAnnexe(Integer id, String libl) {
        this.refProgresID = new RefProgresID(id);
        this.libl = libl;
    }

    //TODO: refactor avec Profession....
    static public TypeAnnexe getTypeById(Integer id2) {
        for (TypeAnnexe type : TypeAnnexe.values()) {
            if (type.getRefProgresID().equals(new RefProgresID(id2))) {
                return type;
            }
        }
        throw new TypeAnnexeNotFoundException();
    }

    public RefProgresID getRefProgresID() {
        return refProgresID;
    }

    public String getLibl() {
        return libl;
    }

    @Override
    public int compare(TypeAnnexe type1, TypeAnnexe type2) {
        return ((TypeAnnexe)type1).getRefProgresID().getId() - ((TypeAnnexe)type2).getRefProgresID().getId();
    }


}
