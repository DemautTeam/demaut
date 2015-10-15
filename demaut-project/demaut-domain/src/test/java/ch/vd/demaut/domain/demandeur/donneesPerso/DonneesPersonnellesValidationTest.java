package ch.vd.demaut.domain.demandeur.donneesPerso;

import ch.vd.demaut.commons.validation.test.AbstractValidationTest;
import ch.vd.demaut.domain.demandeur.Pays;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class DonneesPersonnellesValidationTest extends AbstractValidationTest<DonneesPersonnelles> {

    private DonneesPersonnelles donneesPersonnellesValides;
    private DonneesPersonnelles donneesPersonnellesInvalideNom;

    private Nom nomValide;
    private Nom nomInvalide;
    private Prenom prenomValide;
    private Localite localiteValide;
    private NPA npaValide;
    private Pays pays;
    private Adresse adresseValide;
    private Email emailValide;
    private Genre genreValide;
    private DateDeNaissance dateDeNaissance;
    private TelephonePrive telephonePrive;
    private TelephoneMobile numeroMobile;
    private Fax fax;
    private Pays nationalite;
    private Langue langue;
    private Permis permis;

    @Before
    public void init() {
        nomValide = new Nom("Doe");
        nomInvalide = new Nom(null);
        prenomValide = new Prenom("John");
        localiteValide = new Localite("Lausanne");
        npaValide = new NPA("1000");
        pays = Pays.Suisse;
        adresseValide = new Adresse("", "", localiteValide, npaValide, pays);
        emailValide = new Email("john.doe@nobody.com");
        genreValide = Genre.Feminin;
        dateDeNaissance = new DateDeNaissance(LocalDate.now());
        telephonePrive = new TelephonePrive("022222222");
        numeroMobile = new TelephoneMobile("07625225123");
        fax = new Fax("023456789");
        nationalite = Pays.Suisse;
        langue = Langue.Allemand;
        permis = new Permis(TypePermis.C);

        donneesPersonnellesValides = new DonneesPersonnelles(nomValide, prenomValide, null, adresseValide, emailValide, telephonePrive, numeroMobile, fax, genreValide, dateDeNaissance, nationalite, langue, permis);
        donneesPersonnellesInvalideNom = new DonneesPersonnelles(nomInvalide, prenomValide, null, adresseValide, emailValide, telephonePrive, numeroMobile, fax, genreValide, dateDeNaissance, nationalite, langue, permis);
    }

    @Test
    public void testDonneesPersonnellesValide() {
        validateWithMsg(donneesPersonnellesValides);
        testAllConstraintViolations();
    }

    @Test
    public void testDonneesPersonnellesNomInvalide() {
        validateWithMsg(donneesPersonnellesInvalideNom);
        addExpectedConstraintViolation("nom.value");
        testSomeConstraintViolations();
    }

}
