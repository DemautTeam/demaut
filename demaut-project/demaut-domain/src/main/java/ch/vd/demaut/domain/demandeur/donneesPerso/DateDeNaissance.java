package ch.vd.demaut.domain.demandeur.donneesPerso;

import ch.vd.demaut.commons.vo.BaseValueObject;
import org.joda.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

public class DateDeNaissance extends BaseValueObject {

    @NotNull
    private LocalDate value;

    // ********************************************************* Constructors

    public DateDeNaissance() {
    }

    public DateDeNaissance(LocalDate value) {
        this.value = value;
    }

    public DateDeNaissance(int annee, int mois, int jourDuMois) {
        this.value = new LocalDate(annee, mois, jourDuMois);
    }

    // ********************************************************* Business Methods

    @NotNull
    @Past
    public LocalDate getValue() {
        return value;
    }
}
