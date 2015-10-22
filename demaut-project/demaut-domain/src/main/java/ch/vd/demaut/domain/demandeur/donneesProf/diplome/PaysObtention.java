package ch.vd.demaut.domain.demandeur.donneesProf.diplome;

import ch.vd.demaut.commons.vo.BaseValueObject;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PaysObtention extends BaseValueObject {

    private String value;

    public PaysObtention() {
    }

    public PaysObtention(String value) {
        this.value = value;
    }

    @NotNull
    @Size(min = 1, max = 100)
    public String getValue() {
        return value;
    }
}
