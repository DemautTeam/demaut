package ch.vd.demaut.domain.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ch.vd.demaut.commons.BaseValueObject;
import ch.vd.demaut.commons.annotations.ValueObject;

/**
 * Represents a dispatch code
 *
 */
@ValueObject
public class DispatchCode extends BaseValueObject {

    // ********************************************************* Fields

	private String code;

	// ********************************************************* Constructor

	public DispatchCode(String code) {
		super();
		this.code = code;
	}

	// ********************************************************* Business Methods
	
    // ********************************************************* Technical Methods
	
    // ********************************************************* Getters
	
	/**
	 * 
	 * @return Code of the {@link DispatchCode}
	 */
	@NotNull
	@Size(min=1,  max=10)
	public String getCode() {
		return code;
	}

    // ********************************************************* Private methods
	
}
