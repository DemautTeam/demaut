package ch.vd.demaut.commons.exceptions;

/**
 * <strong>Exception du Domaine :</strong> Gestion des toutes les erreurs liées au domaine.
 */
public class DomainException extends BaseException {

    private static final long serialVersionUID = 6413485124943479155L;

    /**
     * @See {@link BaseException#BaseException()}
     */
    protected DomainException() {
        super("Erreur du domaine métier, message non defini...");
    }

    /**
     * @See {@link BaseException#BaseException(String)}
     */
    protected DomainException(String message) {
        super(message);
    }

}
