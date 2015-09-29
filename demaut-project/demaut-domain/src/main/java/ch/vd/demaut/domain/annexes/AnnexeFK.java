package ch.vd.demaut.domain.annexes;

import ch.vd.demaut.commons.fk.FunctionalKeyAbstract;

/**
 * Clé fonctionnelle d'une {@link Annexe} basée sur {@link TypeAnnexe} et
 * {@link NomFichier}
 */
public class AnnexeFK extends FunctionalKeyAbstract<Annexe> {

    private NomFichier nomFichier;

    private TypeAnnexe typeAnnexe;

    public AnnexeFK(Annexe annexe) {
        this.nomFichier = annexe.getNomFichier();
        this.typeAnnexe = annexe.getTypeAnnexe();
    }

    public AnnexeFK(NomFichier nomFichier, TypeAnnexe typeAnnexe) {
        this.nomFichier = nomFichier;
        this.typeAnnexe = typeAnnexe;
    }

    public NomFichier getNomFichier() {
        return nomFichier;
    }
    
    public TypeAnnexe getTypeAnnexe() {
        return typeAnnexe;
    }

}
