package ch.vd.demaut.domain.demandeur.donneesPerso;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;
import ch.vd.demaut.commons.vo.StringVOInterface;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ValueObject
public class Prenom extends BaseValueObject implements StringVOInterface {

    private  String value;

    // ********************************************************* Constructor

    //For JPA usage
    protected Prenom() {
    }

    public Prenom(String value) {
        this.value = value;
    }

    // ********************************************************* Getters
    @NotNull
    @Size(min = 1, max = 255)
    public String getValue() {
        return value;
    }
}
