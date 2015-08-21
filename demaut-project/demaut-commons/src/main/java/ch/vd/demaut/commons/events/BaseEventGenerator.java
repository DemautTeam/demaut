package ch.vd.demaut.commons.events;

import org.joda.time.DateTime;

import java.util.Collection;

/**
 * Classe abstraite qui imlémente {@link EventGenerator}
 *
 * @param <E>
 * @param <S>
 * @see EventGenerator
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
