package ch.vd.demaut.commons.exceptions;

/**
 * Exception de base du projet qui doit être héritée par les autres exceptions.
 */
public abstract class BaseException extends RuntimeException {

    private static final long serialVersionUID = -308108559858339219L;

    /**
     * @see RuntimeException#RuntimeException()
     */
    protected BaseException() {
    }

    /**
     * @see RuntimeException#RuntimeException(String)
     */
    protected BaseException(String message) {
        super(message);
    }

    /**
     * @see RuntimeException#RuntimeException(String,Throwable)
     */
    protected BaseException(String message, Throwable cause) {
        super(message, cause);
    }

}
