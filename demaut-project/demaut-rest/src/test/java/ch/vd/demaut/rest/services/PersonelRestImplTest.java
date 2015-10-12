package ch.vd.demaut.rest.services;

import ch.vd.demaut.domain.demandeur.Pays;
import ch.vd.demaut.domain.demandeur.donneesPerso.*;
import ch.vd.demaut.rest.services.impl.PersonelRestImpl;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Ignore("TODO Should mock @Context HttpHeaders demaut-user-id")
@ContextConfiguration({"classpath*:restTest-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PersonelRestImplTest {

    private PersonelRestImpl personelRest;

    @Before
    public void setUp() throws Exception {
        assertNotNull(personelRest);
    }

    @Test
    public void testListerLesNationalites() throws Exception {
        Response response = personelRest.listerLesNationalites();
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }

    @Test
    public void testListerLesLangues() throws Exception {
        Response response = personelRest.listerLesLangues();
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }

    @Test
    public void testRenseignerLesDonneesPersonnelles() throws Exception {
        Nom nom = new Nom("Doe");
        Prenom prenom = new Prenom("John");
        Localite localite = new Localite("Lausanne");
        NPA npa = new NPA("1000");
        Pays pays = Pays.Suisse;
        Adresse adresse = new Adresse("", "", localite, npa, pays);
        Email email = new Email("john.doe@nobody.com");
        Genre genre = Genre.Feminin;
        String dateDeNaissance = "31.05.1980";
        NumeroTelephone telephonePrive = new NumeroTelephone("022222222");
        NumeroTelephone telephoneMobile = new NumeroTelephone("07625225123");
        NumeroTelephone fax = new NumeroTelephone("023456789");
        Pays nationalite = Pays.Allemagne;
        Langue langue = Langue.Allemand;
        Permis permis = new Permis(TypePermis.C);

        Response response = personelRest.renseignerLesDonneesPersonnelles(nom.getValue(), prenom.getValue(), null, adresse.getVoie(), adresse.getComplement(), localite.getValue(),
                npa.getValue(), String.valueOf(pays.getRefProgresID()), email.getValue(), telephonePrive.getValue(), telephoneMobile.getValue(), fax.getValue(), genre.name(),
                dateDeNaissance, String.valueOf(nationalite.getRefProgresID()), String.valueOf(langue.getRefProgresID()), permis.getTypePermis().name(), null);
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }
}