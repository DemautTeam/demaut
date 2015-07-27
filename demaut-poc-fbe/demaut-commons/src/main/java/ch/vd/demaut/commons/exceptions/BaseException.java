package ch.vd.demaut.commons.exceptions;

/**
 * Exception de base du projet qui doit être héritée par les autres exceptions.
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = -308108559858339219L;

    protected BaseException() {
        super();
    }

}
