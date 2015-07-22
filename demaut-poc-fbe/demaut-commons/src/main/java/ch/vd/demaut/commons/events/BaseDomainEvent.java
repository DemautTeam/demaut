package ch.vd.demaut.commons.events;

import java.util.EventObject;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import ch.vd.demaut.commons.exceptions.NotYetImplementedException;
import ch.vd.demaut.commons.fk.FunctionalKey;
import ch.vd.demaut.commons.fk.FunctionalKeyAware;

/**
 * Classe abstraite de base pour l'implémentation d'un {@link DomainEvent}
 */
abstract public class BaseDomainEvent<S extends DomainEventPublisherAware> extends EventObject implements DomainEvent {

	private static final long serialVersionUID = -5678203769138641673L;

	// ********************************************************* Attributs

	private final DateDeGenerationEvent dateDeGenerationEvent;

    // ********************************************************* Constructeur
    public BaseDomainEvent(DateDeGenerationEvent dateDeGenerationEvent, S source) {
        super(source);
        this.dateDeGenerationEvent = dateDeGenerationEvent;
    }

    // ********************************************************* Méthodes métier

    @Override
    public boolean estMemeType(Class<? extends DomainEvent> domainEventClass) {
        return domainEventClass.isAssignableFrom(getClass());
    }

    @Override
    public boolean estMemeDate(DateDeGenerationEvent dateGeneration) {
        return getDateDeGenerationEvent().getLocalDate().equals(dateGeneration.getLocalDate());
    }

    @Override
    public boolean estMemeSourceFK(FunctionalKey<? extends FunctionalKeyAware> sourceFK) {
        if (sourceFK == null) {
            return false;
        }
        return sourceFK.equals(getSource().getFunctionalKey());
    }

    // ********************************************************* Méthodes techniques
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    @Override
    public int compareTo(DomainEvent o) {
        throw new NotYetImplementedException();
    }

    // ********************************************************* Getters

    @SuppressWarnings("unchecked")
    @Override
    public S getSource() {
        return (S) super.getSource();
    }

    @Override
    public DateDeGenerationEvent getDateDeGenerationEvent() {
        return dateDeGenerationEvent;
    }

}
