package ch.vd.demaut.services.demandeurs.donneesProf.test;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.domain.demandeur.donneesProf.DonneesProfessionnelles;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.*;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
import ch.vd.demaut.services.demandeurs.donneesProf.DonneesProfessionnellesService;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

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
        ReferenceDeDemande demandeReference = donneesProfessionnellesService.renseignerDonneesProfession(login, referenceDeDemande, profession, new CodeGLN("7601000000125"));
        assertThat(demandeReference).isNotNull();
    }

    @Test
    @Transactional
    public void testAjouterListeDeDiplomes() {
        DonneesProfessionnelles donneesProfessionnelles = donneesProfessionnellesService.recupererDonneesProfessionnellesParReferenceDemande(referenceDeDemande);
        assertThat(donneesProfessionnelles).isNotNull();
        assertThat(donneesProfessionnelles.getListeDesDiplomes().listerDiplomes()).hasSize(3);
    }

    private ListeDesDiplomes creerListeDiplomes() {
        ListeDesDiplomes listeDesDiplomes = new ListeDesDiplomes(new ArrayList<Diplome>());
        listeDesDiplomes.ajouterDiplome(
                new Diplome(TypeDiplomeAccepte.D_FORMATION_APPROFONDIE, new TitreFormation("Pneumologie pédiatrique /118"),
                        new DateObtention(new LocalDate()), new PaysObtention("Suisse"), null));
        listeDesDiplomes.ajouterDiplome(
                new Diplome(TypeDiplomeAccepte.D_FORMATION_INITIALE, new TitreFormation("CFR d'un diplôme étranger de médecin /8"),
                        new DateObtention(new LocalDate()), new PaysObtention("Tunisie"), new DateReconnaissance(new LocalDate())));
        listeDesDiplomes.ajouterDiplome(
                new Diplome(TypeDiplomeAccepte.D_POSTGRADE, new TitreFormation("Cardiologie /83"),
                        new DateObtention(new LocalDate()), new PaysObtention("Suisse"), null));
        return listeDesDiplomes;
    }

    // ********************************************************* Private methods for fixtures

    @Transactional(propagation = Propagation.REQUIRED)
    private void intialiserDemandeEnCours() {
        demandeEnCours = demandeAutorisationService.initialiserDemandeAutorisation(login, profession);
        demandeEnCours.validerEtAjouterDonneesProfessionnelles(new DonneesProfessionnelles(null, creerListeDiplomes().listerDiplomes()));
    }
}
