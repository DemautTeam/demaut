package ch.vd.demaut.commons.events;

import java.util.Collection;

import org.joda.time.DateTime;

/**
 * Classe abstraite qui imlémente {@link EventGenerator}
 * 
 * @see EventGenerator
 * @param <E>
 * @param <S>
 */
public abstract class BaseEventGenerator<E extends BaseDomainEvent<?>, S> implements EventGenerator<E, S> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void publishEvents(DateTime dateTime, Class<? extends E> eventClass, Collection<? extends S> sources) {
        for (S source : sources) {
            publishEvent(dateTime, eventClass, source);
        }
    }

}
