package ch.vd.demaut.domain.exception;

import ch.vd.demaut.commons.exceptions.DomainException;

/**
 * Exception causée par un contenu d'annexe non valide
 */
public class AnnexeContenuException extends DomainException {

    private static final long serialVersionUID = 802815330672796027L;
    
    public AnnexeContenuException(String message, Throwable cause) {
        super(message, cause);
    }
}
