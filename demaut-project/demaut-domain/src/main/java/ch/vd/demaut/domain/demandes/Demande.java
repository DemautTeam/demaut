package ch.vd.demaut.domain.demandes;

import ch.vd.demaut.commons.entities.EntityFunctionalKeyAware;

public abstract class Demande extends EntityFunctionalKeyAware {

    // ********************************************************* Fields
    protected ReferenceDeDemande referenceDeDemande;

    // ********************************************************* Getters
    public ReferenceDeDemande getReferenceDeDemande() {
        return referenceDeDemande;
    }
}
