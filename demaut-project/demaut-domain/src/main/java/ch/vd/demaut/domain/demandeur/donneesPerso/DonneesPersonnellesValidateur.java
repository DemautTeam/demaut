package ch.vd.demaut.domain.demandeur.donneesPerso;

import ch.vd.demaut.commons.validation.AbstractDataValidateur;
import ch.vd.demaut.commons.validation.DataValidateur;
import ch.vd.demaut.commons.validation.ValidatorFactoryDefault;
import ch.vd.demaut.domain.annexes.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.regex.Pattern;

public class DonneesPersonnellesValidateur extends AbstractDataValidateur<DonneesPersonnelles> {

    // ********************************************************* Static fields

    // ********************************************************* Fields

    // ********************************************************* Constructeur

    // ********************************************************* Méthodes métier

    /**
     * Vérifie que l'annexe est valide. Si non valide, renvoie une
     * {@link AnnexeNonValideException}
     */
    @Override
    public void valider(DonneesPersonnelles donneesPersonnelles) {
        Set<ConstraintViolation<DonneesPersonnelles>> constraintViolationsResult = validateData(donneesPersonnelles);

        if (constraintViolationsResult.size() > 0) {
            throw new DonneesPersonnellesNonValideException();
        }


    }

}
