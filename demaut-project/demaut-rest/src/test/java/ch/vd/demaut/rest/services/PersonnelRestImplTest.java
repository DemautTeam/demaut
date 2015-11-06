package ch.vd.demaut.rest.services;

import ch.vd.demaut.domain.demandeur.Email;
import ch.vd.demaut.domain.demandeur.Fax;
import ch.vd.demaut.domain.demandeur.Localite;
import ch.vd.demaut.domain.demandeur.Pays;
import ch.vd.demaut.domain.demandeur.donneesPerso.*;
import ch.vd.demaut.rest.services.impl.PersonnelRestImpl;
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
public class PersonnelRestImplTest {

    private PersonnelRestImpl personelRest;

    private String referenceDeDemande;

    @Before
    public void setUp() throws Exception {
        assertNotNull(personelRest);
        referenceDeDemande = "9e88c31c-9cdf-4b8d-964a-b0af8fd06c1b";
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
        TelephonePrive telephonePrive = new TelephonePrive("022222222");
        TelephoneMobile telephoneMobile = new TelephoneMobile("07625225123");
        Fax fax = new Fax("023456789");
        Pays nationalite = Pays.Allemagne;
        Langue langue = Langue.Francais;
        Permis permis = new Permis(TypePermis.C);

        Response response = personelRest.renseignerLesDonneesPersonnelles(referenceDeDemande, nom.getValue(), prenom.getValue(), null, adresse.getVoie(), adresse.getComplement(), localite.getValue(),
                npa.getValue(), pays.getRefProgresID().getId(), email.getValue(), telephonePrive.getValue(), telephoneMobile.getValue(), fax.getValue(), genre.name(),
                dateDeNaissance, nationalite.getRefProgresID().getId(), langue.toString(), permis.getTypePermis().name(), null);
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }

    @Test
    public void testRecupererDonneesPersonnelles() throws Exception {
        Response response = personelRest.recupererDonneesPersonnelles(referenceDeDemande);
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }
}