package ch.vd.demaut.cucumber.converteurs.commons;

import cucumber.api.Transformer;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

public class LocalDateConverter extends Transformer<LocalDate> {

    public LocalDate transform(String jourMoisAnnee) {
        String pattern = "dd-MM-yyyyy";
        return LocalDate.parse(jourMoisAnnee, DateTimeFormat.forPattern(pattern));
    }
}
