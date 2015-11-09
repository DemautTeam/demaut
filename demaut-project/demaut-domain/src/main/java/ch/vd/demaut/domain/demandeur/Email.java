package ch.vd.demaut.domain.demandeur;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;
import ch.vd.demaut.commons.vo.StringVOInterface;

import javax.validation.constraints.NotNull;

@ValueObject
public class Email extends BaseValueObject  implements StringVOInterface{

    private String value;

    //Used only for JPA
    protected Email() {
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
