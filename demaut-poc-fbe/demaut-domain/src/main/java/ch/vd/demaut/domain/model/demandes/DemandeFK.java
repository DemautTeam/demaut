package ch.vd.demaut.domain.model.demandes;

import ch.vd.demaut.commons.FunctionalKeyAbstract;

public class DemandeFK extends FunctionalKeyAbstract<Demande> {

	// ********************************************************* Fields
	final private DemandePublicId demandePublicId;

	// ********************************************************* Constructor
	public DemandeFK(Demande demande) {
		this.demandePublicId = demande.getDemandePublicId();
	}

	// ********************************************************* Getters
	public DemandePublicId getDemandePublicId() {
		return demandePublicId;
	}

}
