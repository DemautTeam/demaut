package ch.vd.demaut.domain.demandeur.donneesProf.activites;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;
import ch.vd.demaut.commons.vo.StringVOInterface;

import javax.validation.constraints.NotNull;

/**
 * Représente la voie de l'adresse
 *
 * Par exemple :
 *  - "rue Recordon, 1"
 *
 */
@ValueObject
public class Voie extends BaseValueObject implements StringVOInterface {

    private String value;

    // ********************************************************* Constructor

    //Used only for JPA
    protected Voie() {
    }

    public Voie(String value) {
        this.value = value;
    }

    @NotNull
    public String getValue() {
        return value;
    }
}
