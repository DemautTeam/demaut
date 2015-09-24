package ch.vd.demaut.commons.validation.test;

import ch.vd.demaut.commons.validation.ValidatorFactoryDefault;
import ch.vd.demaut.commons.validation.constraints.SimpleConstraintViolation;
import ch.vd.demaut.commons.validation.constraints.SimpleConstraintViolationWithMsg;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.Set;

abstract public class AbstractValidationTest<T> {

    private static final Logger log = LoggerFactory.getLogger(AbstractValidationTest.class);

    protected Validator validator;
    protected Set<ConstraintViolation<T>> constraintViolationsResult = null;
    protected Set<SimpleConstraintViolation<T>> simpleConstraintViolationsResult = null;
    protected Set<SimpleConstraintViolation<T>> simpleConstraintViolationsExpected = null;

    @Before
    public void setUp() {
        validator = ValidatorFactoryDefault.getValidator();
        simpleConstraintViolationsExpected = new HashSet<SimpleConstraintViolation<T>>();
    }

    protected void validate(T object) {
        validate(object, false);
    }

    protected void validateWithMsg(T object) {
        validate(object, true);
    }

    protected void validateProperty(T object, String property) {
        validateProperty(object, property, false);
    }

    protected void validatePropertyWithMsg(T object, String property) {
        validateProperty(object, property, true);
    }

    private void validate(T object, boolean validateMsg) {
        constraintViolationsResult = validator.validate(object);
        generateSimpleConstraintViolations(validateMsg);
    }

    private void validateProperty(T object, String property, boolean validateMsg) {
        constraintViolationsResult = validator.validateProperty(object, property);
        generateSimpleConstraintViolations(validateMsg);
    }

    protected void addExpectedConstraintViolation(String propertyName) {
        simpleConstraintViolationsExpected.add(new SimpleConstraintViolation<T>(propertyName));
    }

    protected void addExpectedConstraintViolation(String propertyName, String message) {
        simpleConstraintViolationsExpected.add(new SimpleConstraintViolationWithMsg<T>(propertyName, message));
    }

    @SuppressWarnings("all")
    protected void testAllConstraintViolations() {
        log.debug("simpleConstraintViolationsResult=" + simpleConstraintViolationsResult);

        if (simpleConstraintViolationsExpected.size() == 0) {
            Assertions.assertThat(simpleConstraintViolationsResult).isEmpty();
        } else {
            Assertions.assertThat(simpleConstraintViolationsResult).containsExactly(
                    simpleConstraintViolationsExpected.toArray(new SimpleConstraintViolation[0]));
        }
    }

    @SuppressWarnings("all")
    protected void testSomeConstraintViolations() {
        if (simpleConstraintViolationsExpected.size() == 0) {
            Assertions.assertThat(simpleConstraintViolationsResult).isEmpty();
        } else {
            Assertions.assertThat(simpleConstraintViolationsResult).contains(
                    simpleConstraintViolationsExpected.toArray(new SimpleConstraintViolation[0]));
        }
    }

    protected void testConstraintViolationsSize(int expectedSize) {
        Assertions.assertThat(constraintViolationsResult).hasSize(expectedSize);
    }

    // ********************************************************* Private Methods
    private void generateSimpleConstraintViolations(boolean validateMsg) {
        simpleConstraintViolationsResult = new HashSet<SimpleConstraintViolation<T>>();
        for (ConstraintViolation<T> constraintViolation : constraintViolationsResult) {
            log.info("constraintViolation=" + constraintViolation);
            if (!validateMsg) {
                simpleConstraintViolationsResult.add(new SimpleConstraintViolation<T>(constraintViolation));
            } else {
                simpleConstraintViolationsResult.add(new SimpleConstraintViolationWithMsg<T>(constraintViolation));
            }
        }
    }
}
