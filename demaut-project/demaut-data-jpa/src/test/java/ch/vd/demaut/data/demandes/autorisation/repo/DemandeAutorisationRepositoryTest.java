package ch.vd.demaut.data.demandes.autorisation.repo;

import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisationFactory;
import ch.vd.demaut.domain.demandes.autorisation.ProfessionDeLaSante;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.domain.utilisateurs.Utilisateur;
import ch.vd.demaut.domain.utilisateurs.UtilisateurRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.inject.Inject;
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
    private DemandeAutorisationFactory demautFactory;


    // ********************************************************* Setup
    @Before
    public void setUp() throws Exception {

        assertThat(demandeAutorisationRepository).isNotNull();
        assertThat(utilisateurRepository).isNotNull();

        assertThat(transactionManagerDemaut).isNotNull();

        assertThat(demautFactory).isNotNull();

    }

    // ********************************************************* Tests

    @Test
    public void sauvegarderUneDemande() {
        TransactionStatus transaction = beginTransaction();

        Utilisateur utilisateur = creerUtilisateur();

        // Sauvegarder la demande
        DemandeAutorisation demandeAutorisation = demautFactory.initierDemandeAutorisation(utilisateur.getLogin(), ProfessionDeLaSante.Medecin, null);
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
        DemandeAutorisation demandeAutorisation = demautFactory.initierDemandeAutorisation(utilisateur.getLogin(), ProfessionDeLaSante.Medecin, null);
        Annexe annexe = new Annexe(TypeAnnexe.CV, "test.pdf", new byte[1]);
        demandeAutorisation.validerEtAttacherAnnexe(annexe);
        assertThat(demandeAutorisation.getId()).isNull();
        demandeAutorisationRepository.store(demandeAutorisation);
        assertThat(demandeAutorisation.getId()).isNotNull();
        Collection<Annexe> annexes = demandeAutorisation.listerLesAnnexes();
        assertThat(annexes).isNotEmpty();

        // Recuperer la demande
        DemandeAutorisation memeDemande = demandeAutorisationRepository.findBy(demandeAutorisation.getId());
        assertThat(memeDemande).isEqualTo(demandeAutorisation);
        assertThat(memeDemande.listerLesAnnexes()).isNotEmpty();

        commitTransaction(transaction);
    }

    // ********************************************************* Methods privées

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