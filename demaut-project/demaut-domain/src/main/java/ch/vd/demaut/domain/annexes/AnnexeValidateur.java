package ch.vd.demaut.domain.annexes;

import ch.vd.demaut.commons.validation.ValidatorFactoryDefault;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.regex.Pattern;

public class AnnexeValidateur {

    // ********************************************************* Static fields
    static private final int tailleMin = 1; //en octets
    static private final int tailleMax = 3 * 1024 * 1024; //en octets

    static private final int longueurMax = 255; //en octets

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

    private Set<ConstraintViolation<Annexe>> constraintViolationsResult;

    // ********************************************************* Constructeur

    AnnexeValidateur() {
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
    public void valider(Annexe annexe) {

        //1. Valide la structure d'une annexe
        validerStructure(annexe);

        //2. Valide taille annexe
        validerTaille(annexe);

        //3. Valide nom fichier
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

    public void validerNomFichier(Annexe annexe) {
        NomFichier nomFichier = annexe.getNomFichier();
        String extension = nomFichier.extraireExtension();
        String nomSansExtension = nomFichier.extraireNonSansExtension();
        boolean accepte = nomFichier.getNomFichier().length() < 255 && nomFichier.getNomFichier().length() > 0 &&
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
