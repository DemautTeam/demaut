package ch.vd.demaut.domain.annexes;

import ch.vd.demaut.commons.vo.BaseValueObject;
import ch.vd.demaut.domain.demandes.DateDeCreation;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class AnnexeMetadata extends BaseValueObject {

    // ********************************************************* Static Fields

    // ********************************************************* Fields
    @NotNull
    @Valid
    private NomFichier nomFichier;

    @NotNull
    private long tailleContenu; //en octets

    private DateDeCreation dateDeCreation;

    // ********************************************************* Constructor

    public AnnexeMetadata(String nomFichier, long tailleContenu, DateDeCreation dateDeCreation) {
        super();
        this.nomFichier = new NomFichier(nomFichier);
        this.tailleContenu = tailleContenu;
        this.dateDeCreation = dateDeCreation;
    }

    // ********************************************************* Getters

    public NomFichier getNomFichier() {
        return nomFichier;
    }

    public long getTailleContenu() {
        return tailleContenu;
    }

    public DateDeCreation getDateDeCreation() {
        return dateDeCreation;
    }

    // ********************************************************* Technical methods

    // ********************************************************* Private Methods

}
