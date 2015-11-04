package ch.vd.demaut.domain.demandeur.donneesProf.activites.envisagee;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Représente le superviseur de l'activité envisagée quand l'activité est titre independant
 */
@ValueObject
public class Superviseur extends BaseValueObject {

    @Size(max=50, message = "Veiullez saisir le nom du superviseur")
    private String value;

    // ********************************************************* Constructor

    public Superviseur() {
    }

    public Superviseur(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
