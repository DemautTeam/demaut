package ch.vd.demaut.cucumber.converters.common;

import ch.vd.demaut.cucumber.converteurs.commons.LocalDateConverter;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalDateConverterTest {

    LocalDateConverter localDateConverter;

    @Before
    public void setup() {
        localDateConverter = new LocalDateConverter();
    }

    @Test
    public void testValid() {
        LocalDate localDate = (LocalDate) localDateConverter.fromString("15.07.2015");
        assertThat(localDate.getYear()).isEqualTo(2015);
        assertThat(localDate.getMonthOfYear()).isEqualTo(7);
        assertThat(localDate.getDayOfMonth()).isEqualTo(15);
    }

    @Test(expected = org.joda.time.IllegalFieldValueException.class)
    public void testInvalid() {
        LocalDate localDate = (LocalDate) localDateConverter.fromString("32.07.2015");
        assertThat(localDate.getYear()).isEqualTo(2015);
    }
}
