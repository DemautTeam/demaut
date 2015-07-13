package ch.vd.demaut.domain.repository.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import ch.vd.demaut.domain.model.DispatchCode;
import ch.vd.demaut.domain.repository.DispatchCodesRepository;

public class DispatchCodeConfigurationMock implements DispatchCodesRepository {

	private Collection<DispatchCode> automaticDispatchCodes = new HashSet<DispatchCode>();

	@Override
	public void initAutomaticDispatchCodes(Collection<DispatchCode> automaticCodes) {
		automaticDispatchCodes.clear();
		automaticDispatchCodes.addAll(automaticCodes);
	}

	@Override
	public Collection<DispatchCode> listAllAutomaticDispatchCodes() {
		return Collections.unmodifiableCollection(automaticDispatchCodes);
	}

}
