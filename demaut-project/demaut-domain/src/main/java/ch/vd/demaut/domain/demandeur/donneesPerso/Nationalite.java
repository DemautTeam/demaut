package ch.vd.demaut.domain.demandeur.donneesPerso;

import ch.vd.demaut.commons.vo.BaseValueObject;

public class Nationalite extends BaseValueObject {

    private String value;

    public Nationalite() {
    }

    public Nationalite(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
