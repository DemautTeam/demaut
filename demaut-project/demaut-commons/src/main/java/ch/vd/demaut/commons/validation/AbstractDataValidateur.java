package ch.vd.demaut.commons.validation;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

/**
 * Initialise un validator
 */
public abstract class AbstractDataValidateur<T> implements DataValidateur<T> {

    private final Validator validator;

    public AbstractDataValidateur() {
        validator = ValidatorFactoryDefault.getValidator();
    }

    protected Validator getValidator() {
        return validator;
    }

    protected Set<ConstraintViolation<T>> validateData(T data) {
        return validator.validate(data);
    }
}
