package ch.vd.demaut.domain.annexes;

import ch.vd.demaut.domain.config.RefProgresID;
import ch.vd.demaut.domain.config.TypeProgres;
import ch.vd.demaut.domain.exception.TypeAnnexeNotFoundException;

/**
 * Représente le type d'une annexe
 */
public enum TypeAnnexe implements TypeProgres {
    CV(50283749, "CV"),
    Diplome(50283740, "Diplôme"),
    Titre(98247240, "Titre(s) (pour chaque Postgrade)"),
    Equivalence(50283747, "Equivalence(s)"),
    CertificatDeTravail(50283742, "Certificats de travail"),
    ExtraitCasierJudiciaire(50283744, "Extrait de casier judiciaire"),
    AttestationBonneConduite(-1, "Attestation de bonne conduite"),
    CertificatMedical(50283757, "Certificat médical"),
    ResponsabiliteCivile(-2, "Responsabilité civile"),
    PieceIdentite(-3, "Piece Identité"),
    AutorisationPratiquer(-4, "Autorisation(s) de pratiquer");

    private RefProgresID refProgresID;

    private String libl;

    private TypeAnnexe(Integer id, String libl) {
        this.refProgresID = new RefProgresID(id);
        this.libl = libl;
    }

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

}
