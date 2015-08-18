package ch.vd.demaut.commons.events;

import java.lang.reflect.ParameterizedType;

/**
 * 
 * Classe abstraite de base pour l'implémentation d'un {@link DomainEventSubscriber}
 * 
 * @param <T>
 */
abstract public class BaseDomainEventSubscriber<T extends DomainEvent> implements DomainEventSubscriber<T> {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canHandleEvent(DomainEvent event) {
        @SuppressWarnings("unchecked")
        Class<T> genericParameter0OfThisClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
        return genericParameter0OfThisClass.isAssignableFrom(event.getClass());
    }

}
