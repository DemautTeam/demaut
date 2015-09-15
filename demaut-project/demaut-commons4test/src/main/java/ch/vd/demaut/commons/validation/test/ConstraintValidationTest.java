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
    public void testaddexpectedconstraintviolation() {
        this.addExpectedConstraintViolation(message);
        assertThat(this.simpleConstraintViolationsExpected).isNotEmpty();
    }

    @SuppressWarnings("all")
    @Test
    public void testaddexpectedconstraintviolationwithproperty() {
        this.addExpectedConstraintViolation(newTestProperty, message);
        assertThat(this.simpleConstraintViolationsExpected).isNotEmpty();
    }

    @SuppressWarnings("all")
    @Test
    public void testvalidate() {
        this.validate(object);
    }

    @SuppressWarnings("all")
    @Test
    public void testvalidatewithmsg() {
        this.validateWithMsg(object);
    }

    @SuppressWarnings("all")
    @Test
    public void testvalidatepropert() {
        this.validateProperty(object, newTestProperty);
    }

    @SuppressWarnings("all")
    @Test
    public void testvalidatepropertywithmsg() {
        this.validatePropertyWithMsg(object, newTestProperty);
    }

    @SuppressWarnings("all")
    @Test
    public void testtestallconstraintviolations() {
        this.addExpectedConstraintViolation(newTestProperty, message);
        assertThat(this.simpleConstraintViolationsExpected).isNotEmpty();

        this.validatePropertyWithMsg(object, newTestProperty);

        this.testConstraintViolationsSize(0);
    }
}
