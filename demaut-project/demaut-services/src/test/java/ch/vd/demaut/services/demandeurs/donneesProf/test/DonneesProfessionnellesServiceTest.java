package ch.vd.demaut.services.demandeurs.donneesProf.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandeur.Pays;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.domain.demandeur.donneesProf.DonneesProfessionnelles;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.DateObtention;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.DateReconnaissance;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.Diplome;
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

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DonneesProfessionnellesService donneesProfessionnellesService;

    @Autowired
    private DemandeAutorisationService demandeAutorisationService;

    private DemandeAutorisation demandeAutorisation;
    private Profession profession;
    private Login login;

    private String genericLogin = "test@test.com";
    private static int currentLoginCounter = 1;

    @Before
    public void setUp() throws Exception {
        assertThat(donneesProfessionnellesService).isNotNull();

        profession = Profession.Medecin;
    }

    private String getCurrentLogin() {
        String loginStr = currentLoginCounter++ + genericLogin;
        logger.info("Login actuel {}", loginStr);
        return loginStr;
    }

    @Test
    public void testRecupererDonneesProfession() throws Exception {
        intialiserDemandeEnCours(getCurrentLogin());

        Profession profession = donneesProfessionnellesService.recupererProfessionDeDemande(login,
                demandeAutorisation.getReferenceDeDemande());
        assertThat(profession).isNotNull();
    }

    @Test
    @Ignore // Car en fait ne teste aucun service
    public void testHasListeDeDiplomes() {
        intialiserDemandeEnCours(getCurrentLogin());

        DonneesProfessionnelles donneesProfessionnelles = donneesProfessionnellesService
                .recupererDonneesProfessionnelles(login, demandeAutorisation.getReferenceDeDemande());
        
        creerListeDiplomes(donneesProfessionnelles);
        assertThat(donneesProfessionnelles).isNotNull();
        assertThat(donneesProfessionnelles.getListeDesDiplomes().listerDiplomes()).hasSize(3);
    }

    @Test
    @Ignore // Car en fait ne teste aucun service
    public void testAjouterUnDiplomes() {
        intialiserDemandeEnCours(getCurrentLogin());

        donneesProfessionnellesService.ajouterUnDiplome(login, demandeAutorisation.getReferenceDeDemande(),
                new ReferenceDeDiplome(UUID.randomUUID().toString()), TypeDiplomeAccepte.D_FORMATION_APPROFONDIE,
                new TitreFormation(TitreFormationApprofondieProgres.ChirurgieDeLaMain.name()), null,
                new DateObtention(new LocalDate()), Pays.AfriqueDuSud, null);
        DonneesProfessionnelles resultDonneesProfessionnelles = donneesProfessionnellesService
                .recupererDonneesProfessionnelles(login, demandeAutorisation.getReferenceDeDemande());
        assertThat(resultDonneesProfessionnelles).isNotNull();
        assertThat(resultDonneesProfessionnelles.getListeDesDiplomes().listerDiplomes()).hasSize(1);
    }

    @Test
    @Ignore // Car en fait ne teste aucun service
    public void testSupprimerUnDiplomes() {
        intialiserDemandeEnCours(getCurrentLogin());

        DonneesProfessionnelles donneesProfessionnelles = donneesProfessionnellesService
                .recupererDonneesProfessionnelles(login, demandeAutorisation.getReferenceDeDemande());
        assertThat(donneesProfessionnelles).isNotNull();
        donneesProfessionnellesService.ajouterUnDiplome(login, demandeAutorisation.getReferenceDeDemande(),
                new ReferenceDeDiplome(UUID.randomUUID().toString()), TypeDiplomeAccepte.D_FORMATION_APPROFONDIE,
                new TitreFormation(TitreFormationApprofondieProgres.ChirurgieDeLaMain.name()), null,
                new DateObtention(new LocalDate()), Pays.AfriqueDuSud, null);

        // Rafraichissement des donnees progfessionnelles pour être en phase avec la base.
        DonneesProfessionnelles donneesProfessionnellesAfterInsert = donneesProfessionnellesService
                .recupererDonneesProfessionnelles(login, demandeAutorisation.getReferenceDeDemande());

        assertThat(donneesProfessionnellesAfterInsert.getListeDesDiplomes().listerDiplomes()).hasSize(1);
        // ----- Test suppression ------
        Diplome diplome = donneesProfessionnellesAfterInsert.getListeDesDiplomes().listerDiplomes().get(0);
        donneesProfessionnellesService.supprimerUnDiplome(login, demandeAutorisation.getReferenceDeDemande(),
                diplome.getReferenceDeDiplome());
        DonneesProfessionnelles resultDonneesProfessionnelles = donneesProfessionnellesService
                .recupererDonneesProfessionnelles(login, demandeAutorisation.getReferenceDeDemande());

        assertThat(resultDonneesProfessionnelles.getListeDesDiplomes().listerDiplomes()).hasSize(0);
    }

    @Test
    public void testValiderEtRenseignerCodeGLN() {
        intialiserDemandeEnCours(getCurrentLogin());
        donneesProfessionnellesService.validerEtRenseignerCodeGLN(login, demandeAutorisation.getReferenceDeDemande(),
                new CodeGLN("4719512002889"));

        DonneesProfessionnelles donneesProfessionnelles = donneesProfessionnellesService
                .recupererDonneesProfessionnelles(login, demandeAutorisation.getReferenceDeDemande());
        assertThat(donneesProfessionnelles).isNotNull();
        assertThat(donneesProfessionnelles.getCodeGLN()).isNotNull();
        assertThat(donneesProfessionnelles.getCodeGLN().getValue()).isEqualTo("4719512002889");
    }

    @Test
    public void testRecupererDiplomesSaisis() throws Exception {
        intialiserDemandeEnCours(getCurrentLogin());
        DonneesProfessionnelles donneesProfessionnelles = donneesProfessionnellesService
                .recupererDonneesProfessionnelles(login, demandeAutorisation.getReferenceDeDemande());
        assertThat(donneesProfessionnelles).isNotNull();
        donneesProfessionnellesService.ajouterUnDiplome(login, demandeAutorisation.getReferenceDeDemande(),
                new ReferenceDeDiplome(UUID.randomUUID().toString()), TypeDiplomeAccepte.D_FORMATION_APPROFONDIE,
                new TitreFormation(TitreFormationApprofondieProgres.ChirurgieDeLaMain.name()), null,
                new DateObtention(new LocalDate()), Pays.AfriqueDuSud, null);
        // L'objet Données professionnelles n'est pas rafraichi, l'instance est déconnectée...
        DonneesProfessionnelles donneesProfessionnellesAfter = donneesProfessionnellesService
                .recupererDonneesProfessionnelles(login, demandeAutorisation.getReferenceDeDemande());
        assertThat(donneesProfessionnellesAfter.getListeDesDiplomes().listerDiplomes()).hasSize(1);

        List<Diplome> diplomesSaisis = donneesProfessionnellesService.recupererDiplomesSaisis(login,
                demandeAutorisation.getReferenceDeDemande());

        assertThat(diplomesSaisis).isNotNull();
        assertThat(diplomesSaisis).isNotEmpty();
    }

    // ********************************************************* Private methods
    // for fixtures

    private void creerListeDiplomes(DonneesProfessionnelles donneesProfessionnelles) {
        donneesProfessionnelles.validerEtAjouterDiplome(new Diplome(
                new ReferenceDeDiplome(UUID.randomUUID().toString()), TypeDiplomeAccepte.D_FORMATION_APPROFONDIE,
                new TitreFormation(TitreFormationApprofondieProgres.PneumologiePediatrique.name()), null,
                new DateObtention(new LocalDate()), Pays.Suisse, null));
        donneesProfessionnelles.validerEtAjouterDiplome(new Diplome(
                new ReferenceDeDiplome(UUID.randomUUID().toString()), TypeDiplomeAccepte.D_FORMATION_INITIALE,
                new TitreFormation(TitreFormationInitialeProgres.CFRDUnDiplomeEtrangerDeMedecin.name()), null,
                new DateObtention(new LocalDate()), Pays.Allemagne, new DateReconnaissance(new LocalDate())));
        donneesProfessionnelles.validerEtAjouterDiplome(
                new Diplome(new ReferenceDeDiplome(UUID.randomUUID().toString()), TypeDiplomeAccepte.D_POSTGRADE,
                        new TitreFormation(TitreFormationPostgradeProgres.Cardiologie.name()), null,
                        new DateObtention(new LocalDate()), Pays.Suisse, null));
    }

    /**
     * Cette methode utilise des transaction uniquement pour l'initialisation de la deamde
     * 
     * @param loginStr
     */
    // TOD réviser la méthode d'initialisation de ce test
    @Transactional(propagation = Propagation.REQUIRED)
    private void intialiserDemandeEnCours(String loginStr) {
        this.login = new Login(loginStr);

        demandeAutorisation = demandeAutorisationService.initialiserDemandeAutorisation(profession,
                new CodeGLN("7601000000125"), login);
        assertThat(demandeAutorisation).isNotNull();
    }
}
