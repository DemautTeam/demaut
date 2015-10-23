package ch.vd.demaut.domain.demandeur;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.StringVO;

import javax.validation.constraints.Pattern;

@ValueObject
public class Fax extends StringVO {

    public Fax() {
    }

    @Pattern(regexp = "[0\\+]+\\d{4,}", message = "Veuillez entrer un numéro de fax valide...")
    public Fax(String value) {
        super(value);
    }
}