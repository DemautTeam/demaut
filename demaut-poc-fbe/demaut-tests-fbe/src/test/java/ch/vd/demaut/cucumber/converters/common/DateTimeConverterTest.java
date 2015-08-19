package ch.vd.demaut.cucumber.converters.common;

import static org.assertj.core.api.Assertions.assertThat;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import ch.vd.demaut.cucumber.converters.commons.DateTimeConverter;

public class DateTimeConverterTest {

	DateTimeConverter converter;
	
	@Before
	public void setup() {
		converter = new DateTimeConverter();
	}
	
	@Test
	public void test() {
		DateTime dateTime = (DateTime) converter.fromString("15.07.2015 10:20");
		assertThat(dateTime.getYear()).isEqualTo(2015);
		assertThat(dateTime.getMonthOfYear()).isEqualTo(7);
		assertThat(dateTime.getDayOfMonth()).isEqualTo(15);
		assertThat(dateTime.getMinuteOfHour()).isEqualTo(20);
		assertThat(dateTime.getHourOfDay()).isEqualTo(10);
	}

}
