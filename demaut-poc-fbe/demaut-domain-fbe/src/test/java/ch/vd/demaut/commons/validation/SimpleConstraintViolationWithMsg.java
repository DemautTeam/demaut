package ch.vd.demaut.commons.validation;

import javax.validation.ConstraintViolation;

/**
 * Help to test constraint violations
 *
 */
public class SimpleConstraintViolationWithMsg<T> extends SimpleConstraintViolation<T> {

	private String message;

	public SimpleConstraintViolationWithMsg(String propertyName,
			String message) {
		super(propertyName);
		this.message = message;
	}

	public SimpleConstraintViolationWithMsg(ConstraintViolation<T> constraintViolation) {
		this(constraintViolation.getPropertyPath().toString(), constraintViolation
						.getMessage());
	}

	public String getMessage() {
		return message;
	}
}
