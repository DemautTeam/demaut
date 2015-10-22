package ch.vd.demaut.commons.exceptions;


import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * <strong>Exception du Domaine :</strong> Gestion des toutes les erreurs liées au domaine.
 */
public class DomainException extends BaseException {

    private static final long serialVersionUID = 6413485124943479155L;

    private Set<ConstraintViolation<?>> constraintViolations;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public DomainException() {
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public DomainException(String message) {
        super(message);
    }

    /**
     * Constructs a new runtime exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A <tt>null</tt> value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     * @since 1.4
     */
    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new runtime exception with the specified cause and a
     * detail message of <tt>(cause==null ? null : cause.toString())</tt>
     * (which typically contains the class and detail message of
     * <tt>cause</tt>).  This constructor is useful for runtime exceptions
     * that are little more than wrappers for other throwables.
     *
     * @param cause the cause (which is saved for later retrieval by the
     *              {@link #getCause()} method).  (A <tt>null</tt> value is
     *              permitted, and indicates that the cause is nonexistent or
     *              unknown.)
     * @since 1.4
     */
    public DomainException(Throwable cause) {
        super(cause);
    }

    /**
     * Initialisation des containtes de validation
     *
     * @param message
     * @param constraintViolations
     */
    public DomainException(String message, Set<ConstraintViolation<?>> constraintViolations){
        super(message);
        this.constraintViolations = constraintViolations;
    }

    public DomainException(String message, Throwable cause, Set<ConstraintViolation<?>> constraintViolations){
        super(message, cause);
        this.constraintViolations = constraintViolations;
    }

    @Override
    public String getMessage() {
        String message = super.getMessage();
        if (StringUtils.isEmpty(message)) {
            message = "Erreur du domaine métier, message non defini...";
        }
        return message;
    }

    /**
     * Retourne les contraintes de validation. peut être null.
     *
     * @return
     */
    public Set<ConstraintViolation<?>> getConstraintViolations() {
        return constraintViolations;
    }
}
