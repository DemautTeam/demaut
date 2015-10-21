package ch.vd.demaut.domain.annexes;

import ch.vd.demaut.commons.fk.FunctionalKeyAbstract;

/**
 * Clé fonctionnelle d'une {@link Annexe} basée le
 * {@link NomFichier}
 */
public class AnnexeFK extends FunctionalKeyAbstract<Annexe> {

    private NomFichier nomFichier;

    public AnnexeFK(Annexe annexe) {
        this.nomFichier = annexe.getNomFichier();
    }

    public AnnexeFK(NomFichier nomFichier) {
        this.nomFichier = nomFichier;
    }

    public NomFichier getNomFichier() {
        return nomFichier;
    }

}
