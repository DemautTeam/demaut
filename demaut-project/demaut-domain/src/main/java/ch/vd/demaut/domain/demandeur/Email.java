package ch.vd.demaut.domain.demandeur;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;

import javax.validation.constraints.NotNull;

@ValueObject
public class Email extends BaseValueObject {

    private String value;

    //Used only for JPA
    Email() {
    }

    public Email(String value) {
        this.value = value;
    }

    @NotNull
    @org.hibernate.validator.constraints.Email
    public String getValue() {
        return value;
    }
}
