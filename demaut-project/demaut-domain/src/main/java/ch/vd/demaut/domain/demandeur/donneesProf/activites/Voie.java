package ch.vd.demaut.domain.demandeur.donneesProf.activites;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;

import javax.validation.constraints.NotNull;

/**
 * Représente la voie de l'adresse
 *
 * Par exemple :
 *  - "rue Recordon, 1"
 *
 */
@ValueObject
public class Voie extends BaseValueObject {

    private String value;

    // ********************************************************* Constructor

    public Voie() {
    }

    public Voie(String value) {
        this.value = value;
    }

    @NotNull
    public String getValue() {
        return value;
    }
}
