package ch.vd.demaut.commons.exceptions;

public class TechnicalException extends RuntimeException {

    private static final long serialVersionUID = 6115324290688968492L;

    public TechnicalException(String message) {
        super(message);
    }
    
    public TechnicalException(String message, Throwable e) {
        super(message, e);
    }

}
