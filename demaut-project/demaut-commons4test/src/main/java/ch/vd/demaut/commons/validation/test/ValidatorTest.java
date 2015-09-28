package ch.vd.demaut.commons.validation.test;

import ch.vd.demaut.commons.validation.ValidatorFactoryDefault;
import org.junit.Before;
import org.junit.Test;

import javax.validation.Validator;

import static org.assertj.core.api.Assertions.assertThat;

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
