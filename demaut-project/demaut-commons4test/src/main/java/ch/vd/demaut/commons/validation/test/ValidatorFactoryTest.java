package ch.vd.demaut.commons.validation.test;

import ch.vd.demaut.commons.validation.ValidatorFactoryDefault;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ValidatorFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidatorFactoryTest {

    private ValidatorFactory validatorFactory;

    @Before
    public void setUp() throws Exception {
        this.validatorFactory = ValidatorFactoryDefault.getInstance();
    }

    @Test
    public void testgetinstancefactory() {
        assertThat(validatorFactory).isNotNull();
    }

    @Test
    public void testgetinstancefactoryvalidator() {
        assertThat(validatorFactory.getValidator()).isNotNull();
    }

    @Test
    public void testgetinstanceconstraintfactory() {
        assertThat(validatorFactory.getConstraintValidatorFactory()).isNotNull();
    }
}
