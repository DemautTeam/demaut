package ch.vd.demaut.domain.demandeur;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@ValueObject
public class Telephone extends BaseValueObject {

    // ********************************************************* Field
    private String value;

    // ********************************************************* Constructor

    //For JPA usage only
    Telephone() {
    }

    // ********************************************************* Getters
    public Telephone(String value) {
        this.value = value;
    }

    /**
     * Le pattern valide le téléphone avec minimum 5 digits: 0xxxxx
     */
    @Size(min=5, max=12)
    @Pattern(regexp = "[0\\+]+\\d{5,12}", message = "Veuillez entrer un numéro de téléphone valide")
    public String getValue() {
        return value;
    }
}
