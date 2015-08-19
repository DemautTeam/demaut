package ch.vd.demaut.commons.exceptions;

/**
 * <strong>Exception Domain </strong> lorsqu'une Entity existe déjà dans le repository.<br/>
 */
public class EntityAlreadyExistsException extends DomainException {

    private static final long serialVersionUID = -951117752810923154L;

    public EntityAlreadyExistsException() {
        super();
    }

}
