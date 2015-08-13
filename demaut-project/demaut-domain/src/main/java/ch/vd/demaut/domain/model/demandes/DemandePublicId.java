package ch.vd.demaut.domain.model.demandes;

import ch.vd.demaut.commons.annotations.ValueObject;

@ValueObject
public class DemandePublicId {
	
	// ********************************************************* Fields
	private String id;

	// ********************************************************* Constructor
	public DemandePublicId(String id) {
		this.id = id;
	}

	// ********************************************************* Getters
	public String getId() {
		return id;
	}
}
