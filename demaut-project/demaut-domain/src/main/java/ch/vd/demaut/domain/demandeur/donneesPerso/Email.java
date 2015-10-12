package ch.vd.demaut.domain.demandeur.donneesPerso;

import ch.vd.demaut.commons.vo.StringVO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Email extends StringVO {

    public Email() {
    }

    public Email(String value) {
        super(value);
    }

    @NotNull
    @Pattern(regexp = "^[_a-z0-9-\\.]+@[a-z0-9-]+\\.[a-z]+$")
    @Override
    public String getValue() {
        return super.getValue();
    }
}
