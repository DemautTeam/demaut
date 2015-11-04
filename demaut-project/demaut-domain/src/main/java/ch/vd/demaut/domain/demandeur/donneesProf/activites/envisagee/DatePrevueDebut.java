package ch.vd.demaut.domain.demandeur.donneesProf.activites.envisagee;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

/**
 * Représente la date estimée pour de l'activité envisagée
 */
@ValueObject
public class DatePrevueDebut extends BaseValueObject {

    @NotNull
    @Past
    private Date value;

    // ********************************************************* Constructor

    public DatePrevueDebut() {
    }

    public DatePrevueDebut(Date value) {
        this.value = value;
    }

    public Date getValue() {
        return value;
    }
}

