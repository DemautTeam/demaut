package ch.vd.demaut.cucumber.converters.common;

import ch.vd.demaut.cucumber.converteurs.commons.DateTimeConverter;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DateTimeConverterTest {

    DateTimeConverter dateTimeConverter;

    @Before
    public void setup() {
        dateTimeConverter = new DateTimeConverter();
    }

    @Test
    public void testValid() {
        DateTime dateTime = (DateTime) dateTimeConverter.fromString("15-07-2015 10:20");
        assertThat(dateTime.getYear()).isEqualTo(2015);
        assertThat(dateTime.getMonthOfYear()).isEqualTo(7);
        assertThat(dateTime.getDayOfMonth()).isEqualTo(15);
        assertThat(dateTime.getMinuteOfHour()).isEqualTo(20);
        assertThat(dateTime.getHourOfDay()).isEqualTo(10);
    }

    @Test(expected = org.joda.time.IllegalFieldValueException.class)
    public void testInvalid() {
        DateTime dateTime = (DateTime) dateTimeConverter.fromString("32-07-2015 10:20");
        assertThat(dateTime.getYear()).isEqualTo(2015);
    }
}
