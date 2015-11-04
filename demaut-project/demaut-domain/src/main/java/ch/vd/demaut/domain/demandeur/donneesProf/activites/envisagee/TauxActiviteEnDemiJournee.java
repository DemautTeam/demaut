package ch.vd.demaut.domain.demandeur.donneesProf.activites.envisagee;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;

import javax.validation.constraints.NotNull;

/**
 * Représente le nombre en demi journée par semaine de l'activité envisagée
 */
@ValueObject
public class TauxActiviteEnDemiJournee extends BaseValueObject {

    @NotNull
    private Integer value;

    // ********************************************************* Constructor

    public TauxActiviteEnDemiJournee() {
    }

    public TauxActiviteEnDemiJournee(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
