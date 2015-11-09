package ch.vd.demaut.domain.demandeur;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;
import ch.vd.demaut.commons.vo.StringVOInterface;

@ValueObject
public class Telephone extends BaseValueObject implements StringVOInterface{

    // ********************************************************* Field
    private String value;

    // ********************************************************* Constructor

    //For JPA usage only
    protected Telephone() {
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
