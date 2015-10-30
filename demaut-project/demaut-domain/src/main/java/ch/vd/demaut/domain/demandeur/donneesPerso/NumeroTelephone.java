package ch.vd.demaut.domain.demandeur.donneesPerso;

import ch.vd.demaut.commons.vo.BaseValueObject;

public class NumeroTelephone extends BaseValueObject {

    private String value;

    public NumeroTelephone() {
    }

    public NumeroTelephone(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
