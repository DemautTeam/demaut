package ch.vd.demaut.domain.demandeur.donneesPerso;

import javax.validation.constraints.Size;

import ch.vd.demaut.commons.vo.BaseValueObject;
import ch.vd.demaut.commons.vo.StringVOInterface;

public class NPA extends BaseValueObject implements StringVOInterface {

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
