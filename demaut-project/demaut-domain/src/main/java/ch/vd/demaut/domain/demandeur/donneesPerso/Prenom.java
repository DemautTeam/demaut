package ch.vd.demaut.domain.demandeur.donneesPerso;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.StringVO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ValueObject
public class Prenom extends StringVO {

    // ********************************************************* Constructor

    public Prenom() {
    }

    public Prenom(String value) {
        super(value);
    }

    @NotNull
    @Size(min = 1, max = 255)
    @Override
    public String getValue() {
        return super.getValue();
    }
}
