package ch.vd.demaut.domain.dummy;

import ch.vd.demaut.commons.entities.EntityFunctionalKeyAware;

public class DummyDemandeur extends EntityFunctionalKeyAware {

	@Override
	public DummyDemandeurFK getFunctionalKey() {
		return new DummyDemandeurFK(this);
	}

}
