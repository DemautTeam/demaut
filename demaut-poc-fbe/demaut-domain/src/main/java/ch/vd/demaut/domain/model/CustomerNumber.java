package ch.vd.demaut.domain.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ch.vd.demaut.commons.BaseValueObject;
import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.domain.model.documents.Customer;

/**
 * Number of a {@link Customer}
 *
 */
@ValueObject
public class CustomerNumber extends BaseValueObject {
	
	// ********************************************************* Fields
	private String number;

	// ********************************************************* Constructor
	public CustomerNumber(String number) {
		super();
		this.number = number;
	}

	// ********************************************************* Business Methods
	
    // ********************************************************* Technical Methods
	
    // ********************************************************* Getters

	/**
	 * 
	 * @return Number of the account
	 */
	@NotNull
	@Size(min=1, max=40)
	public String getNumber() {
		return number;
	}
}
