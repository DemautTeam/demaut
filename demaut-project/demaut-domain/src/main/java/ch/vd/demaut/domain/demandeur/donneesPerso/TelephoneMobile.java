package ch.vd.demaut.domain.demandeur.donneesPerso;

import ch.vd.demaut.commons.vo.BaseValueObject;

import javax.validation.constraints.Pattern;

public class TelephoneMobile extends BaseValueObject {

    private String value;

    public TelephoneMobile() {
    }

    public TelephoneMobile(String value) {
        this.value = value;
    }

    @Pattern(regexp = "[0\\+]+\\d{5,}", message = "Veuillez entrer un numéro de téléphone valide...")
    public String getValue() {
        return value;
    }
}
