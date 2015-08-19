package ch.vd.demaut.domain.demandeurs;

import ch.vd.demaut.commons.fk.FunctionalKeyAbstract;

public class DemandeurFK extends FunctionalKeyAbstract<Demandeur> {

	// ********************************************************* Fields
	private NomEtPrenomDemandeur nomsEtPrenoms;

	// ********************************************************* Constructor

	public DemandeurFK(Demandeur demandeur) {
			super();
			this.nomsEtPrenoms = demandeur.getNomsEtPrenoms();
		}

	// ********************************************************* Getters

	public NomEtPrenomDemandeur getNomsEtPrenoms() {
		return nomsEtPrenoms;
	}

}
