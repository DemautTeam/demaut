package ch.vd.demaut.domain.demandeur.donneesPerso;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;
import ch.vd.demaut.commons.vo.StringVOInterface;

@ValueObject
public class AutrePermis extends BaseValueObject implements StringVOInterface {

    private String value;

    public AutrePermis() {
    }

    public AutrePermis(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
