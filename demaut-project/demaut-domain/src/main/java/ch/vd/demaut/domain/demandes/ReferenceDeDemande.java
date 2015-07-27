package ch.vd.demaut.domain.demandes;

import ch.vd.demaut.commons.annotations.ValueObject;

@ValueObject
public class ReferenceDeDemande {
	
	// ********************************************************* Fields
	private String id;

	// ********************************************************* Constructor
	public ReferenceDeDemande(String id) {
		this.id = id;
	}

	// ********************************************************* Getters
	public String getId() {
		return id;
	}
}
