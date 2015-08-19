package ch.vd.demaut.domain.demandes;

import ch.vd.demaut.commons.entities.EntityFunctionalKeyAware;

public abstract class Demande extends EntityFunctionalKeyAware {

	// ********************************************************* Fields
	protected ReferenceDeDemande reference;
		
	// ********************************************************* Getters
	public ReferenceDeDemande getReference() {
		return reference;
	}
}
