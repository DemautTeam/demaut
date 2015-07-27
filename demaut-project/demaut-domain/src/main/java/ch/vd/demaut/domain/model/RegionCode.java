package ch.vd.demaut.domain.model;

import javax.validation.constraints.NotNull;

import ch.vd.demaut.commons.BaseValueObject;
import ch.vd.demaut.commons.annotations.ValueObject;

@ValueObject
public class RegionCode extends BaseValueObject {

    // ********************************************************* Fields

	private String code;

	// ********************************************************* Constructor

	public RegionCode(String code) {
		super();
		this.code = code;
	}

	// ********************************************************* Business Methods
	
    // ********************************************************* Technical Methods
	
    // ********************************************************* Getters
	
	/**
	 * 
	 * @return Code of the {@link RegionCode}
	 */
	@NotNull
	public String getCode() {
		return code;
	}

    // ********************************************************* Private methods
	
}
