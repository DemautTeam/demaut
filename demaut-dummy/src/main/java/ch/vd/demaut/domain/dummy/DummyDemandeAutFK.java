package ch.vd.demaut.domain.dummy;

import ch.vd.demaut.commons.fk.FunctionalKeyAbstract;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;

public class DummyDemandeAutFK extends FunctionalKeyAbstract<DummyDemandeAut> {

	private ReferenceDeDemande ref;
	
	public DummyDemandeAutFK(DummyDemandeAut d) {
		this.ref = d.getReferenceDeDemande();
	}
	
	public ReferenceDeDemande getReferenceDeDemande() {
		return ref;
	}
	
}
