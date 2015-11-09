package ch.vd.demaut.domain.demandeur.donneesProf.activites.envisagee;

import javax.validation.constraints.NotNull;

import org.joda.time.LocalDate;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;
import ch.vd.demaut.commons.vo.LocalDateVOInterface;

/**
 * Représente la date estimée pour le début de l'activité envisagée
 */
@ValueObject
public class DatePrevueDebut extends BaseValueObject implements LocalDateVOInterface {

    // ********************************************************* Fields
    private LocalDate value;

    // ********************************************************* Constructor

    //Used for JPA Only
    DatePrevueDebut() {
    }

    public DatePrevueDebut(LocalDate value) {
        this.value = value;
    }

    // ********************************************************* Getters
    @NotNull
    //TODO: Doit-on vérifier que la date est dans le future ?
    public LocalDate getValue() {
        return value;
    }
}

