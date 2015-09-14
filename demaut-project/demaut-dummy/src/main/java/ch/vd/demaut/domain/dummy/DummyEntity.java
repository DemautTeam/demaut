package ch.vd.demaut.domain.dummy;

import ch.vd.demaut.commons.annotations.Aggregate;
import ch.vd.demaut.commons.entities.EntityFunctionalKeyAware;

@Aggregate
public class DummyEntity extends EntityFunctionalKeyAware {
	
	public DummyEntity() {
	}
	
	private DummyRef dummyRef;
	
	private long nonPersistField;
	
	public void setDummyRef(DummyRef dummyRef) {
		this.dummyRef = dummyRef;
	}
	
	public DummyRef getDummyRef() {
		return dummyRef;
	}

	@Override
	public DummyEntityFK getFunctionalKey() {
		return new DummyEntityFK(this);
	}

	public long getNonPersistField() {
		return nonPersistField;
	}
	
	public void setNonPersistField(long nonPersistField) {
		this.nonPersistField = nonPersistField;
	}
//    protected ReferenceDeDemande referenceDeDemande;
//
//    public ReferenceDeDemande getReferenceDeDemande() {
//        return referenceDeDemande;
//    }
//    
//    public void setReferenceDeDemande(ReferenceDeDemande referenceDeDemande) {
//		this.referenceDeDemande = referenceDeDemande;
//	}

}
 