package ch.vd.demaut.domain.demandeur.donneesProf.activites.envisagee;

import javax.validation.constraints.NotNull;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;

/**
 * Représente le nombre en demi journée par semaine de l'activité envisagée
 */
@ValueObject
public class TauxActiviteEnDemiJournee extends BaseValueObject {

    private Integer value;

    // ********************************************************* Constructor

    // Only for JPA
    TauxActiviteEnDemiJournee() {
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
