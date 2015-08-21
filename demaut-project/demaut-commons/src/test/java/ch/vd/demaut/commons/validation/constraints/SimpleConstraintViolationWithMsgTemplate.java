package ch.vd.demaut.commons.validation.constraints;

import javax.validation.ConstraintViolation;

/**
 * Help to test constraint violations
 */
public class SimpleConstraintViolationWithMsgTemplate<T> extends SimpleConstraintViolation<T> {

    private String messageTemplate;

    public SimpleConstraintViolationWithMsgTemplate(String propertyName,
                                                    String messageTemplate) {
        super(propertyName);
        this.messageTemplate = messageTemplate;
    }

    public SimpleConstraintViolationWithMsgTemplate(ConstraintViolation<T> constraintViolation) {
        this(constraintViolation.getPropertyPath().toString(),
                constraintViolation.getMessageTemplate());
    }

    /**
     * @return the messageTemplate
     */
    public String getMessageTemplate() {
        return messageTemplate;
    }

}
