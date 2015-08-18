package ch.vd.demaut.domain.model.documents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.constraints.Size;

import ch.vd.demaut.commons.vo.BaseValueObject;
import ch.vd.demaut.commons.annotations.ValueObject;

/** 
 * Mailing address of a customer
 *
 */
@ValueObject
public class Address extends BaseValueObject {

	// ********************************************************* Fields
	private List<String> lines = new ArrayList<String>();

	// ********************************************************* Constructor
	
	public Address(List<String> lines) {
		super();
		this.lines = lines;
	}

	// ********************************************************* Business Methods
	public String getLine(int lineNumber) {
		return lines.get(lineNumber);
	}

	// ********************************************************* Technical Methods

	// ********************************************************* Getters
	/**
	 * 
	 * @return Lines of this {@link Address} instance
	 */
	@Size(min = 1)
	public List<String> getLines() {
		return Collections.unmodifiableList(lines);
	}
	
}
