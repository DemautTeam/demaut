package ch.vd.demaut.services.demandeurs.donneesProf.test;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.domain.demandeur.donneesProf.DonneesProfessionnelles;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.DateObtention;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.DateReconnaissance;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.Diplome;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.PaysObtention;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.TitreFormation;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.TypeDiplomeAccepte;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
import ch.vd.demaut.services.demandeurs.donneesProf.DonneesProfessionnellesService;

@ContextConfiguration({"classpath*:/servicesTest-context.xml"})
@ActiveProfiles({"data"})
@RunWith(SpringJUnit4ClassRunner.class)
public class DonneesProfessionnellesServiceTest {

    @Inject
    private DonneesProfessionnellesService donneesProfessionnellesService;

    @Inject
    private DemandeAutorisationService demandeAutorisationService;

    private ReferenceDeDemande referenceDeDemande;
    private Profession profession;
    private Login login;
    private DemandeAutorisation demandeEnCours;


    @Before
    public void setUp() throws Exception {
        profession = Profession.Medecin;
        login = new Login("admin@admin");

        intialiserDemandeEnCours();

        assertThat(demandeEnCours).isNotNull();
        referenceDeDemande = demandeEnCours.getReferenceDeDemande();
        assertThat(referenceDeDemande).isNotNull();
        assertThat(profession).isNotNull();
        assertThat(login).isNotNull();
        assertThat(donneesProfessionnellesService).isNotNull();
    }

    @Test
    public void testAfficherDonneesProfession() throws Exception {
        Profession profession = donneesProfessionnellesService.afficherDonneesProfession(referenceDeDemande);
        assertThat(profession).isNotNull();
    }

    @Test
    public void testRenseignerDonneesProfession() throws Exception {
        ReferenceDeDemande demandeReference = donneesProfessionnellesService.renseignerDonneesProfession(referenceDeDemande, profession, new CodeGLN("7601000000125"));
        assertThat(demandeReference).isNotNull();
    }

    @Test
    @Transactional
    public void testAjouterListeDeDiplomes() {
        DonneesProfessionnelles donneesProfessionnelles = donneesProfessionnellesService.recupererDonneesProfessionnellesParReferenceDemande(referenceDeDemande);
        assertThat(donneesProfessionnelles).isNotNull();
        assertThat(donneesProfessionnelles.getListeDesDiplomes().listerDiplomes()).hasSize(3);
    }

    private void creerListeDiplomes(DonneesProfessionnelles donneesProfessionnelles) {
        Diplome diplome;

        diplome = new Diplome(TypeDiplomeAccepte.D_FORMATION_APPROFONDIE,
                new TitreFormation("Pneumologie pédiatrique /118"), new DateObtention(new LocalDate()),
                new PaysObtention("Suisse"), null);
        donneesProfessionnelles.validerEtAjouterDiplome(diplome);
        
        diplome = new Diplome(TypeDiplomeAccepte.D_FORMATION_INITIALE,
                new TitreFormation("CFR d'un diplôme étranger de médecin /8"), new DateObtention(new LocalDate()),
                new PaysObtention("Tunisie"), new DateReconnaissance(new LocalDate()));
        donneesProfessionnelles.validerEtAjouterDiplome(diplome);
        
        diplome = new Diplome(TypeDiplomeAccepte.D_POSTGRADE, new TitreFormation("Cardiologie /83"),
                new DateObtention(new LocalDate()), new PaysObtention("Suisse"), null);
        donneesProfessionnelles.validerEtAjouterDiplome(diplome);
    }

    // ********************************************************* Private methods for fixtures

    @Transactional(propagation = Propagation.REQUIRED)
    private void intialiserDemandeEnCours() {
        demandeEnCours = demandeAutorisationService.initialiserDemandeAutorisation(profession);
        creerListeDiplomes(demandeEnCours.getDonneesProfessionnelles());
        demandeEnCours.validerDonneesProfessionnelles();
    }
}
