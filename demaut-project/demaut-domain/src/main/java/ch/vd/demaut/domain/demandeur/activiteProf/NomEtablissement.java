package ch.vd.demaut.domain.demandeur.activiteProf;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ValueObject
public class NomEtablissement extends BaseValueObject {

    private String value;

    // ********************************************************* Constructor

    public NomEtablissement() {
    }

    public NomEtablissement(String value) {
        this.value = value;
    }

    // ********************************************************* Getters
    @NotNull
    @Size(min = 1, max = 120)
    public String getValue() {
        return value;
    }
}
