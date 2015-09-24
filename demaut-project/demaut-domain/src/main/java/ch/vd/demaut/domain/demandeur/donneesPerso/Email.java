package ch.vd.demaut.domain.demandeur.donneesPerso;

import ch.vd.demaut.commons.vo.StringVO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by mourad on 14.09.15.
 */
public class Email extends StringVO {

    public Email(String value) {
        super(value);
    }

    @NotNull
    @Pattern(regexp = "^[_a-z0-9-]+(\\\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\\\.[a-z0-9-]+)+$")
    @Override
    public String getValue() {
        return super.getValue();
    }
}
