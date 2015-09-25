package ch.vd.demaut.domain.demandeur.donneesPerso;

import java.util.Set;

import javax.validation.ConstraintViolation;

import ch.vd.demaut.commons.validation.AbstractDataValidateur;
import ch.vd.demaut.domain.annexes.AnnexeNonValideException;

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
