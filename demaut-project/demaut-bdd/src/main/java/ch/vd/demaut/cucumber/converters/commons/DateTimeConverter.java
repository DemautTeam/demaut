package ch.vd.demaut.cucumber.converters.commons;

import cucumber.api.Transformer;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class DateTimeConverter extends Transformer<DateTime> {

    private static String pattern = "dd.MM.yy hh:mm";

    @Override
    public DateTime transform(String jourEtHeure) {
        DateTime dateTime = DateTime.parse(jourEtHeure, DateTimeFormat.forPattern(pattern));
        return dateTime;
    }

}
