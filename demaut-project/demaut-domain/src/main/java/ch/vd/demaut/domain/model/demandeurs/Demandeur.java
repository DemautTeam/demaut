package ch.vd.demaut.domain.model.demandeurs;

import ch.vd.demaut.commons.fk.ObjectFunctionalKeyAware;
import ch.vd.demaut.commons.annotations.Aggregate;

@Aggregate
public class Demandeur extends ObjectFunctionalKeyAware {

	// ********************************************************* Fields
	private NomPrenomsDemandeur nomsEtPrenoms;

	// ********************************************************* Constructor
	public Demandeur(String nom, String prenom) {
		super();
		this.nomsEtPrenoms = new NomPrenomsDemandeur(nom, prenom);
	}

	// ********************************************************* Getters
	public NomPrenomsDemandeur getNomsEtPrenoms() {
		return nomsEtPrenoms;
	}
	
	// ********************************************************* Technical Methods
	@Override
	public DemandeurFK getFunctionalKey() {
		return new DemandeurFK(this);
	}

}
