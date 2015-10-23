package ch.vd.demaut.domain.demandeur.donneesPerso;

import ch.vd.demaut.commons.vo.StringVO;

import javax.validation.constraints.NotNull;

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
