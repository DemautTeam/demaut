package ch.vd.demaut.domain.model.demandeurs;

import ch.vd.demaut.commons.fk.FunctionalKeyAbstract;

public class DemandeurFK extends FunctionalKeyAbstract<Demandeur> {

	// ********************************************************* Fields
	private NomPrenomsDemandeur nomsEtPrenoms;

	// ********************************************************* Constructor

	public DemandeurFK(Demandeur demandeur) {
			super();
			this.nomsEtPrenoms = demandeur.getNomsEtPrenoms();
		}

	// ********************************************************* Getters

	public NomPrenomsDemandeur getNomsEtPrenoms() {
		return nomsEtPrenoms;
	}

}
