package ch.vd.demaut.commons.events;

import java.util.Collection;

import org.joda.time.DateTime;

/**
 * 
 * Générateur d'évènements F
 * 
 * FIXME : Refactoriser en Builder et utiliser exclusivement le générateur lors de la création des évènements
 * 
 * @param <E>
 *            Type d'evenement domaine
 * @param <S>
 *            Type de la source d'évènements
 */
public interface EventGenerator<E extends BaseDomainEvent<?>, S> {

    /**
     * Initialise le générateur
     * 
     * @param source
     */
    void init(S source);

    /**
     * Crée et publie l'évènèment généré par la source
     * 
     * @param source
     */
    void publishEvent(DateTime dateTime, Class<? extends E> eventClass, S source);

    /**
     * Crée et publie les évènèments générés par les sources
     * 
     * @param source
     */
    void publishEvents(DateTime dateTime, Class<? extends E> eventClass, Collection<? extends S> sources);

}
