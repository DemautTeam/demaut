package ch.vd.demaut.domain.demandeur.donneesProf.diplome;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ValueObject
public class TitreFormation extends BaseValueObject {

    private String value;

    public TitreFormation() {
    }

    public TitreFormation(String value) {
        this.value = value;
    }

    @NotNull
    @Size(min = 1, max = 255)
    public String getValue() {
        return value;
    }
}
