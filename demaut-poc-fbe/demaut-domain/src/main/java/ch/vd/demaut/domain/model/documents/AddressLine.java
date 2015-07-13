package ch.vd.demaut.domain.model.documents;

import ch.vd.demaut.commons.BaseValueObject;

/**
 * Line of an {@link Address}
 *
 */
public class AddressLine extends BaseValueObject {

	// ********************************************************* Fields
	private String line;
	
	// ********************************************************* Constructor
	public AddressLine(String line) {
		super();
		this.line = line;
	}

	// ********************************************************* Getters
	public String getLine() {
		return line;
	}
	
}
