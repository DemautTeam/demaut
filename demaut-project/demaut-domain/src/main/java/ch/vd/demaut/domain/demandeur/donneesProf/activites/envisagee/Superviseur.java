package ch.vd.demaut.domain.demandeur.donneesProf.activites.envisagee;

import javax.validation.constraints.Size;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;

/**
 * Représente le superviseur de l'activité envisagée quand l'activité est à titre dépendant
 */
@ValueObject
public class Superviseur extends BaseValueObject {

    // ********************************************************* Field
    private String value;

    // ********************************************************* Constructor

    //For JPA usage only
    Superviseur() {
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
