package ch.vd.demaut.domain.demandes.autorisation;

import javax.validation.constraints.NotNull;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import ch.vd.demaut.commons.vo.BaseValueObject;

public class DateSoumissionDemande extends BaseValueObject {

    public static final String PATTERN = "dd.MM.yyyy hh.mm";

    private LocalDate value;

    // ********************************************************* Constructors

    public DateSoumissionDemande() {
    }

    public DateSoumissionDemande(LocalDate value) {
        this.value = value;
    }

    public DateSoumissionDemande(int annee, int mois, int jourDuMois) {
        this.value = new LocalDate(annee, mois, jourDuMois);
    }

    public DateSoumissionDemande(String value) {
        this.value = LocalDate.parse(value, DateTimeFormat.forPattern(PATTERN));
    }

    // ********************************************************* Business Methods

    @NotNull
    public LocalDate getValue() {
        return value;
    }
}
