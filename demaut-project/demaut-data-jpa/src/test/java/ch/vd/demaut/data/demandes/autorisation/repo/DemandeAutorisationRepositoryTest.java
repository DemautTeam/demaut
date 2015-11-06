package ch.vd.demaut.data.demandes.autorisation.repo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import ch.vd.demaut.domain.demandeur.Email;
import ch.vd.demaut.domain.demandeur.Telephone;
import ch.vd.demaut.domain.demandeur.donneesPerso.Nom;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.*;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionStatus;

import ch.vd.demaut.commons.utils.TransactionManagerWrapper;
import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.AnnexeMetadata;
import ch.vd.demaut.domain.annexes.ContenuAnnexe;
import ch.vd.demaut.domain.annexes.NomFichier;
import ch.vd.demaut.domain.demandes.DateDeCreation;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisationFactory;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;
import ch.vd.demaut.domain.demandeur.Localite;
import ch.vd.demaut.domain.demandeur.Pays;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.domain.demandeur.donneesProf.DonneesProfessionnelles;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.envisagee.ActiviteEnvisagee;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.envisagee.DatePrevueDebut;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.envisagee.Superviseur;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.envisagee.TauxActiviteEnDemiJournee;
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
import ch.vd.demaut.domain.utilisateurs.Utilisateur;
import ch.vd.demaut.domain.utilisateurs.UtilisateurRepository;

