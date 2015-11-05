package ch.vd.demaut.domain.demandeur.donneesProf.activites.etablissement;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;

import javax.validation.constraints.Pattern;

@ValueObject
public class TelephoneMobile extends BaseValueObject {

    // ********************************************************* Field
    private String value;

    // ********************************************************* Constructor

    //For JPA usage only
    TelephoneMobile() {
    }

    // ********************************************************* Getters
    public TelephoneMobile(String value) {
        this.value = value;
    }

    /**
     * Le pattern valide le téléphone avec minimum 5 digits: 0xxxxx
     */
    @Pattern(regexp = "[0\\+]+\\d{5,}", message = "Veuillez entrer un numéro de téléphone mobile valide...")
    public String getValue() {
        return value;
    }
}
