package ch.vd.demaut.domain.demandes;

import ch.vd.demaut.commons.entities.EntityFunctionalKeyAware;

public abstract class Demande extends EntityFunctionalKeyAware {

    // ********************************************************* Fields
    protected ReferenceDeDemande referenceDeDemande;

    // ********************************************************* Constructeur

    protected Demande() {
    }

    // ********************************************************* Business Methodes

    public void generateReference() {
        referenceDeDemande = new ReferenceDeDemande();
    }


    // ********************************************************* Getters
    public ReferenceDeDemande getReferenceDeDemande() {
        return referenceDeDemande;
    }

}
