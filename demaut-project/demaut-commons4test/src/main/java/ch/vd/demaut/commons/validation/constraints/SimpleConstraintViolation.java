package ch.vd.demaut.commons.validation.constraints;

import ch.vd.demaut.commons.vo.BaseValueObject;

import javax.validation.ConstraintViolation;

/**
 * Help to test constraint violations
 */
public class SimpleConstraintViolation<T> extends BaseValueObject {

    private final String propertyName;

    public SimpleConstraintViolation(String propertyName) {
        this.propertyName = propertyName;
    }

    public SimpleConstraintViolation(ConstraintViolation<T> constraintViolation) {
        this(constraintViolation.getPropertyPath().toString());
    }

    /**
     * Retourne le hashCode de la propriété.
     *
     * Suppression d'une alerte critique de sonar.
     *
     * @return
     */
    @Override
    public int hashCode() {
        return propertyName.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof SimpleConstraintViolation) {
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
