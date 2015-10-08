package ch.vd.demaut.domain.demandeur.donneesProf.diplome;

import ch.vd.demaut.commons.vo.BaseValueObject;
import org.joda.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

public class DateReconnaissance extends BaseValueObject {

    @NotNull
    private Date value;

    // ********************************************************* Constructors

    public DateReconnaissance() {
    }

    public DateReconnaissance(LocalDate value) {
        this.value = value.toDate();
    }

    public DateReconnaissance(int annee, int mois, int jourDuMois) {
        this.value = new LocalDate(annee, mois, jourDuMois).toDate();
    }

    // ********************************************************* Business Methods

    @NotNull
    @Past
    public Date getValue() {
        return value;
    }
}
