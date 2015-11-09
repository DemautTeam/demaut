package ch.vd.demaut.domain.demandeur.donneesPerso;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;

import javax.validation.constraints.Size;

@ValueObject
public class NomDeCelibataire extends BaseValueObject {

    private String value;

    public NomDeCelibataire() {
    }

    public NomDeCelibataire(String value) {
        this.value = value;
    }

    @Size(min = 1, max = 120)
    public String getValue() {
        return value;
    }
}
