package ch.vd.demaut.domain.demandeur;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.StringVO;

import javax.validation.constraints.NotNull;

@ValueObject
public class Email extends StringVO {

    public Email() {
    }

    public Email(String value) {
        super(value);
    }

    @NotNull
    @org.hibernate.validator.constraints.Email
    @Override
    public String getValue() {
        return super.getValue();
    }
}
