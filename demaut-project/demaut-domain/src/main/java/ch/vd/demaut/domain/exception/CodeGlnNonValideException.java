package ch.vd.demaut.domain.exception;

import ch.vd.demaut.commons.exceptions.DomainException;

/**
 * Indique que le code GLN est invalide
 *
 */
public class CodeGlnNonValideException extends DomainException {

    private static final long serialVersionUID = -7843676995609086690L;

    public CodeGlnNonValideException() {
        super("Le code GLN renseigné est non valide");
    }
}
