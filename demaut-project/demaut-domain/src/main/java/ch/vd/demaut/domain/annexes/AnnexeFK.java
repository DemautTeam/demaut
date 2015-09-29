package ch.vd.demaut.domain.annexes;

import ch.vd.demaut.commons.fk.FunctionalKeyAbstract;

/**
 * Clé fonctionnelle d'une {@link Annexe} basée sur {@link TypeAnnexe} et
 * {@link NomFichier}
 *
 */
public class AnnexeFK extends FunctionalKeyAbstract<Annexe> {

    private TypeAnnexe typeAnnexe;

    private NomFichier nomFichier;

    public AnnexeFK(Annexe annexe) {
        this.typeAnnexe = annexe.getTypeAnnexe();
        this.nomFichier = annexe.getNomFichier();
    }

    public TypeAnnexe getTypeAnnexe() {
        return typeAnnexe;
    }

    public NomFichier getNomFichier() {
        return nomFichier;
    }
}
