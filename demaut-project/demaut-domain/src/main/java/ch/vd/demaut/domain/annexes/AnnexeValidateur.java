package ch.vd.demaut.domain.annexes;

import ch.vd.demaut.commons.validation.ValidatorFactoryDefault;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.regex.Pattern;

public class AnnexeValidateur {

    // ********************************************************* Singleton
    /**
     * Singleton predicate instance
     */
    public static final AnnexeValidateur INSTANCE = new AnnexeValidateur();

    /**
     * Factory returning the singleton instance.
     *
     * @return the singleton instance
     */
    public static AnnexeValidateur getInstance() {
        return INSTANCE;
    }

    // ********************************************************* Fields

    private Validator validator;

    private AnnexeMetadataValidateur annexeMetadataValidateur;

    private Set<ConstraintViolation<Annexe>> annexeConstraintViolationsResult;

    private Set<ConstraintViolation<AnnexeMetadata>> annexeMetadataConstraintViolationsResult;


    // ********************************************************* Constructeur

    AnnexeValidateur() {
        validator = ValidatorFactoryDefault.getValidator();
        annexeMetadataValidateur = AnnexeMetadataValidateur.getInstance();
    }

    /**
     * Vérifie que l'annexe est valide.
     * Si non valide, renvoie une {@link AnnexeNonValideException}
     */
    public void valider(Annexe annexe) {

        //1. Valide la structure d'une annexe
        validerStructure(annexe);

        //2. Valide taille annexe
        validerTaille(annexe);

    }

    public void validerStructure(Annexe annexe) {
        annexeConstraintViolationsResult = validator.validate(annexe);
        annexeMetadataConstraintViolationsResult = annexeMetadataValidateur.valider(annexe.getAnnexeMetadata());

        if (!annexeConstraintViolationsResult.isEmpty() || !annexeMetadataConstraintViolationsResult.isEmpty()) {
            throw new AnnexeNonValideException();
        }
    }

    public void validerTaille(Annexe annexe) {
        if (annexe.getContenuAnnexe() == null) {
            throw new AnnexeNonValideException();
        }
    }
}
