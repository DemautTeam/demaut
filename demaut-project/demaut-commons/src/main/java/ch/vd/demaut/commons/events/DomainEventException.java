package ch.vd.demaut.commons.events;

import ch.vd.demaut.commons.exceptions.TechnicalException;

/**
 * 
 * Exception d'un évènement métier
 * 
 */
public class DomainEventException extends TechnicalException {

    private static final long serialVersionUID = 8961240312831691394L;

    public DomainEventException(String message) {
        super(message);
    }
}
