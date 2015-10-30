package ch.vd.demaut.domain.demandeur.donneesPerso;

import ch.vd.demaut.commons.vo.BaseValueObject;

public class NomDeCelibataire extends BaseValueObject {

    private String value;

    public NomDeCelibataire() {
    }

    public NomDeCelibataire(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
