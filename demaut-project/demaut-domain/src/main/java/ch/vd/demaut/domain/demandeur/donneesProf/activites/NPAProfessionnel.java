package ch.vd.demaut.domain.demandeur.donneesProf.activites;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.StringVO;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * Ce NPAProfessionnel est forcement un NPAProfessionnel suisse donc il doit contenir 4 chiffres.
 *
 */
@ValueObject
public class NPAProfessionnel extends StringVO {

    public NPAProfessionnel() {
    }

    public NPAProfessionnel(String value) {
        super(value);
    }

    @Size(min = 4, max = 4)
    @Pattern(regexp = "\\d{4}")
    @Override
    public String getValue() {
        return super.getValue();
    }
}
