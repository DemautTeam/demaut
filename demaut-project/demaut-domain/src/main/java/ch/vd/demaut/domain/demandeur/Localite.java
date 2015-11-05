package ch.vd.demaut.domain.demandeur;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ValueObject
public class Localite extends BaseValueObject {

    private String value;

    //Used only for JPA
    Localite() {
    }

    public Localite(String value) {

        this.value = value;
    }

    @NotNull
    @Size(max = 100)
    public String getValue() {
        return value;
    }
}