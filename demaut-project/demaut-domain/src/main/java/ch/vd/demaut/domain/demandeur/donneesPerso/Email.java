package ch.vd.demaut.domain.demandeur.donneesPerso;

import ch.vd.demaut.commons.vo.BaseValueObject;

import javax.validation.constraints.NotNull;

public class Email extends BaseValueObject {

    private String value;

    public Email() {
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
