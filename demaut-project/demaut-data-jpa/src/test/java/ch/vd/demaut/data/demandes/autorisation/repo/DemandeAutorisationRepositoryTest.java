package ch.vd.demaut.data.demandes.autorisation.repo;

import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.AnnexeMetadata;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisationFactory;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;
import ch.vd.demaut.domain.demandeur.Pays;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration({"classpath*:/jpaTest-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class DemandeAutorisationRepositoryTest {

    private final CodeGLN glnValide = new CodeGLN("4719512002889");
    // ********************************************************* Repos et
    // Services injectés
    @Autowired
    private DemandeAutorisationRepository demandeAutorisationRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private DemandeAutorisationFactory demandeAutorisationFactory;
    @Autowired
    private PlatformTransactionManager transactionManagerDemaut;
    // ********************************************************* Transient
    // fields
    private TransactionStatus transaction;

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
        transaction = beginTransaction();

        // Construction de la demande
        Utilisateur utilisateur = creerUtilisateur("admin1@admin");
        DemandeAutorisation demandeInit = demandeAutorisationFactory.initierDemandeAutorisation(utilisateur.getLogin(),
                Profession.Ergotherapeute, glnValide);
        assertThat(demandeInit.getId()).isNull();

        persisterDemandeEtVerifier(demandeInit);
    }

    @Test
    public void countAllDemande() {
        long result = demandeAutorisationRepository.countAll();
        assertThat(result).isGreaterThanOrEqualTo(0);
    }

    @Test
    //TODO: Nettoyer le code avec des belles methodes lisibles
    public void sauvegarderUneDemandeAvecAnnexes() {
        transaction = beginTransaction();

        // Construction de la demande
        Utilisateur utilisateur = creerUtilisateur("admin2@admin");
        DemandeAutorisation demandeInit = demandeAutorisationFactory.initierDemandeAutorisation(utilisateur.getLogin(),
                Profession.Chiropraticien, glnValide);
        byte[] contenu = "AnnexeContenu".getBytes();
        Annexe annexe = new Annexe(TypeAnnexe.CV, "test.pdf", contenu, "01.01.2015 11:00");
        demandeInit.validerEtAttacherAnnexe(annexe);

        persisterDemandeEtVerifier(demandeInit);
    }

    @Test
    public void sauvegarderUneDemandeAvecDonneeProfessionnellesEtDiplomes() {
        transaction = beginTransaction();

        Utilisateur utilisateur = creerUtilisateur("admin3@admin");

        // Sauvegarder la demande
        DemandeAutorisation demandeAutorisation = demandeAutorisationFactory
                .initierDemandeAutorisation(utilisateur.getLogin(), Profession.Medecin, glnValide);

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
        donneesProfessionnelles.validerEtAjouterDiplome(
                new Diplome(new ReferenceDeDiplome(UUID.randomUUID().toString()), TypeDiplomeAccepte.D_FORMATION_APPROFONDIE,
                        new TitreFormation(TitreFormationApprofondieProgres.PneumologiePediatrique.name()), null,
                        new DateObtention(new LocalDate()), new PaysObtention(Pays.Suisse.name()), null));
        donneesProfessionnelles.validerEtAjouterDiplome(
                new Diplome(new ReferenceDeDiplome(UUID.randomUUID().toString()), TypeDiplomeAccepte.D_FORMATION_INITIALE,
                        new TitreFormation(TitreFormationInitialeProgres.CFRDUnDiplomeEtrangerDeMedecin.name()), null,
                        new DateObtention(new LocalDate()), new PaysObtention(Pays.Allemagne.name()), new DateReconnaissance(new LocalDate())));
        donneesProfessionnelles.validerEtAjouterDiplome(
                new Diplome(new ReferenceDeDiplome(UUID.randomUUID().toString()), TypeDiplomeAccepte.D_POSTGRADE,
                        new TitreFormation(TitreFormationPostgradeProgres.Cardiologie.name()), null,
                        new DateObtention(new LocalDate()), new PaysObtention(Pays.Suisse.name()), null));
    }

    private Utilisateur creerUtilisateur(String loginStr) {
        Login login = new Login(loginStr);
        // Créer et sauvegarder un Utilisateur
        Utilisateur utilisateur = new Utilisateur(login);
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

    private void rollbackTransaction(TransactionStatus transaction) {
        transactionManagerDemaut.rollback(transaction);
    }


    private DemandeAutorisation recupererDemandePersistee(DemandeAutorisation demandeAutorisation) {
        commitTransaction(transaction);
        transaction = beginTransaction();
        return demandeAutorisationRepository
                .recupererDemandeParReference(demandeAutorisation.getReferenceDeDemande());
    }

    private void verifieMemeDemande(DemandeAutorisation demandePersistee, DemandeAutorisation demandeInit) {
        // Meme Ref
        assertThat(demandePersistee).isEqualTo(demandeInit);
        assertThat(demandePersistee.getLogin()).isEqualTo(demandeInit.getLogin());
        assertThat(demandePersistee.getProfession()).isEqualTo(demandeInit.getProfession());

        // Tester les annexes metadata
        Collection<AnnexeMetadata> annexeMetadatasInit = demandeInit.listerLesAnnexeMetadatas();
        Collection<AnnexeMetadata> annexeMetadatasPersit = demandePersistee.listerLesAnnexeMetadatas();

        assertThat(annexeMetadatasPersit).hasSameSizeAs(annexeMetadatasInit);
        //assertThat(annexeMetadatasPersit).containsAll(annexeMetadatasInit);

        // Teste le contenu annexe
        List<Annexe> annexesPerst = demandePersistee.listerLesAnnexes();
        List<Annexe> annexesInit = demandeInit.listerLesAnnexes();
        if (annexesPerst.size() > 0) {
            Annexe premiereAnnexePerst = annexesPerst.iterator().next();
            Annexe premiereAnnexeInit = annexesInit.iterator().next();
            assertThat(premiereAnnexePerst.getContenu().getContenu())
                    .isEqualTo(premiereAnnexeInit.getContenu().getContenu());
        }
    }

    private void persisterDemandeEtVerifier(DemandeAutorisation demandeInit) {
        // Sauvegarder la demande
        demandeAutorisationRepository.store(demandeInit);

        // Teste que la demande a été persistée correctement
        DemandeAutorisation memeDemande = recupererDemandePersistee(demandeInit);
        verifieMemeDemande(memeDemande, demandeInit);

        commitTransaction(transaction);
    }


}