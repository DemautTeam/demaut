package ch.vd.demaut.domain.demandeur.donneesProf.activites.envisagee;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;
import ch.vd.demaut.commons.vo.IntegerVOInterface;

import javax.validation.constraints.NotNull;

/**
 * Représente le nombre en demi journée par semaine de l'activité envisagée
 */
@ValueObject
public class TauxActiviteEnDemiJournee extends BaseValueObject implements IntegerVOInterface {

    private Integer value;

    // ********************************************************* Constructor

    // Only for JPA
    protected TauxActiviteEnDemiJournee() {
    }

    public TauxActiviteEnDemiJournee(Integer value) {
        this.value = value;
    }

    // ********************************************************* Getters

    @NotNull
    public Integer getValue() {
        return value;
    }
}
