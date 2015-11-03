package ch.vd.demaut.domain.demandeur.donneesProf.activites;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;

/**
 * Représente le complement d'adresse
 */
@ValueObject
public class Complement extends BaseValueObject {

    private String value;

    // ********************************************************* Constructor

    public Complement() {
    }

    public Complement(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
