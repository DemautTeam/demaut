package ch.vd.demaut.domain.annexes;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import ch.vd.demaut.commons.vo.BaseValueObject;

public class AnnexeMetadata extends BaseValueObject {

    // ********************************************************* Static Fields

    // ********************************************************* Fields
    private NomFichier nomFichier;

    private long tailleContenu; //en octets

    // ********************************************************* Constructor

    public AnnexeMetadata(String nomFichier, long tailleContenu) {
        super();
        this.nomFichier = new NomFichier(nomFichier);
        this.tailleContenu = tailleContenu;
    }

    // ********************************************************* Getters

    @NotNull
    @Valid
    public NomFichier getNomFichier() {
        return nomFichier;
    }

    @NotNull
    public long getTailleContenu() {
        return tailleContenu;
    }

    // ********************************************************* Technical methods

    // ********************************************************* Private Methods

}
