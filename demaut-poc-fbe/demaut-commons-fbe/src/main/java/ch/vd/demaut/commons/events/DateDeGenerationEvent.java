package ch.vd.demaut.commons.events;

import org.joda.time.LocalDate;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.exceptions.NotYetImplementedException;

@ValueObject
public class DateDeGenerationEvent {
    /**
     * Construction d'une {@link DateDeGenerationEvent}
     * 
     */
    public LocalDate getLocalDate() {
        throw new NotYetImplementedException();
    }
}
