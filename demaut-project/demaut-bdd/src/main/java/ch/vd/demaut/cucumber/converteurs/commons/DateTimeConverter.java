package ch.vd.demaut.cucumber.converteurs.commons;

import cucumber.api.Transformer;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class DateTimeConverter extends Transformer<DateTime> {

    public DateTime transform(String jourEtHeure) {
        String pattern = "dd.MM.yy hh:mm";
        return DateTime.parse(jourEtHeure, DateTimeFormat.forPattern(pattern));
    }
}
