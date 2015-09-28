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

    @Override
    public boolean equals(Object o) {
        if(o instanceof SimpleConstraintViolation){
            return ((SimpleConstraintViolation<?>) o).getPropertyName().equals(this.propertyName);
        }
        return false;
    }

    /**
     * @return the propertyName
     */
    public String getPropertyName() {
        return propertyName;
    }

}
