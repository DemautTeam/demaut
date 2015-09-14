package ch.vd.demaut.domain.dummy;

import ch.vd.demaut.commons.fk.FunctionalKeyAbstract;

public class DummyEntityFK extends FunctionalKeyAbstract<DummyEntity> {

	private DummyRef dummyRef;
	
	public DummyEntityFK(DummyEntity d) {
		this.dummyRef = d.getDummyRef();
	}
	
	public DummyRef getDummyRef() {
		return dummyRef;
	}
	
	public void setDummyRef(DummyRef dummyRef) {
		this.dummyRef = dummyRef;
	}
}
