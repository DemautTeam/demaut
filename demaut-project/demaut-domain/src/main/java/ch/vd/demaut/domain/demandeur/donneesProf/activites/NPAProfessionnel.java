package ch.vd.demaut.domain.demandeur.donneesProf.activites;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * Ce NPAProfessionnel est forcement un NPAProfessionnel suisse donc il doit contenir 4 chiffres.
 * TODO: Renommer en NPASuisse & Changer la String en Integer
 */
@ValueObject
public class NPAProfessionnel extends BaseValueObject {

    private String value;

    public NPAProfessionnel() {
    }

    public NPAProfessionnel(String value) {
        this.value = value;
    }

    @Size(min = 4, max = 4)
    @Pattern(regexp = "\\d{4}")
    public String getValue() {
        return value;
    }
}
