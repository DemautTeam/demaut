package ch.vd.demaut.data.demandes.autorisation.repo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisationFactory;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;
import ch.vd.demaut.domain.demandeur.donneesProf.DonneesProfessionnelles;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.DateObtention;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.DateReconnaissance;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.Diplome;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.PaysObtention;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.TitreFormation;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.TypeDiplomeAccepte;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.domain.utilisateurs.Utilisateur;
import ch.vd.demaut.domain.utilisateurs.UtilisateurRepository;

@ContextConfiguration({ "classpath*:/data-jpa-test-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class DemandeAutorisationRepositoryTest {

    // ********************************************************* Repos et
    // Services injectés
    @Inject
    private DemandeAutorisationRepository demandeAutorisationRepository;

    @Inject
    private UtilisateurRepository utilisateurRepository;

    @Inject
    private DemandeAutorisationFactory demandeAutorisationFactory;

    @Inject
    private PlatformTransactionManager transactionManagerDemaut;

    // ********************************************************* Setup
    @Before
    public void setUp() throws Exception {
        assertThat(demandeAutorisationRepository).isNotNull();
        assertThat(utilisateurRepository).isNotNull();
        assertThat(transactionManagerDemaut).isNotNull();
        assertThat(demandeAutorisationFactory).isNotNull();
    }

    // ********************************************************* Tests

    @Test
    public void sauvegarderUneDemande() {
        TransactionStatus transaction = beginTransaction();

        Utilisateur utilisateur = creerUtilisateur();

        // Sauvegarder la demande
        DemandeAutorisation demandeAutorisation = demandeAutorisationFactory
                .initierDemandeAutorisation(utilisateur.getLogin(), Profession.Medecin, null);
        assertThat(demandeAutorisation.getId()).isNull();
        demandeAutorisationRepository.store(demandeAutorisation);
        assertThat(demandeAutorisation.getId()).isNotNull();

        commitTransaction(transaction);
    }

    @Test
    // TODO: Nettoyer le code avec des belles methodes lisibles
    public void sauvegarderUneDemandeAvecAnnexes() {
        TransactionStatus transaction = beginTransaction();

        Utilisateur utilisateur = creerUtilisateur();

        // Sauvegarder la demande
        DemandeAutorisation demandeAutorisation = demandeAutorisationFactory
                .initierDemandeAutorisation(utilisateur.getLogin(), Profession.Chiropraticien, null);
        byte[] contenu = "AnnexeContenu".getBytes();
        Annexe annexe = new Annexe(TypeAnnexe.CV, "test.pdf", contenu, "01.01.2015 11:00");
        demandeAutorisation.validerEtAttacherAnnexe(annexe);
        assertThat(demandeAutorisation.getId()).isNull();
        demandeAutorisationRepository.store(demandeAutorisation);
        assertThat(demandeAutorisation.getId()).isNotNull();
        Collection<Annexe> annexes = demandeAutorisation.listerLesAnnexes();
        assertThat(annexes).isNotEmpty();

        // commitTransaction(transaction);

        // transaction = beginTransaction();

        // Recuperer la demande
        DemandeAutorisation memeDemande = demandeAutorisationRepository
                .recupererDemandeParReference(demandeAutorisation.getReferenceDeDemande());
        assertThat(memeDemande).isEqualTo(demandeAutorisation);

        // Tester les annexes
        // Ceci ne marche pas car OpenJPA enhanced la class
        // assertThat(memeDemande.listerLesAnnexes()).containsExactlyElementsOf(annexes);
        List<Annexe> memesAnnexes = memeDemande.listerLesAnnexes();
        Annexe premiereAnnexe = memesAnnexes.iterator().next();
        assertThat(premiereAnnexe).isEqualTo(annexe);
        assertThat(premiereAnnexe.getContenu().getContenu()).isEqualTo(contenu);

        commitTransaction(transaction);
    }

    @Test
    public void sauvegarderUneDemandeAvecDonneeProfessionnellesEtDiplomes() {
        TransactionStatus transaction = beginTransaction();

        Utilisateur utilisateur = creerUtilisateur();

        // Sauvegarder la demande
        DemandeAutorisation demandeAutorisation = demandeAutorisationFactory
                .initierDemandeAutorisation(utilisateur.getLogin(), Profession.Medecin, null);
        
        DonneesProfessionnelles donneesProfessionnelles = demandeAutorisation.getDonneesProfessionnelles();
        creerListeDiplomes(donneesProfessionnelles);
        demandeAutorisation.validerDonneesProfessionnelles();
        
        assertThat(demandeAutorisation.getId()).isNull();
        demandeAutorisationRepository.store(demandeAutorisation);
        assertThat(demandeAutorisation.getId()).isNotNull();
        Collection<Diplome> diplomes = demandeAutorisation.getDonneesProfessionnelles().getListeDesDiplomes()
                .listerDiplomes();
        assertThat(diplomes).isNotEmpty();

        // Recuperer la demande
        DemandeAutorisation memeDemande = demandeAutorisationRepository.findBy(demandeAutorisation.getId());
        assertThat(memeDemande).isEqualTo(demandeAutorisation);

        // Tester les diplomes
        assertThat(memeDemande.getDonneesProfessionnelles().getListeDesDiplomes().listerDiplomes()).isNotEmpty();
        Diplome premiereDiplome = memeDemande.getDonneesProfessionnelles().getListeDesDiplomes().listerDiplomes()
                .iterator().next();
        assertThat(premiereDiplome.getTypeDiplomeAccepte()).isEqualTo(TypeDiplomeAccepte.D_FORMATION_APPROFONDIE);

        commitTransaction(transaction);
    }

    // ********************************************************* Methods privées

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

    private Utilisateur creerUtilisateur() {
        // Créer et sauvegarder un Utilisateur
        Login login1 = new Login("login1");
        Utilisateur utilisateur = new Utilisateur(login1);
        utilisateurRepository.store(utilisateur);
        assertThat(utilisateur.getId()).isNotNull();
        utilisateur = utilisateurRepository.findBy(utilisateur.getId());
        return utilisateur;
    }

    private void commitTransaction(TransactionStatus transaction) {
        transactionManagerDemaut.commit(transaction);
    }

    private TransactionStatus beginTransaction() {
        // Voir
        // http://elnur.pro/programmatic-transaction-management-in-tests-with-spring/
        // http://stackoverflow.com/questions/6864574/openjpa-lazy-fetching-does-not-work
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        return transactionManagerDemaut.getTransaction(definition);
    }

}