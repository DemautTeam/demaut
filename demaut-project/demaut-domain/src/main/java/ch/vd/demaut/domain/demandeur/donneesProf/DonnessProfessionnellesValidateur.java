package ch.vd.demaut.domain.demandeur.donneesProf;

import ch.vd.demaut.commons.validation.AbstractDataValidateur;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.ListeDesDiplomes;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class DonnessProfessionnellesValidateur extends AbstractDataValidateur<DonneesProfessionnelles> {

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

        // 1. Valide la structure d'une donneesProfessionnelles
        validerStructure(donneesProfessionnelles);

        // 2. Valide CodeGLN
        validerCodeGLN(donneesProfessionnelles);

        // 3. Valide Diplome
        validerDiplome(donneesProfessionnelles);

    }

    public void validerStructure(DonneesProfessionnelles donneesProfessionnelles) {
        Set<ConstraintViolation<DonneesProfessionnelles>> constraintViolationsResult = validateData(donneesProfessionnelles);

        if (constraintViolationsResult.size() > 0) {
            throw new DonneesProfessionnellesNonValideException();
        }
    }

    public void validerCodeGLN(DonneesProfessionnelles donneesProfessionnelles) {
        @SuppressWarnings("unused")
        CodeGLN codeGLN = donneesProfessionnelles.getCodeGLN();
        // TODO : implement me
    }

    private void validerDiplome(DonneesProfessionnelles donneesProfessionnelles) {
        @SuppressWarnings("unused")
        ListeDesDiplomes listeDesDiplomes = donneesProfessionnelles.getListeDesDiplomes();
        // TODO : implement me
    }
}
