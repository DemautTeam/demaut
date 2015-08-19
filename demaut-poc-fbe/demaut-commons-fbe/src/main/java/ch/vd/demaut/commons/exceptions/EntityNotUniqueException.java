package ch.vd.demaut.commons.exceptions;

/**
 * <strong>Exception Domain </strong> lorsque plusieurs Entity ont été trouvées dans le repository alors qu'il devrait
 * en avoir q'une seule <br/>
 */
public class EntityNotUniqueException extends DomainException {

    private static final long serialVersionUID = -7596870853975660756L;

    public EntityNotUniqueException() {
        super();
    }

}
