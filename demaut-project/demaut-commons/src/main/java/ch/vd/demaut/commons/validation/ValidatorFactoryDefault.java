package ch.vd.demaut.commons.validation;

import javax.validation.Configuration;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Factory to create a JavaBean validator
 *
 * Warning validator is a singleton.
 */
public class ValidatorFactoryDefault {

    private static Configuration<?> config = null;
    private static ValidatorFactory INSTANCE = null;
    private static Validator validator = null;

    private synchronized static ValidatorFactory getInstance() {
        if (INSTANCE == null) {
            config = Validation.byDefaultProvider().configure();
            INSTANCE = config.buildValidatorFactory();
        }
        return INSTANCE;
    }

    public static Validator getValidator() {
        if (validator == null) {
            validator = getInstance().getValidator();
        }
        return validator;
    }
}
