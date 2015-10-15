package ch.vd.demaut.services.demandeurs.donneesProf.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

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
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.domain.demandeur.donneesProf.DonneesProfessionnelles;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.DateObtention;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.DateReconnaissance;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.Diplome;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.PaysObtention;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.ReferenceDeDiplome;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.TitreFormation;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.TitreFormationApprofondieProgres;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.TitreFormationInitialeProgres;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.TitreFormationPostgradeProgres;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.TypeDiplomeAccepte;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
import ch.vd.demaut.services.demandeurs.donneesProf.DonneesProfessionnellesService;

@ContextConfiguration({ "classpath*:/servicesTest-context.xml" })
@ActiveProfiles({ "data" })
@RunWith(SpringJUnit4ClassRunner.class)
public class DonneesProfessionnellesServiceTest {

    @Autowired
    private DonneesProfessionnellesService donneesProfessionnellesService;

    @Autowired
    private DemandeAutorisationService demandeAutorisationService;

    private Profession profession;
    private Login login;

    @Before
    public void setUp() throws Exception {
        assertThat(donneesProfessionnellesService).isNotNull();

        profession = Profession.Medecin;
    }

    @Test
    public void testRecupererDonneesProfession() throws Exception {
        intialiserDemandeEnCours("admin1@admin.com");

        Profession profession = donneesProfessionnellesService.recupererDonneesProfession(login);
        assertThat(profession).isNotNull();
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void testHasListeDeDiplomes() {
        intialiserDemandeEnCours("admin2@admin.com");

        DonneesProfessionnelles donneesProfessionnelles = donneesProfessionnellesService
                .recupererDonneesProfessionnelles(login);
        creerListeDiplomes(donneesProfessionnelles);
        assertThat(donneesProfessionnelles).isNotNull();
        assertThat(donneesProfessionnelles.getListeDesDiplomes().listerDiplomes()).hasSize(3);
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void testAjouterUnDiplomes() {
        intialiserDemandeEnCours("admin3@admin.com");

        DonneesProfessionnelles donneesProfessionnelles = donneesProfessionnellesService
                .recupererDonneesProfessionnelles(login);
        donneesProfessionnellesService.ajouterUnDiplome(login, new ReferenceDeDiplome(UUID.randomUUID().toString()),
                TypeDiplomeAccepte.D_FORMATION_APPROFONDIE,
                new TitreFormation(TitreFormationApprofondieProgres.ChirurgieDeLaMain.name()), null,
                new DateObtention(new LocalDate()), new PaysObtention(Pays.AfriqueDuSud.name()), null);
        assertThat(donneesProfessionnelles).isNotNull();
        assertThat(donneesProfessionnelles.getListeDesDiplomes().listerDiplomes()).hasSize(1);
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void testSupprimerUnDiplomes() {
        intialiserDemandeEnCours("admin4@admin.com");

        DonneesProfessionnelles donneesProfessionnelles = donneesProfessionnellesService
                .recupererDonneesProfessionnelles(login);
        assertThat(donneesProfessionnelles).isNotNull();
        donneesProfessionnellesService.ajouterUnDiplome(login, new ReferenceDeDiplome(UUID.randomUUID().toString()),
                TypeDiplomeAccepte.D_FORMATION_APPROFONDIE,
                new TitreFormation(TitreFormationApprofondieProgres.ChirurgieDeLaMain.name()), null,
                new DateObtention(new LocalDate()), new PaysObtention(Pays.AfriqueDuSud.name()), null);
        assertThat(donneesProfessionnelles.getListeDesDiplomes().listerDiplomes()).hasSize(1);

        Diplome diplome = donneesProfessionnelles.getListeDesDiplomes().listerDiplomes().get(0);
        donneesProfessionnellesService.supprimerUnDiplome(login, diplome.getReferenceDeDiplome());
        assertThat(donneesProfessionnelles).isNotNull();
        assertThat(donneesProfessionnelles.getListeDesDiplomes().listerDiplomes()).hasSize(0);
    }

    // ********************************************************* Private methods
    // for fixtures

    private void creerListeDiplomes(DonneesProfessionnelles donneesProfessionnelles) {
        donneesProfessionnelles.validerEtAjouterDiplome(new Diplome(
                new ReferenceDeDiplome(UUID.randomUUID().toString()), TypeDiplomeAccepte.D_FORMATION_APPROFONDIE,
                new TitreFormation(TitreFormationApprofondieProgres.PneumologiePediatrique.name()), null,
                new DateObtention(new LocalDate()), new PaysObtention(Pays.Suisse.name()), null));
        donneesProfessionnelles.validerEtAjouterDiplome(new Diplome(
                new ReferenceDeDiplome(UUID.randomUUID().toString()), TypeDiplomeAccepte.D_FORMATION_INITIALE,
                new TitreFormation(TitreFormationInitialeProgres.CFRDUnDiplomeEtrangerDeMedecin.name()), null,
                new DateObtention(new LocalDate()), new PaysObtention(Pays.Allemagne.name()),
                new DateReconnaissance(new LocalDate())));
        donneesProfessionnelles.validerEtAjouterDiplome(
                new Diplome(new ReferenceDeDiplome(UUID.randomUUID().toString()), TypeDiplomeAccepte.D_POSTGRADE,
                        new TitreFormation(TitreFormationPostgradeProgres.Cardiologie.name()), null,
                        new DateObtention(new LocalDate()), new PaysObtention(Pays.Suisse.name()), null));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private void intialiserDemandeEnCours(String loginStr) {
        this.login = new Login(loginStr);

        demandeAutorisationService.initialiserDemandeAutorisation(profession, new CodeGLN("7601000000125"), login);
    }
}
