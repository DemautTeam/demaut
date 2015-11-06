package ch.vd.demaut.data.converters;

import java.util.Date;

import javax.persistence.AttributeConverter;

import org.joda.time.LocalDate;

/**
 * Converts a Joda LocalDate <-> JPA 2.1 date
 * @see https://github.com/smsiebe/joda-jpa-converters
 */
public class JodaLocalDateConverter implements AttributeConverter<LocalDate, Date> {

    public Date convertToDatabaseColumn(LocalDate localDate) {
        return localDate.toDate();
    }

    public LocalDate convertToEntityAttribute(Date date) {
        return LocalDate.fromDateFields(date);
    }
}
