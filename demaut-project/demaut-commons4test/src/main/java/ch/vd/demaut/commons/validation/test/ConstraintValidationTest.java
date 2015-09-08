package ch.vd.demaut.commons.validation.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("rawtypes")
@RunWith(JUnit4.class)
public class ConstraintValidationTest extends AbstractValidationTest {

    private String newTestProperty = "NewTestProperty";
    private String message = "Constraint Violation Message for Test!";
    private Object object = new Object();

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        super.setUp();
        this.simpleConstraintViolationsExpected.clear();
        assertThat(this.simpleConstraintViolationsExpected).isEmpty();

        assertThat(object).isNotNull();
    }

    @SuppressWarnings("all")
    @Test
    public void should_add_expected_constraint_violation() {
        this.addExpectedConstraintViolation(message);
        assertThat(this.simpleConstraintViolationsExpected).isNotEmpty();
    }

    @SuppressWarnings("all")
    @Test
    public void should_add_expected_constraint_violation_with_property() {
        this.addExpectedConstraintViolation(newTestProperty, message);
        assertThat(this.simpleConstraintViolationsExpected).isNotEmpty();
    }

    @SuppressWarnings("all")
    @Test
    public void should_validate() {
        this.validate(object);
    }

    @SuppressWarnings("all")
    @Test
    public void should_validate_with_msg() {
        this.validateWithMsg(object);
    }

    @SuppressWarnings("all")
    @Test
    public void should_validate_propert() {
        this.validateProperty(object, newTestProperty);
    }

    @SuppressWarnings("all")
    @Test
    public void should_validate_property_with_msg() {
        this.validatePropertyWithMsg(object, newTestProperty);
    }

    @SuppressWarnings("all")
    @Test
    public void should_test_all_constraint_violations() {
        this.addExpectedConstraintViolation(newTestProperty, message);
        assertThat(this.simpleConstraintViolationsExpected).isNotEmpty();

        this.validatePropertyWithMsg(object, newTestProperty);

        this.testConstraintViolationsSize(0);
    }
}
