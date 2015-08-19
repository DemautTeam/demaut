package ch.vd.demaut.commons.events;

/**
 * Publisher d'un {@link DomainEvent}
 * 
 * @see http://en.wikipedia.org/wiki/Publish-subscribe_pattern
 * @see https://code.google.com/p/ddd-cqrs-sample/
 * @see livre Implementing DDD -Chapter 8 Domain Events
 */
public interface DomainEventPublisher {
    
    /**
     * Envoie un évènement domaine {@link DomainEvent} à tous les @link DomainEventSubscriber abonnés à cet évènement 
     * @param event Evènement domaine à notifier
     */
    void publishEvent(DomainEvent event);
    
    /**
     * Les {@link DomainEventSubscriber} doivent s'enregistrer en utilisant cette méthode afin d'être notifié
     * @param subscriber
     */
    void subscribe(DomainEventSubscriber<?> subscriber);
    
    /**
     * Permet de désinscrire un {@link DomainEventSubscriber} aux subscriber notifiés
     * @param subscriber
     */
    void unsubscribe(DomainEventSubscriber<?> subscriber);
}
