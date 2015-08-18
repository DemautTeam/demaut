package ch.vd.demaut.domain.annexes;

import javax.validation.constraints.NotNull;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;

@ValueObject
public class NomFichier extends BaseValueObject {

	// ********************************************************* Fields
	@NotNull
	final private String value;
	
	// ********************************************************* Constructor
	
	public NomFichier(String value) {
		super();
		this.value = value;
	}

	// ********************************************************* Getter
	
	public String getValue() {
		return value;
	}

}
