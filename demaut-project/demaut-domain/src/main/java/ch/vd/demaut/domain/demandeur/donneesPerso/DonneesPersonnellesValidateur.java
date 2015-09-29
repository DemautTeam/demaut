package ch.vd.demaut.domain.demandeur.donneesPerso;

import ch.vd.demaut.commons.validation.AbstractDataValidateur;
import ch.vd.demaut.domain.exception.AnnexeNonValideException;

import javax.validation.ConstraintViolation;
import java.util.Set;

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
