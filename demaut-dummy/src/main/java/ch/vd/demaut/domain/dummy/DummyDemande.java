package ch.vd.demaut.domain.dummy;

import javax.validation.constraints.NotNull;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;


abstract public class DummyDemande extends EntityFunctionalKeyAware {
	
	private ReferenceDeDemande referenceDeDemande;

    public DummyDemande() {
    	referenceDeDemande = new ReferenceDeDemande();
    }
    
	@NotNull
    public ReferenceDeDemande getReferenceDeDemande() {
        return referenceDeDemande;
    }
}
 