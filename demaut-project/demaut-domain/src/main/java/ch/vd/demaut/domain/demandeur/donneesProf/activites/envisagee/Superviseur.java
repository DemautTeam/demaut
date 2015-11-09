package ch.vd.demaut.domain.demandeur.donneesProf.activites.envisagee;

import javax.validation.constraints.Size;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;
import ch.vd.demaut.commons.vo.StringVOInterface;

/**
 * Représente le superviseur de l'activité envisagée quand l'activité est à titre dépendant
 */
@ValueObject
public class Superviseur extends BaseValueObject implements StringVOInterface{

    // ********************************************************* Field
    private String value;

    // ********************************************************* Constructor

    //For JPA usage only
    protected Superviseur() {
    }

    public Superviseur(String value) {
        this.value = value;
    }

    // ********************************************************* Getters
    @Size(max=50)
    public String getValue() {
        return value;
    }

}
