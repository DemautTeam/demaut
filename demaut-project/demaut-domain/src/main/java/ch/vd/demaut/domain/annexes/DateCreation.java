package ch.vd.demaut.domain.annexes;

import ch.vd.demaut.commons.vo.BaseValueObject;
import org.joda.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

public class DateCreation extends BaseValueObject {

    @NotNull
    private LocalDate value;

    // ********************************************************* Constructors

    public DateCreation() {
    }

    public DateCreation(LocalDate value) {
        this.value = value;
    }

    public DateCreation(int annee, int mois, int jourDuMois) {
        this.value = new LocalDate(annee, mois, jourDuMois);
    }

    // ********************************************************* Business Methods

    @NotNull
    @Past
    public LocalDate getValue() {
        return value;
    }
}