@ContextConfiguration({"classpath*:/jpaTest-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class DemandeAutorisationRepositoryTest {

    // ********************************************************* Repos et
    // Services injectés
    // TODO : Virer les @Autowired (la config XML est deja créée mais les injected field sont null) !
    @Autowired
    private DemandeAutorisationRepository demandeAutorisationRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private DemandeAutorisationFactory demandeAutorisationFactory;

    @Autowired
    private TransactionManagerWrapper transactionManagerWrapper;

    // ********************************************************* Transient
    // fields
    private final CodeGLN glnValide = new CodeGLN("4719512002889");
    private final DateDeCreation dateDeCreation = new DateDeCreation(2015, 10, 1);

    // ********************************************************* Setup
    @Before
    public void setUp() throws Exception {
        assertThat(demandeAutorisationRepository).isNotNull();
        assertThat(utilisateurRepository).isNotNull();
        assertThat(demandeAutorisationFactory).isNotNull();
        assertThat(transactionManagerWrapper).isNotNull();
        transactionManagerWrapper.startTransaction();
    }

    // ********************************************************* Tests

    @Test
    public void sauvegarderUneDemande() {

        // Construction de la demande
        Utilisateur utilisateur = creerUtilisateur("admin1@admin");
        DemandeAutorisation demandeInit = demandeAutorisationFactory.initierDemandeAutorisation(utilisateur.getLogin(),
                Profession.Ergotherapeute, glnValide, dateDeCreation);
        assertThat(demandeInit.getId()).isNull();

        persisterDemandeEtVerifier(demandeInit);
    }

    @Test
    // TODO: Nettoyer le code avec des belles methodes lisibles
    public void sauvegarderUneDemandeAvecAnnexes() {
        // Construction de la demande
        Utilisateur utilisateur = creerUtilisateur("admin2@admin");
        DemandeAutorisation demandeInit = demandeAutorisationFactory.initierDemandeAutorisation(utilisateur.getLogin(),
                Profession.Chiropraticien, glnValide, dateDeCreation);
        byte[] contenu = "AnnexeContenu".getBytes();
        Annexe annexe = new Annexe(new NomFichier("test.pdf"), new ContenuAnnexe(contenu));
        demandeInit.validerEtAttacherAnnexe(annexe);

        persisterDemandeEtVerifier(demandeInit);
    }

    @Test
    public void sauvegarderUneDemandeAvecActivitesFutures() {
        // Construction de la demande
        Utilisateur utilisateur = creerUtilisateur("admin4@admin");
        DemandeAutorisation demandeInit = demandeAutorisationFactory.initierDemandeAutorisation(utilisateur.getLogin(),
                Profession.Chiropraticien, glnValide, dateDeCreation);

        ActiviteFuture activiteFuture = buildActiviteFutureValide();

        demandeInit.validerEtAjouterActiviteFuture(activiteFuture);

        persisterDemandeEtVerifier(demandeInit);
    }

    @Test
    public void sauvegarderUneDemandeAvecDonneeProfessionnellesEtDiplomes() {
        Utilisateur utilisateur = creerUtilisateur("admin3@admin");

        // Sauvegarder la demande
        DemandeAutorisation demandeAutorisation = demandeAutorisationFactory
                .initierDemandeAutorisation(utilisateur.getLogin(), Profession.Medecin, glnValide, dateDeCreation);

        DonneesProfessionnelles donneesProfessionnelles = demandeAutorisation.getDonneesProfessionnelles();
        creerListeDiplomes(donneesProfessionnelles);
        demandeAutorisation.validerDonneesProfessionnelles();

        assertThat(demandeAutorisation.getId()).isNull();
        demandeAutorisationRepository.store(demandeAutorisation);
        assertThat(demandeAutorisation.getId()).isNotNull();
        Collection<Diplome> diplomes = demandeAutorisation.getDonneesProfessionnelles().getListeDesDiplomes()
                .listerDiplomes();
        assertThat(diplomes).isNotEmpty();

        // TODO : Appeler persisterDemandeEtVerifier
    }

    // ********************************************************* Methods privées

    private ActiviteFuture buildActiviteFutureValide() {
        Etablissement etablissement = new Etablissement(new Nom("Centre medical"), new Voie("2"), null, new Localite("Lausanne"), new NPAProfessionnel("1234"),
                new Telephone("0123456"), new Telephone("0123456"), new Telephone("0123456"), new Email("toto@titi.com"), new SiteInternet("www.google.com"));
        ActiviteEnvisagee activiteEnvisagee = new ActiviteEnvisagee(TypeActivite.Dependant,
                new TauxActiviteEnDemiJournee(1), new DatePrevueDebut(new LocalDate(2015, 10, 1)),
                new Superviseur("superviseur"));
        ActiviteFuture activiteFuture = new ActiviteFuture(etablissement, TypePratiqueLamal.Non, activiteEnvisagee);
        return activiteFuture;
    }

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

    private Utilisateur creerUtilisateur(String loginStr) {
        Login login = new Login(loginStr);
        // Créer et sauvegarder un Utilisateur
        Utilisateur utilisateur = new Utilisateur(login);
        utilisateurRepository.store(utilisateur);
        assertThat(utilisateur.getId()).isNotNull();
        utilisateur = utilisateurRepository.findBy(utilisateur.getId());
        return utilisateur;
    }

    private DemandeAutorisation recupererDemandePersistee(ReferenceDeDemande referenceDeDemande) {
        return demandeAutorisationRepository.recupererDemandeParReference(referenceDeDemande);
    }

    private void verifierMemeDemande(DemandeAutorisation demandePersistee, DemandeAutorisation demandeInit) {
        // Verifier Ref
        assertThat(demandePersistee).isEqualTo(demandeInit);

        assertThat(demandePersistee.getLogin()).isEqualTo(demandeInit.getLogin());
        assertThat(demandePersistee.getProfession()).isEqualTo(demandeInit.getProfession());
        assertThat(demandePersistee.getDateDeCreation()).isEqualTo(demandeInit.getDateDeCreation());

        verifierActivitesFutures(demandePersistee, demandeInit);

        verifierAnnexes(demandePersistee, demandeInit);
    }

    private void verifierActivitesFutures(DemandeAutorisation demandePersistee, DemandeAutorisation demandeInit) {
        List<ActiviteFuture> activitesFuturesInit = demandeInit.getActivitesFutures().listerActivitesFutures();
        List<ActiviteFuture> activitesFuturesPersist = demandePersistee.getActivitesFutures().listerActivitesFutures();

        assertThat(activitesFuturesPersist).hasSameSizeAs(activitesFuturesInit);
        assertThat(activitesFuturesPersist).containsAll(activitesFuturesInit);
    }

    private void verifierAnnexes(DemandeAutorisation demandePersistee, DemandeAutorisation demandeInit) {
        // Tester les annexes metadata
        Collection<AnnexeMetadata> annexeMetadatasInit = demandeInit.listerLesAnnexeMetadatas();
        Collection<AnnexeMetadata> annexeMetadatasPersit = demandePersistee.listerLesAnnexeMetadatas();
        assertThat(annexeMetadatasPersit).hasSameSizeAs(annexeMetadatasInit);
        assertThat(annexeMetadatasPersit).containsAll(annexeMetadatasInit);
    }

    private void persisterDemandeEtVerifier(DemandeAutorisation demandeInit) {
        // Sauvegarder la demande
        demandeAutorisationRepository.store(demandeInit);

        transactionManagerWrapper.commitTransaction();
        assertThat(transactionManagerWrapper.getTransaction()).isInstanceOf(TransactionStatus.class);
        TransactionStatus transaction = (TransactionStatus) transactionManagerWrapper.getTransaction();
        assertThat(transaction.isCompleted()).isTrue();

        transactionManagerWrapper.startTransaction();
        transaction = (TransactionStatus) transactionManagerWrapper.getTransaction();
        assertThat(transaction.isNewTransaction()).isTrue();

        // Teste que la demande a été persistée correctement
        DemandeAutorisation memeDemande = recupererDemandePersistee(demandeInit.getReferenceDeDemande());
        verifierMemeDemande(memeDemande, demandeInit);

        transactionManagerWrapper.commitTransaction();
    }

    // ********************************************************* Setters for injection

    public void setDemandeAutorisationFactory(DemandeAutorisationFactory demandeAutorisationFactory) {
        this.demandeAutorisationFactory = demandeAutorisationFactory;
    }

    public void setDemandeAutorisationRepository(DemandeAutorisationRepository demandeAutorisationRepository) {
        this.demandeAutorisationRepository = demandeAutorisationRepository;
    }

    public void setTransactionManagerWrapper(TransactionManagerWrapper transactionManagerWrapper) {
        this.transactionManagerWrapper = transactionManagerWrapper;
    }

    public void setUtilisateurRepository(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

}