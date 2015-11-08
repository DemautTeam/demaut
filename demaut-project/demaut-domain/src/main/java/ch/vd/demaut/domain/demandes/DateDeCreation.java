package ch.vd.demaut.domain.demandes;

import javax.validation.constraints.NotNull;

import org.joda.time.LocalDate;

import ch.vd.demaut.commons.vo.BaseValueObject;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;

/**
 * Date de création d'une {@link DemandeAutorisation}
 *
 */
public class DateDeCreation extends BaseValueObject {

    // ********************************************************* Fields
    private LocalDate value;

    // ********************************************************* Constructors

    /**
     * Construit une {@link DateDeCreation} à la date du jours system
     */
    public DateDeCreation() {
        this.value = new LocalDate();
    }

    public DateDeCreation(LocalDate value) {
        this.value = value;
    }

    public DateDeCreation(int annee, int mois, int jourDuMois) {
        this.value = new LocalDate(annee, mois, jourDuMois);
    }

    // ********************************************************* Business Methods

    @NotNull
    public LocalDate getValue() {
        return value;
    }
}
