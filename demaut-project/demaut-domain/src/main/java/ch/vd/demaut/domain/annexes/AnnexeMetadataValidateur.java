package ch.vd.demaut.domain.annexes;

import ch.vd.demaut.commons.validation.ValidatorFactoryDefault;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.regex.Pattern;

public class AnnexeMetadataValidateur {

    // ********************************************************* Static fields
    static private final int tailleMin = 1; //en octets
    static private final int tailleMax = 3 * 1024 * 1024; //en octets

    static private final int longueurMax = 255; //en octets

    // ********************************************************* Singleton
    /**
     * Singleton predicate instance
     */
    public static final AnnexeMetadataValidateur INSTANCE = new AnnexeMetadataValidateur();

    /**
     * Factory returning the singleton instance.
     *
     * @return the singleton instance
     */
    public static AnnexeMetadataValidateur getInstance() {
        return INSTANCE;
    }

    // ********************************************************* Fields

    private Validator validator;

    private Set<ConstraintViolation<AnnexeMetadata>> constraintViolationsResult;

    // ********************************************************* Constructeur

    AnnexeMetadataValidateur() {
        validator = ValidatorFactoryDefault.getValidator();
    }

    // ********************************************************* Méthodes métier

    public static int getTailleMin() {
        return tailleMin;
    }

    public static int getTailleMax() {
        return tailleMax;
    }

    public static int getLongueurMax() {
        return longueurMax;
    }

    /**
     * Vérifie que l'annexe est valide.
     * Si non valide, renvoie une {@link AnnexeNonValideException}
     */
    public Set<ConstraintViolation<AnnexeMetadata>> valider(AnnexeMetadata annexeMetadata) {

        //1. Valide la structure d'une annexe
        validerStructure(annexeMetadata);

        //2. Valide taille annexe
        validerTaille(annexeMetadata);

        //3. Valide nom fichier
        validerNomFichier(annexeMetadata);

        return constraintViolationsResult;
    }

    public void validerStructure(AnnexeMetadata annexeMetadata) {
        constraintViolationsResult = validator.validate(annexeMetadata);

        if (constraintViolationsResult.size() > 0) {
            throw new AnnexeNonValideException();
        }
    }

    public void validerTaille(AnnexeMetadata annexeMetadata) {
        long taille = annexeMetadata.getTailleContenu();

        if (taille < getTailleMin()) {
            throw new AnnexeNonValideException();
        }

        if (taille > getTailleMax()) {
            throw new AnnexeNonValideException();
        }
    }

    public void validerNomFichier(AnnexeMetadata annexeMetadata) {
        NomFichier nomFichier = annexeMetadata.getNomFichier();
        String extension = nomFichier.extraireExtension();
        String nomSansExtension = nomFichier.extraireNonSansExtension();
        boolean accepte = nomFichier.getNomFichier().length() < getLongueurMax() && nomFichier.getNomFichier().length() > 0 &&
                verifieSiExtensionEstAcceptee(extension) && verifieSiNomSansExtensionEstAcceptee(nomSansExtension);
        if (!accepte) {
            throw new AnnexeNonValideException();
        }
    }

    /**
     * Ne doit pas etre vide
     * Ne doit pas commencer par .
     * Ne doit pas contenir | * ? \ : < > $
     * Ne doit pas finir avec .
     */
    private boolean verifieSiNomSansExtensionEstAcceptee(String nomSansExtension) {
        Pattern pattern = Pattern.compile("^(?!\\.)[^\\|\\*\\?\\\\:<>/$\"]*[^\\.\\|\\*\\?\\\\:<>/$\"]+$");
        return pattern.matcher(nomSansExtension).matches();
    }

    private boolean verifieSiExtensionEstAcceptee(String extension) {
        boolean accepte = false;
        for (FormatFichierAccepte formatAccepte : FormatFichierAccepte.values()) {
            if (formatAccepte.toString().equalsIgnoreCase(extension)) {
                accepte = true;
                break;
            }
        }
        return accepte;
    }
}
