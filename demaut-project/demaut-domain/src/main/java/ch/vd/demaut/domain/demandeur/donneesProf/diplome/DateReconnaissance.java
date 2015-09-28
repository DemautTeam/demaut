package ch.vd.demaut.domain.demandeur.donneesProf.diplome;

import ch.vd.demaut.commons.vo.BaseValueObject;
import org.joda.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

public class DateReconnaissance extends BaseValueObject {

    @NotNull
    private LocalDate value;

    // ********************************************************* Constructors

    public DateReconnaissance() {
    }

    public DateReconnaissance(LocalDate value) {
        this.value = value;
    }

    public DateReconnaissance(int annee, int mois, int jourDuMois) {
        this.value = new LocalDate(annee, mois, jourDuMois);
    }

    // ********************************************************* Business Methods

    @NotNull
    @Past
    public LocalDate getValue() {
        return value;
    }
}
