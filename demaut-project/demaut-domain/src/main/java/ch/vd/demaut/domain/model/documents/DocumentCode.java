package ch.vd.demaut.domain.model.documents;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import ch.vd.demaut.commons.vo.BaseValueObject;

/**
 * Represented the document code of a {@link Document} associated to a type of document.
 * For instance, 0020 = Account Statement document type
 *
 */
public class DocumentCode extends BaseValueObject {

	// ********************************************************* Fields
	private String code;

	// ********************************************************* Constructor
	
	public DocumentCode(String code) {
		super();
		this.code = code;
	}

	// ********************************************************* Business Methods

	// ********************************************************* Technical Methods

	// ********************************************************* Getters
	//TODO: Reduce the accepted type in enums
	@NotBlank(message = "The document code must not be empty")
	@Size(max = 4, message = "The document code '${validatedValue}' must be no more than {value} chars")
	public String getCode() {
		return code;
	}
}
