package ch.vd.demaut.domain.demandeur.donneesPerso;

import ch.vd.demaut.commons.vo.BaseValueObject;

import javax.validation.constraints.Pattern;

public class Fax extends BaseValueObject {

    private String value;

    public Fax() {
    }

    public Fax(String value) {
        this.value = value;
    }

    @Pattern(regexp = "[0\\+]+\\d{4,}", message = "Veuillez entrer un numéro de fax valide...")
    public String getValue() {
        return value;
    }
}