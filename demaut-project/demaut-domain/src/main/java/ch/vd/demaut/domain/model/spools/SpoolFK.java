package ch.vd.demaut.domain.model.spools;

import ch.vd.demaut.commons.fk.FunctionalKeyAbstract;
import ch.vd.demaut.domain.model.DispatchCode;
import ch.vd.demaut.domain.model.EnvelopeSize;
import ch.vd.demaut.domain.model.RegionCode;

/**
 * Functional Key of a {@link Spool}
 */
public class SpoolFK extends FunctionalKeyAbstract<Spool> {
	
	
	// ********************************************************* Fields
	private DispatchCode dispatchCode;
	private RegionCode regionCode;
	private EnvelopeSize size;
	private Integer increment;

	
	// ********************************************************* Constructor
	public SpoolFK(Spool spool) {
		super();
		this.dispatchCode = spool.getDispatchCode();
		this.regionCode = spool.getRegionCode();
		this.size = spool.getSize();
		this.increment = spool.getIncrement();
	}

	// ********************************************************* Getters

	public DispatchCode getDispatchCode() {
		return dispatchCode;
	}


	public RegionCode getRegionCode() {
		return regionCode;
	}


	public EnvelopeSize getSize() {
		return size;
	}


	public Integer getIncrement() {
		return increment;
	}

	
	
	
	

}
