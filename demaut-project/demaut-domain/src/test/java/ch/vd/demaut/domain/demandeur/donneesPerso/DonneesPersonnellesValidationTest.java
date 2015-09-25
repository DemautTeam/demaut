package ch.vd.demaut.domain.demandeur.donneesPerso;

import ch.vd.demaut.commons.validation.test.AbstractValidationTest;
import ch.vd.demaut.domain.demandeur.Pays;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by mourad on 25.09.15.
 */
public class DonneesPersonnellesValidationTest extends AbstractValidationTest {

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
    private Nationalite nationalite;
    private Permis permis;
    private DateDeNaissance dateDeNaissance;
    private NumeroTelephone numeroTelephone;
    private NumeroTelephone numeroMobile;
    private NumeroTelephone numeroFax;

    @Before
    public void init(){
        nomValide = new Nom("Doe");
        nomInvalide = new Nom(null);
        prenomValide = new Prenom("John");
        localiteValide = new Localite("Lausanne");
        npaValide = new NPA("1000");
        pays = new Pays("Suisse");
        adresseValide = new Adresse("","", localiteValide, npaValide, pays);
        emailValide = new Email("john.doe@nobody.com");
        genreValide = Genre.Feminin;
        nationalite = new Nationalite("Suisse");
        permis = new Permis(TypePermis.Aucun);
        dateDeNaissance = new DateDeNaissance(LocalDate.now());
        numeroTelephone = new NumeroTelephone("022222222");
        numeroMobile = new NumeroTelephone("07625225123");
        numeroFax = new NumeroTelephone("023456789");

        donneesPersonnellesValides = new DonneesPersonnelles(nomValide,prenomValide,null,genreValide, dateDeNaissance, adresseValide, emailValide, numeroTelephone, numeroMobile, numeroFax );
        donneesPersonnellesInvalideNom = new DonneesPersonnelles(nomInvalide,prenomValide,null,genreValide, dateDeNaissance, adresseValide, emailValide, numeroTelephone, numeroMobile, numeroFax );

    }

    @Test
    public void testDonneesPersonnellesValide(){
        validateWithMsg(donneesPersonnellesValides);
        testAllConstraintViolations();
    }

    @Test
    public void testDonneesPersonnellesNomInvalide(){
        validateWithMsg(donneesPersonnellesInvalideNom);
        addExpectedConstraintViolation("nom.value");
        testSomeConstraintViolations();
    }




}
