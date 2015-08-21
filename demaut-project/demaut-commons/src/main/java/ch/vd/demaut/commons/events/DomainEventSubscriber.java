package ch.vd.demaut.commons.events;

/**
 * Un abonné (aka EventHandler ou EventSubscriber) à un type d'évènements domaine doit implémenter cette interface
 *
 * @see https://code.google.com/p/ddd-cqrs-sample/
 * @see livre Implementing DDD -Chapter 8 Domain Events
 */
public interface DomainEventSubscriber<T extends DomainEvent> {

    /**
     * Renvoie true si l'évènement passé doit notifier ce subscriber
     *
     * @param event
     * @return
     */
    boolean canHandleEvent(DomainEvent event);

    /**
     * Méthode qui impléemente la réponse à l'évènement notifié
     *
     * @param event
     */
    void handleEvent(DomainEvent event);

}
