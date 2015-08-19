package ch.vd.demaut.domain.demandeurs;

import ch.vd.demaut.commons.vo.BaseValueObject;

public class Login extends BaseValueObject
{
	// ********************************************************* Fields
	final private String value;
	
	// ********************************************************* Constructor
	public Login(String value) {
		super();
		this.value = value;
	}

	// ********************************************************* Getters
	public String getValue() {
		return value;
	}
	
}
