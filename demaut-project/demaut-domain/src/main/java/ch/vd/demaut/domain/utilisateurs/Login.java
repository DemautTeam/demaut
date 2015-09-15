package ch.vd.demaut.domain.utilisateurs;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;

@ValueObject
public class Login extends BaseValueObject {

	// ********************************************************* Fields
	private String value;
	
    // ********************************************************* Constructor

	//Pour OpenJPA
	protected Login(){
    }
    
    public Login(String value) {
    	super();
        this.value = value;
    }

    // ********************************************************* Getters

    public String getValue() {
    	return value;
    }
}
