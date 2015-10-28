package ch.vd.demaut.commons.exceptions;

import java.text.MessageFormat;

import ch.vd.demaut.commons.fk.FunctionalKey;

/**
 * <strong>Exception Domain </strong> lorsqu'une Entity n'as pas été trouvée
 * dans le repository alors qu'elle devrait y être.<br/>
 */
public class EntityNotFoundException extends DomainException {

    private static final long serialVersionUID = -6172381432630248990L;

    public EntityNotFoundException(FunctionalKey<?> fk) {

        super(buildMessage(fk));
    }

    private static String buildMessage(FunctionalKey<?> fk) {
        return MessageFormat.format("L'entité n'a pas été trouvée pour {1}", fk.toString());
    }

}
