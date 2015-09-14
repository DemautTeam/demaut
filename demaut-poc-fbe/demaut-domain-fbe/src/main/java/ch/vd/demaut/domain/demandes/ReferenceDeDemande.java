package ch.vd.demaut.domain.demandes;

import ch.vd.demaut.commons.annotations.ValueObject;

/**
 * Référence unique d'une demande dans le système Demaut. Cette référence a une
 * visibilité client et ne correspond pas à un ID technique en base de données.
 *
 */
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
