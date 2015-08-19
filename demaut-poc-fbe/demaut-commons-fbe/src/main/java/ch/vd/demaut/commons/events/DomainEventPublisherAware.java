package ch.vd.demaut.commons.events;

import ch.vd.demaut.commons.fk.FunctionalKeyAware;

/**
 * Interface qqui doit être implémentée par une classe qui veut avoir un {@link DomainEventPublisher} injecté par le
 * contexte
 * 
 */
public interface DomainEventPublisherAware extends FunctionalKeyAware {
    /**
     * Set the DomainEventPublisher that this object runs in.
     *
     */
    void publishEvent(DomainEvent event);

    /**
     * Renvoie true si la publication des évènements est activées
     * 
     */
    boolean estPublicationEventsActivee();
    
    
    void initDomainEventPublisher(DomainEventPublisher domainEventPublisher);
}
