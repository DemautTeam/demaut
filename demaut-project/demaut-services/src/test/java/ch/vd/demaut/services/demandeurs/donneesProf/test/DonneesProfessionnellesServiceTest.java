package ch.vd.demaut.services.demandeurs.donneesProf.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

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
import ch.vd.demaut.domain.demandeur.Pays;
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
    @Transactional
    public void testHasListeDeDiplomes() {
        DonneesProfessionnelles donneesProfessionnelles = donneesProfessionnellesService.recupererDonneesProfessionnellesParReferenceDemande(referenceDeDemande);
        creerListeDiplomes(donneesProfessionnelles);
        assertThat(donneesProfessionnelles).isNotNull();
        assertThat(donneesProfessionnelles.getListeDesDiplomes().listerDiplomes()).hasSize(3);
    }

    @Test
    @Transactional
    public void testAjouterUnDiplomes() {
        DonneesProfessionnelles donneesProfessionnelles = donneesProfessionnellesService.recupererDonneesProfessionnellesParReferenceDemande(referenceDeDemande);
        donneesProfessionnellesService.ajouterUnDiplome(referenceDeDemande,
                new ReferenceDeDiplome(UUID.randomUUID().toString()),
                TypeDiplomeAccepte.D_FORMATION_APPROFONDIE,
                new TitreFormation(TitreFormationApprofondieProgres.ChirurgieDeLaMain.name()),
                new DateObtention(new LocalDate()), new PaysObtention(Pays.AfriqueDuSud.name()), null);
        assertThat(donneesProfessionnelles).isNotNull();
        assertThat(donneesProfessionnelles.getListeDesDiplomes().listerDiplomes()).hasSize(1);
    }

    @Test
    @Transactional
    public void testSupprimerUnDiplomes() {
        DonneesProfessionnelles donneesProfessionnelles = donneesProfessionnellesService.recupererDonneesProfessionnellesParReferenceDemande(referenceDeDemande);
        assertThat(donneesProfessionnelles).isNotNull();
        donneesProfessionnellesService.ajouterUnDiplome(referenceDeDemande,
                new ReferenceDeDiplome(UUID.randomUUID().toString()),
                TypeDiplomeAccepte.D_FORMATION_APPROFONDIE,
                new TitreFormation(TitreFormationApprofondieProgres.ChirurgieDeLaMain.name()),
                new DateObtention(new LocalDate()), new PaysObtention(Pays.AfriqueDuSud.name()), null);
        assertThat(donneesProfessionnelles.getListeDesDiplomes().listerDiplomes()).hasSize(1);

        Diplome diplome = donneesProfessionnelles.getListeDesDiplomes().listerDiplomes().get(0);
        donneesProfessionnellesService.supprimerUnDiplome(referenceDeDemande, diplome.getReferenceDeDiplome());
        assertThat(donneesProfessionnelles).isNotNull();
        assertThat(donneesProfessionnelles.getListeDesDiplomes().listerDiplomes()).hasSize(0);
    }

    private void creerListeDiplomes(DonneesProfessionnelles donneesProfessionnelles) {
        donneesProfessionnelles.validerEtAjouterDiplome(
                new Diplome(new ReferenceDeDiplome(UUID.randomUUID().toString()), TypeDiplomeAccepte.D_FORMATION_APPROFONDIE,
                        new TitreFormation(TitreFormationApprofondieProgres.PneumologiePediatrique.name()),
                        new DateObtention(new LocalDate()), new PaysObtention(Pays.Suisse.name()), null));
        donneesProfessionnelles.validerEtAjouterDiplome(
                new Diplome(new ReferenceDeDiplome(UUID.randomUUID().toString()), TypeDiplomeAccepte.D_FORMATION_INITIALE,
                        new TitreFormation(TitreFormationInitialeProgres.CFRDUnDiplomeEtrangerDeMedecin.name()),
                        new DateObtention(new LocalDate()), new PaysObtention(Pays.Allemagne.name()), new DateReconnaissance(new LocalDate())));
        donneesProfessionnelles.validerEtAjouterDiplome(
                new Diplome(new ReferenceDeDiplome(UUID.randomUUID().toString()), TypeDiplomeAccepte.D_POSTGRADE,
                        new TitreFormation(TitreFormationPostgradeProgres.Cardiologie.name()),
                        new DateObtention(new LocalDate()), new PaysObtention(Pays.Suisse.name()), null));
    }

    // ********************************************************* Private methods for fixtures

    @Transactional(propagation = Propagation.REQUIRED)
    private void intialiserDemandeEnCours() {
        demandeEnCours = demandeAutorisationService.initialiserDemandeAutorisation(profession);
    }
}
