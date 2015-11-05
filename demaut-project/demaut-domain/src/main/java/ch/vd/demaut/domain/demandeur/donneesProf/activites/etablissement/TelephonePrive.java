package ch.vd.demaut.domain.demandeur.donneesProf.activites.etablissement;

import ch.vd.demaut.commons.vo.BaseValueObject;

import javax.validation.constraints.Pattern;

public class TelephonePrive extends BaseValueObject {

    // ********************************************************* Field
    private String value;

    // ********************************************************* Constructor
    //For JPA usage only
    TelephonePrive() {
    }

    // ********************************************************* Getters
    public TelephonePrive(String value) {
        this.value = value;
    }

    // ********************************************************* Getters

    /**
     * Le pattern valide le téléphone avec minimum 5 digits: 0xxxxx
     */
    @Pattern(regexp = "[0\\+]+\\d{5,}", message = "Veuillez entrer un numéro de téléphone valide...")
    public String getValue() {
        return value;
    }
}
