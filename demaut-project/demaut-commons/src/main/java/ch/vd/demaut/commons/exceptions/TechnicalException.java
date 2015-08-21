package ch.vd.demaut.commons.exceptions;

public class TechnicalException extends RuntimeException {

    private static final long serialVersionUID = 6115324290688968492L;

    //TODO : Replace by code
    private String message;

    public TechnicalException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
