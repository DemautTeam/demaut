package ch.vd.demaut.commons.vo;

import org.joda.time.LocalDate;

import javax.validation.constraints.NotNull;

abstract public class LocalDateVO extends BaseValueObject {

    // ********************************************************* Fields
    @NotNull
    final private LocalDate value;

    // ********************************************************* Constructor

    public LocalDateVO(int annee, int mois, int jourDuMois) {
        super();
        this.value = new LocalDate(annee, mois, jourDuMois);
    }

    public LocalDateVO(LocalDate value) {
        super();
        this.value = value;
    }

    // ********************************************************* Getters

    public LocalDate getLocalDate() {
        return value;
    }

}
