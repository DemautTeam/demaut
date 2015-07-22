package ch.vd.demaut.domain.demandes;

import ch.vd.demaut.commons.fk.FunctionalKeyAbstract;

public class DemandeFK extends FunctionalKeyAbstract<Demande> {

	// ********************************************************* Fields
	final private ReferenceDeDemande reference;

	// ********************************************************* Constructor
	public DemandeFK(Demande demande) {
		this.reference = demande.getReference();
	}

	// ********************************************************* Getters
	public ReferenceDeDemande getReference() {
		return reference;
	}

}
