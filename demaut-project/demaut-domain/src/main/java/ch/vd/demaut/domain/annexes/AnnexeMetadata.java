package ch.vd.demaut.domain.annexes;

import ch.vd.demaut.commons.vo.BaseValueObject;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class AnnexeMetadata extends BaseValueObject {

    // ********************************************************* Static Fields

    // ********************************************************* Fields
    @NotNull
    private TypeAnnexe typeAnnexe;

    @NotNull
    @Valid
    private NomFichier nomFichier;

    @NotNull
    private long tailleContenu; //en octets

    // ********************************************************* Constructor

    public AnnexeMetadata(TypeAnnexe typeAnnexe, String nomFichier, long tailleContenu) {
        super();
        this.typeAnnexe = typeAnnexe;
        this.nomFichier = new NomFichier(nomFichier);
        this.tailleContenu = tailleContenu;
    }

    // ********************************************************* Getters

    public TypeAnnexe getTypeAnnexe() {
        return typeAnnexe;
    }

    public NomFichier getNomFichier() {
        return nomFichier;
    }

    public long getTailleContenu() {
        return tailleContenu;
    }

    public double getTailleEnMB() {
        return ((double) tailleContenu) / ((double) (1024 * 1024));
    }


    // ********************************************************* Technical methods

    // ********************************************************* Private Methods

}
