package ch.vd.demaut.domain.demandeur.donneesPerso;

import ch.vd.demaut.commons.vo.BaseValueObject;

import javax.validation.constraints.Size;

public class NPA extends BaseValueObject {

    private String value;

    public NPA() {
    }

    public NPA(String value) {
        this.value = value;
    }

    @Size(max = 15)
    public String getValue() {
        return value;
    }
}
