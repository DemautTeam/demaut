package ch.vd.demaut.commons.validation;

import ch.vd.demaut.commons.exceptions.DomainException;

/**
 * Interface generique de validation
 */
public interface DataValidateur<T> {

    void valider(T data) throws DomainException;

}
