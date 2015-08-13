package ch.vd.demaut.domain.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ch.vd.demaut.commons.BaseValueObject;
import ch.vd.demaut.commons.annotations.ValueObject;

@ValueObject
public class Society extends BaseValueObject {

	// ********************************************************* Fields
	private String number;
	

	// ********************************************************* Constructor

	public Society(String number) {
		this.number = number;
	}

	// ********************************************************* Getters
	@NotNull
	@Size(min=1, max=10)
	public String getNumber() {
		return number;
	}
}
