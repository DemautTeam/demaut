package ch.vd.demaut.domain.exception;

import ch.vd.demaut.commons.exceptions.DomainException;

/**
 * Exception levée lorsqu'on veut créer un brouillon et qu'il en existe déjà
 */
public class DemandeBrouillonExisteDejaException extends DomainException {

    private static final long serialVersionUID = 6507126130204632518L;

}
