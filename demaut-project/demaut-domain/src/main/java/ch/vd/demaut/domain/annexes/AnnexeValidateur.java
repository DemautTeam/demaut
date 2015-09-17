package ch.vd.demaut.domain.annexes;

import ch.vd.demaut.commons.validation.ValidatorFactoryDefault;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.regex.Pattern;

public class AnnexeValidateur {

    /**
     * Singleton predicate instance
     */
    public static final AnnexeValidateur INSTANCE = new AnnexeValidateur();
    // ********************************************************* Static fields
    static private final int tailleMin = 1; // en octets
    static private final int tailleMax = 3 * 1024 * 1024; // en octets

    // ********************************************************* Singleton
    static private final int tailleNomFichierMax = 255; // en octets
    // ********************************************************* Fields
    private Validator validator;
    private Set<ConstraintViolation<Annexe>> constraintViolationsResult;
    AnnexeValidateur() {
        validator = ValidatorFactoryDefault.getValidator();
    }

    // ********************************************************* Constructeur

    /**
     * Factory returning the singleton instance.
     *
     * @return the singleton instance
     */
    public static AnnexeValidateur getInstance() {
        return INSTANCE;
    }

    // ********************************************************* Méthodes métier

    public static int getTailleMin() {
        return tailleMin;
    }

    public static int getTailleMax() {
        return tailleMax;
    }

    public static int getTailleNomFichierMax() {
        return tailleNomFichierMax;
    }

    /**
     * Vérifie que l'annexe est valide. Si non valide, renvoie une
     * {@link AnnexeNonValideException}
     */
    public void valider(Annexe annexe) {

        // 1. Valide la structure d'une annexe
        validerStructure(annexe);

        // 2. Valide taille annexe
        validerTaille(annexe);

        // 3. Valide nom fichier
        validerNomFichier(annexe);

    }

    public void validerStructure(Annexe annexe) {
        constraintViolationsResult = validator.validate(annexe);

        if (constraintViolationsResult.size() > 0) {
            throw new AnnexeNonValideException();
        }
    }

    public void validerTaille(Annexe annexe) {
        ContenuAnnexe contenu = annexe.getContenu();
        long taille = contenu.getTaille();

        if (taille < getTailleMin()) {
            throw new AnnexeNonValideException();
        }

        if (taille > getTailleMax()) {
            throw new AnnexeNonValideException();
        }
    }

    // TODO: Validateur de NomFichier qui doit être appelé par ce validateur
    private void validerNomFichier(Annexe annexe) {

        NomFichier nomFichier = annexe.getNomFichier();
        valideTailleNomFichier(nomFichier);

        String extension = nomFichier.extraireExtension();
        valideExtension(extension);

        String nomSansExtension = nomFichier.extraireNomSansExtension();
        valideNomFichierSansExtension(nomSansExtension);

    }

    private void valideTailleNomFichier(NomFichier nomFichier) {
        if (nomFichier.getNomFichier().length() > getTailleNomFichierMax()) {
            throw new AnnexeNonValideException();
        }
        if (nomFichier.getNomFichier().length() <= 0) {
            throw new AnnexeNonValideException();
        }
    }

    private void valideExtension(String extension) {
        for (FormatFichierAccepte formatAccepte : FormatFichierAccepte.values()) {
            if (formatAccepte.toString().equalsIgnoreCase(extension)) {
                return;
            }
        }
        throw new AnnexeNonValideException();
    }

    /**
     * Ne doit pas etre vide Ne doit pas commencer par . Ne doit pas contenir |
     * * ? \ : < > $ Ne doit pas finir avec .
     */
    private void valideNomFichierSansExtension(String nomSansExtension) {
        Pattern pattern = Pattern.compile("^(?!\\.)[^\\|\\*\\?\\\\:<>/$\"]*[^\\.\\|\\*\\?\\\\:<>/$\"]+$");
        if (!pattern.matcher(nomSansExtension).matches()) {
            throw new AnnexeNonValideException();
        }
    }

}
