package ch.vd.demaut.domain.exception;

import ch.vd.demaut.commons.exceptions.DomainException;

/**
 * Exception causée par une annexe non valide
 */
public class DonneesPersonnellesNonValideException extends DomainException {

    private static final long serialVersionUID = 2276585885787963698L;

    public DonneesPersonnellesNonValideException() {
        super("Les données personnelles ne sont pas valides");
    }

    public DonneesPersonnellesNonValideException(String message) {
        super(message);
    }

}
