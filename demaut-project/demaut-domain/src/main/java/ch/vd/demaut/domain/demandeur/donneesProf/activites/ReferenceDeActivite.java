package ch.vd.demaut.domain.demandeur.donneesProf.activites;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;

import javax.validation.constraints.NotNull;

@ValueObject
public class ReferenceDeActivite extends BaseValueObject {

    // ********************************************************* Fields

    private String value;

    // ********************************************************* Constructor

    public ReferenceDeActivite() {
    }

    public ReferenceDeActivite(String reference) {
        this.value = reference;
    }

    // ********************************************************* Getters
    @NotNull
    public String getValue() {
        return value;
    }

}
