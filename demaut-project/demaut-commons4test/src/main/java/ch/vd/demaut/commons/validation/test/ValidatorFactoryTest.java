package ch.vd.demaut.commons.validation.test;

import ch.vd.demaut.commons.validation.ValidatorFactoryDefault;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.validation.ValidatorFactory;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class ValidatorFactoryTest extends TestCase {

    private ValidatorFactory validatorFactory;

    @Before
    public void setUp() throws Exception {
        this.validatorFactory = ValidatorFactoryDefault.getInstance();
    }

    @Test
    public void should_get_instance_factory() {
        assertThat(validatorFactory).isNotNull();
    }

    @Test
    public void should_get_instance_factory_validator() {
        assertThat(validatorFactory.getValidator()).isNotNull();
    }

    @Test
    public void should_get_instance_constraint_factory() {
        assertThat(validatorFactory.getConstraintValidatorFactory()).isNotNull();
    }
}
