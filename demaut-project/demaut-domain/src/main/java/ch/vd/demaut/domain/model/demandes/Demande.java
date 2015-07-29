package ch.vd.demaut.domain.model.demandes;

import ch.vd.demaut.commons.ObjectFunctionalKeyAware;

public abstract class Demande extends ObjectFunctionalKeyAware {

	// ********************************************************* Fields
	protected DemandePublicId demandePublicId;
		
	// ********************************************************* Getters
	public DemandePublicId getDemandePublicId() {
		return demandePublicId;
	}

}