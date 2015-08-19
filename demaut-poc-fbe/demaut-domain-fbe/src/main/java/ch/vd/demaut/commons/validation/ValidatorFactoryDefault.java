package ch.vd.demaut.commons.validation;

import javax.validation.Configuration;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory to create a JavaBean validator
 *
 */
public class ValidatorFactoryDefault {

    private static final Logger log = LoggerFactory.getLogger(ValidatorFactoryDefault.class);

    private static Configuration<?> config = null;
    private static ValidatorFactory INSTANCE = null;
    private static Validator validator = null;

    public synchronized static Configuration<?> getConfig() {
        if (config == null) {
            config = Validation.byDefaultProvider().configure();
            log.debug("Validation Provider configured successfully");
        }
        return config;
    }

    public synchronized static ValidatorFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = getConfig().buildValidatorFactory();
        }
        return INSTANCE;
    }

    public synchronized static Validator getValidator() {
        if (validator == null) {
            validator = getInstance().getValidator();
        }
        return validator;
    }
}
