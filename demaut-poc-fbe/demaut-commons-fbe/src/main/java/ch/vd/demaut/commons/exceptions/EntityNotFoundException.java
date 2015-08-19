package ch.vd.demaut.commons.exceptions;

/**
 * <strong>Exception Domain </strong> lorsqu'une Entity n'as pas été trouvée dans le repository alors qu'elle devrait y
 * être.<br/>
 */
public class EntityNotFoundException extends DomainException {

    private static final long serialVersionUID = -6172381432630248990L;

    public EntityNotFoundException() {
        super();
    }

}
