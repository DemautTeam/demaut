package ch.vd.demaut.data.demandes.autorisation.repo;

import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisationFactory;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.domain.demandeur.donneesProf.DonneesProfessionnelles;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.*;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.domain.utilisateurs.Utilisateur;
import ch.vd.demaut.domain.utilisateurs.UtilisateurRepository;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration({"classpath*:/data-jpa-test-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class DemandeAutorisationRepositoryTest {

    // ********************************************************* Repos et
    // Services injectés
    @Inject
    private DemandeAutorisationRepository demandeAutorisationRepository;

    @Inject
    private UtilisateurRepository utilisateurRepository;

    @Inject
    private JpaTransactionManager transactionManagerDemaut;

    @Inject
    private DemandeAutorisationFactory demandeAutorisationFactory;


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
        DemandeAutorisation demandeAutorisation = demandeAutorisationFactory.initierDemandeAutorisation(utilisateur.getLogin(), Profession.Medecin, null);
        assertThat(demandeAutorisation.getId()).isNull();
        demandeAutorisationRepository.store(demandeAutorisation);
        assertThat(demandeAutorisation.getId()).isNotNull();

        commitTransaction(transaction);
    }

    @Test
    public void sauvegarderUneDemandeAvecAnnexes() {
        TransactionStatus transaction = beginTransaction();

        Utilisateur utilisateur = creerUtilisateur();

        // Sauvegarder la demande
        DemandeAutorisation demandeAutorisation = demandeAutorisationFactory.initierDemandeAutorisation(utilisateur.getLogin(), Profession.Chiropraticien, null);
        byte[] contenu = "AnnexeContenu".getBytes();
        Annexe annexe = new Annexe(TypeAnnexe.CV, "test.pdf", contenu, "01.01.2015 11:00");
        demandeAutorisation.validerEtAttacherAnnexe(annexe);
        assertThat(demandeAutorisation.getId()).isNull();
        demandeAutorisationRepository.store(demandeAutorisation);
        assertThat(demandeAutorisation.getId()).isNotNull();
        Collection<Annexe> annexes = demandeAutorisation.listerLesAnnexes();
        assertThat(annexes).isNotEmpty();

        // Recuperer la demande
        DemandeAutorisation memeDemande = demandeAutorisationRepository.findBy(demandeAutorisation.getId());
        assertThat(memeDemande).isEqualTo(demandeAutorisation);

        //Tester les annexes
        assertThat(memeDemande.listerLesAnnexes()).isNotEmpty();
        Annexe premiereAnnexe = memeDemande.listerLesAnnexes().iterator().next();
        assertThat(premiereAnnexe.getContenu().getContenu()).isEqualTo(contenu);

        commitTransaction(transaction);
    }

    @Test
    public void sauvegarderUneDemandeAvecDonneeProfessionnellesEtDiplomes() {
        TransactionStatus transaction = beginTransaction();

        Utilisateur utilisateur = creerUtilisateur();

        // Sauvegarder la demande
        DemandeAutorisation demandeAutorisation = demandeAutorisationFactory.initierDemandeAutorisation(utilisateur.getLogin(), Profession.Medecin, null);
        CodeGLN codeGLN = new CodeGLN("7601000000125");
        ListeDesDiplomes listeDesDiplomes = creerListeDiplomes();
        DonneesProfessionnelles donneesProfessionnelles = new DonneesProfessionnelles(codeGLN, listeDesDiplomes.listerDiplomes());
        demandeAutorisation.validerEtAjouterDonneesProfessionnelles(donneesProfessionnelles);
        assertThat(demandeAutorisation.getId()).isNull();
        demandeAutorisationRepository.store(demandeAutorisation);
        assertThat(demandeAutorisation.getId()).isNotNull();
        Collection<Diplome> diplomes = demandeAutorisation.getDonneesProfessionnelles().getListeDesDiplomes().listerDiplomes();
        assertThat(diplomes).isNotEmpty();

        // Recuperer la demande
        DemandeAutorisation memeDemande = demandeAutorisationRepository.findBy(demandeAutorisation.getId());
        assertThat(memeDemande).isEqualTo(demandeAutorisation);

        //Tester les diplomes
        assertThat(memeDemande.getDonneesProfessionnelles().getListeDesDiplomes().listerDiplomes()).isNotEmpty();
        Diplome premiereDiplome = memeDemande.getDonneesProfessionnelles().getListeDesDiplomes().listerDiplomes().iterator().next();
        assertThat(premiereDiplome.getTypeDiplomeAccepte()).isEqualTo(TypeDiplomeAccepte.D_FORMATION_APPROFONDIE);

        commitTransaction(transaction);
    }

    // ********************************************************* Methods privées

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