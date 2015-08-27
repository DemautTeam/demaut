package ch.vd.demaut.commons.validation.constraints;

import ch.vd.demaut.commons.vo.BaseValueObject;

import javax.validation.ConstraintViolation;

/**
 * Help to test constraint violations
 */
public class SimpleConstraintViolation<T> extends BaseValueObject {

    private String propertyName;

    public SimpleConstraintViolation(String propertyName) {
        super();
        this.propertyName = propertyName;
    }

    public SimpleConstraintViolation(ConstraintViolation<T> constraintViolation) {
        this(constraintViolation.getPropertyPath().toString());
    }

    /**
     * @return the propertyName
     */
    public String getPropertyName() {
        return propertyName;
    }

}
