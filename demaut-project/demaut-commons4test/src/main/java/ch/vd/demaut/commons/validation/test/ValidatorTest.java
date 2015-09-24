package ch.vd.demaut.commons.validation.test;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;

import ch.vd.demaut.commons.validation.ValidatorFactoryDefault;

public class ValidatorTest {

    private Validator validator;

    @Before
    public void setUp() throws Exception {
        validator = ValidatorFactoryDefault.getValidator();
    }

    @Test
    public void testGetValidator() {
        assertThat(validator).isNotNull();
    }

}
