package ch.vd.demaut.commons.events;

import ch.vd.demaut.commons.fk.FunctionalKey;
import ch.vd.demaut.commons.fk.FunctionalKeyAware;
import ch.vd.demaut.commons.vo.ValueObject;

import java.io.Serializable;

/**
 * Evénement domaine. Evènement qui a une signification métier et reste agnostique à Spring ou autres framework (POJO)
 *
 * @see https://code.google.com/p/ddd-cqrs-sample/
 * @see livre Implementing DDD -Chapter 8 Domain Events
 */
public interface DomainEvent extends Serializable, ValueObject, Comparable<DomainEvent> {

    DateDeGenerationEvent getDateDeGenerationEvent();

    boolean estMemeSourceFK(FunctionalKey<? extends FunctionalKeyAware> sourceFK);

    boolean estMemeDate(DateDeGenerationEvent dateGeneration);

    boolean estMemeType(Class<? extends DomainEvent> domainEventClass);
}
