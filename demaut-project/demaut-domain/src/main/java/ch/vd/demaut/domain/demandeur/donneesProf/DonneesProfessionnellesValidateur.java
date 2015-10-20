package ch.vd.demaut.domain.demandeur.donneesProf;

import ch.vd.demaut.commons.validation.AbstractDataValidateur;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.ListeDesDiplomes;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class DonneesProfessionnellesValidateur extends AbstractDataValidateur<DonneesProfessionnelles> {

    // ********************************************************* Static fields

    // ********************************************************* Singleton

    // ********************************************************* Fields

    // ********************************************************* Constructeur


    // ********************************************************* Méthodes métier


    /**
     * Vérifie que la donneesProfessionnelles est valide. Si non valide, renvoie une
     * {@link DonneesProfessionnellesNonValideException}
     */
    @Override
    public void valider(DonneesProfessionnelles donneesProfessionnelles) {

        validerStructure(donneesProfessionnelles);

    }

    public void validerStructure(DonneesProfessionnelles donneesProfessionnelles) {
        Set<ConstraintViolation<DonneesProfessionnelles>> constraintViolationsResult = validateData(donneesProfessionnelles);

        if (constraintViolationsResult.size() > 0) {
            throw new DonneesProfessionnellesNonValideException();
        }
    }

}
