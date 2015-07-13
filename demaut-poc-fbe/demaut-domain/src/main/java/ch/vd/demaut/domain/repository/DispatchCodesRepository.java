package ch.vd.demaut.domain.repository;

import java.util.Collection;

import ch.vd.demaut.domain.model.DispatchCode;

/**
 * Interface that represents repository of {@link DispatchCode}
 *
 */
public interface DispatchCodesRepository {

	void initAutomaticDispatchCodes(Collection<DispatchCode> automaticCodes);
	
	Collection<DispatchCode> listAllAutomaticDispatchCodes();

}
