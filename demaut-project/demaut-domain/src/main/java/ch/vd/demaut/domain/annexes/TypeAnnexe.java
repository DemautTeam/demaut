package ch.vd.demaut.domain.annexes;

import ch.vd.demaut.domain.config.RefProgresID;
import ch.vd.demaut.domain.config.TypeProgres;
import ch.vd.demaut.domain.exception.TypeAnnexeNotFoundException;

/**
 * Représente le type d'une annexe
 */
public enum TypeAnnexe implements TypeProgres {
    CV(1, "CV"),
    Diplome(2, "Diplôme"),
    Titre(3, "Titre(s) (pour chaque Postgrade)"),
    Equivalence(4, "Equivalence(s)"),
    CertificatDeTravail(5, "Certificats de travail"),
    ExtraitCasierJudiciaire(6, "Extrait de casier judiciaire"),
    AttestationBonneConduite(7, "Attestation de bonne conduite"),
    CertificatMedical(8, "Certificat médical"),
    ResponsabiliteCivile(9, "Responsabilité civile"),
    PieceIdentite(10, "Piece Identité"),
    AutorisationPratiquer(11, "Autorisation(s) de pratiquer"),
    Divers(12, "Divers");

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

}
