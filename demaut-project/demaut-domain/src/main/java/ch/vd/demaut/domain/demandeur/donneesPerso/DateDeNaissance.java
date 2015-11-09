package ch.vd.demaut.domain.demandeur.donneesPerso;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.joda.time.LocalDate;

import ch.vd.demaut.commons.vo.BaseValueObject;
import ch.vd.demaut.commons.vo.LocalDateVOInterface;

/**
 * Date de naissance d'une personne
 *
 */
public class DateDeNaissance extends BaseValueObject implements LocalDateVOInterface {

    // ********************************************************* Fields
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
