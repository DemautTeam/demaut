package ch.vd.demaut.services.demandeurs.donneesPerso;

import static org.assertj.core.api.Assertions.assertThat;

import ch.vd.demaut.domain.demandeur.Telephone;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandeur.Pays;
import ch.vd.demaut.domain.demandeur.donneesPerso.Adresse;
import ch.vd.demaut.domain.demandeur.donneesPerso.DateDeNaissance;
import ch.vd.demaut.domain.demandeur.donneesPerso.DonneesPersonnelles;
import ch.vd.demaut.domain.demandeur.Email;
import ch.vd.demaut.domain.demandeur.donneesPerso.Genre;
import ch.vd.demaut.domain.demandeur.donneesPerso.Langue;
import ch.vd.demaut.domain.demandeur.Localite;
import ch.vd.demaut.domain.demandeur.donneesPerso.NPA;
import ch.vd.demaut.domain.demandeur.donneesPerso.Nom;
import ch.vd.demaut.domain.demandeur.donneesPerso.Permis;
import ch.vd.demaut.domain.demandeur.donneesPerso.Prenom;
import ch.vd.demaut.domain.demandeur.donneesPerso.TypePermis;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;

@ContextConfiguration({ "classpath*:/servicesTest-context.xml" })
@ActiveProfiles({ "data" })
@RunWith(SpringJUnit4ClassRunner.class)
public class DonneesPersonnellesServiceTest {

    @Autowired
    private DemandeAutorisationService demandeAutorisationService;

    @Autowired
    private DonneesPersonnellesService donneesPersonnellesService;

    private Profession profession;
    private Login login;
    private DemandeAutorisation demandeEnCours;

    @Before
    public void setUp() throws Exception {
        profession = Profession.Medecin;
        login = new Login("admin@admin");

        if (demandeEnCours == null) {
            intialiserDemandeEnCours();
        }

        assertThat(demandeEnCours).isNotNull();
        ReferenceDeDemande referenceDeDemande = demandeEnCours.getReferenceDeDemande();
        assertThat(referenceDeDemande).isNotNull();
        assertThat(profession).isNotNull();
        assertThat(login).isNotNull();
        assertThat(donneesPersonnellesService).isNotNull();

    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void testRecupererDonneesPersonnelles() throws Exception {
        DonneesPersonnelles donneesPersonnelles = donneesPersonnellesService.recupererDonneesPersonnelles(login, demandeEnCours.getReferenceDeDemande());
        assertThat(donneesPersonnelles).isNotNull();
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void testRenseignerLesDonneesPersonnelles() throws Exception {
        Nom nom = new Nom("Doe");
        Prenom prenom = new Prenom("John");
        Localite localite = new Localite("Lausanne");
        NPA npa = new NPA("1000");
        Pays pays = Pays.Suisse;
        Adresse adresse = new Adresse("", "", localite, npa, pays);
        Email email = new Email("john.doe@nobody.com");
        Genre genre = Genre.Feminin;
        DateDeNaissance dateDeNaissance = new DateDeNaissance(LocalDate.now().minusDays(1000));
        Telephone telephonePrive = new Telephone("022222222");
        Telephone telephoneMobile = new Telephone("07625225123");
        Telephone fax = new Telephone("023456789");
        Pays nationalite = Pays.Allemagne;
        Langue langue = Langue.Autre;
        Permis permis = new Permis(TypePermis.C);

        donneesPersonnellesService.renseignerLesDonneesPersonnelles(login, demandeEnCours.getReferenceDeDemande(), nom, prenom, null, adresse, email,
                telephonePrive, telephoneMobile, fax, genre, dateDeNaissance, nationalite, langue, permis);

        DonneesPersonnelles donneesPersonnelles = donneesPersonnellesService.recupererDonneesPersonnelles(login, demandeEnCours.getReferenceDeDemande());
        assertThat(donneesPersonnelles).isNotNull();
        assertThat(donneesPersonnelles.getEmail().getValue()).isEqualTo(email.getValue());
    }

    // ********************************************************* Private methods
    // for fixtures

    @Transactional(propagation = Propagation.REQUIRED)
    private void intialiserDemandeEnCours() {
        demandeEnCours = demandeAutorisationService.initialiserDemandeAutorisation(profession,
                new CodeGLN("7601000000125"), login);
    }
}