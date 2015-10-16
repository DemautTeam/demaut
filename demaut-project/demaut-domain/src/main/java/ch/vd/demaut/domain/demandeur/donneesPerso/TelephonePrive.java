package ch.vd.demaut.domain.demandeur.donneesPerso;

import ch.vd.demaut.commons.vo.StringVO;

import javax.validation.constraints.Pattern;

public class TelephonePrive extends StringVO {

    public TelephonePrive() {
    }

    public TelephonePrive(String value) {
        super(value);
    }

    @Pattern(regexp = "[0\\+]+\\d{4,}", message = "Veuillez entrer un numéro de téléphone valide...")
    @Override
    public String getValue() {
        return super.getValue();
    }
}
