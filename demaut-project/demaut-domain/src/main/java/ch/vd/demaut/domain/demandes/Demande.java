package ch.vd.demaut.domain.demandes;

import ch.vd.demaut.commons.entities.EntityFunctionalKeyAware;

public abstract class Demande extends EntityFunctionalKeyAware {

    // ********************************************************* Fields
    protected ReferenceDeDemande referenceDeDemande;

    protected DateDeCreation dateDeCreation;

    // ********************************************************* Constructeur

    protected Demande() {
    }

    // ********************************************************* Business Methodes

    public void generateReference() {
        referenceDeDemande = new ReferenceDeDemande();
    }


    public void generateDateDeCreation() {
        dateDeCreation = new DateDeCreation();
    }

    // ********************************************************* Getters
    public ReferenceDeDemande getReferenceDeDemande() {
        return referenceDeDemande;
    }

    public DateDeCreation getDateDeCreation() {
        return dateDeCreation;
    }
}
