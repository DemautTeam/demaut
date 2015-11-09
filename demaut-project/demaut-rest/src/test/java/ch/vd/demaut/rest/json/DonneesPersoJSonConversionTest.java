package ch.vd.demaut.rest.json;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import ch.vd.demaut.domain.demandeur.Email;
import ch.vd.demaut.domain.demandeur.Localite;
import ch.vd.demaut.domain.demandeur.Pays;
import ch.vd.demaut.domain.demandeur.Telephone;
import ch.vd.demaut.domain.demandeur.donneesPerso.Adresse;
import ch.vd.demaut.domain.demandeur.donneesPerso.DateDeNaissance;
import ch.vd.demaut.domain.demandeur.donneesPerso.DonneesPersonnelles;
import ch.vd.demaut.domain.demandeur.donneesPerso.Genre;
import ch.vd.demaut.domain.demandeur.donneesPerso.Langue;
import ch.vd.demaut.domain.demandeur.donneesPerso.NPA;
import ch.vd.demaut.domain.demandeur.donneesPerso.Nom;
import ch.vd.demaut.domain.demandeur.donneesPerso.Permis;
import ch.vd.demaut.domain.demandeur.donneesPerso.Prenom;
import ch.vd.demaut.domain.demandeur.donneesPerso.TypePermis;

public class DonneesPersoJSonConversionTest extends AbstractJSonConversionTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();
        ;
    }

    @Test
    public void testDonneesPersoJson() {
        DonneesPersonnelles donneesPerso = buildDonneesPerso();

        String jsonStrExpected = 
                "{\"id\":null,\"version\":0,\"nom\":\"Newman\",\"prenom\":\"prenom\",\"nomDeCelibataire\":\"Newman2\",\"genre\":\"Feminin\",\"dateDeNaissance\":1447023600000,\"adresse\":{\"id\":null,\"version\":0,\"voie\":\"\",\"complement\":\"\",\"npa\":\"1000\",\"localite\":\"Lausanne\",\"pays\":{\"name\":\"Suisse\",\"id\":1,\"libl\":\"Suisse\"}},\"nationalite\":{\"name\":\"Andorre\",\"id\":7,\"libl\":\"Andorre\"},\"email\":\"john.doe@nobody.com\",\"telephonePrive\":\"022222222\",\"telephoneMobile\":\"07625225123\",\"fax\":\"+411215156\",\"langue\":{\"name\":\"Autre\",\"id\":0,\"libl\":\"Autre\"},\"permis\":{\"typePermis\":\"B\",\"autrePermis\":\"\"}}";

        assertJsonStr(donneesPerso, jsonStrExpected);

    }

    private DonneesPersonnelles buildDonneesPerso() {
        Localite localite = new Localite("Lausanne");
        NPA npa = new NPA("1000");
        Pays pays = Pays.Suisse;
        Adresse adresse = new Adresse("", "", localite, npa, pays);
        Email email = new Email("john.doe@nobody.com");
        Genre genre = Genre.Feminin;
        DateDeNaissance dateDeNaissance = new DateDeNaissance(LocalDate.now());
        Telephone numeroTelephone = new Telephone("022222222");
        Telephone numeroMobile = new Telephone("07625225123");
        Telephone fax = new Telephone("+411215156");
        Pays nationalite = Pays.Andorre;
        Langue langue = Langue.Autre;
        Permis permis = new Permis(TypePermis.B);
        Nom nom = new Nom("Newman");
        Prenom prenom = new Prenom("prenom");
        Nom nomCelib = new Nom("Newman2");

        DonneesPersonnelles donneesPerso = new DonneesPersonnelles(nom, prenom, nomCelib, adresse, email,
                numeroTelephone, numeroMobile, fax, genre, dateDeNaissance, nationalite, langue, permis);

        return donneesPerso;
    }


}
