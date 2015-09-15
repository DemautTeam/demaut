package ch.vd.demaut.cucumber.steps;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import ch.vd.demaut.cucumber.converters.commons.AccepteOuRefuse;
import ch.vd.demaut.cucumber.steps.definitions.AttacherAnnexeStepDefinitions;
import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.AnnexeNonValideException;
import ch.vd.demaut.domain.annexes.AnnexesObligatoires;
import ch.vd.demaut.domain.annexes.ListeDesAnnexes;
import ch.vd.demaut.domain.config.ConfigDemaut;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisationFactory;
import ch.vd.demaut.domain.demandes.autorisation.ProfessionDeLaSante;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.domain.utilisateurs.Utilisateur;
import ch.vd.demaut.domain.utilisateurs.UtilisateurRepository;

@Transactional
public class DemandeAutorisationSteps {

    // ********************************************************* Static fields

    private static DemandeAutorisationSteps INSTANCE = null;
    private static final Logger LOGGER = LoggerFactory.getLogger(AttacherAnnexeStepDefinitions.class);

    // ********************************************************* Fields

    //Beans Initialisés pas Spring Context
    private UtilisateurRepository utilisateurRepository;
    private DemandeAutorisationRepository demandeAutorisationRepository;
    private ConfigDemaut configDemaut;
    private DemandeAutorisationFactory demautFactoy = DemandeAutorisationFactory.getInstance();
    
    ////////// Données temporaires pour les tests (non-thread safe)
    
    private Utilisateur utilisateur;
    private DemandeAutorisation demandeEnCours;
    private AccepteOuRefuse actualAcceptationAnnexe;


    // ********************************************************* Methods

    public void ajouterAnnexesObligatoires(ProfessionDeLaSante profession, AnnexesObligatoires annexesObligatoires) {
        configDemaut.ajouterAnnexesObligatoires(profession, annexesObligatoires);
    }

    public void initialiserUtilisateur(Login login) {
        utilisateur = new Utilisateur(login);
        utilisateurRepository.store(utilisateur);
        LOGGER.debug("L'utilisateur " + login + " a été ajouté au repository avec l'id technique:" + utilisateur.getId());
    }

    public void initialiserDemandeEnCours(ProfessionDeLaSante profession) {
        demandeEnCours = demautFactoy.inititierDemandeAutorisation(utilisateur.getLogin(), profession, configDemaut);
        demandeAutorisationRepository.store(demandeEnCours);

        LOGGER.debug("La demande autorisation " + demandeEnCours + " a été ajoutée au repository avec l'id technique:" + demandeEnCours.getId());
    }

    public void ajouterAnnexesADemandeEnCours(ListeDesAnnexes listeDesAnnexesInitiales) {
        Collection<Annexe> annexesInit = listeDesAnnexesInitiales.listerAnnexes();
        for (Annexe annexe : annexesInit) {
            demandeEnCours.validerEtAttacherAnnexe(annexe);
        }
        assertThat(demandeEnCours.listerLesAnnexes()).hasSameSizeAs(annexesInit);
    }

    public void verifieDemandeCree(ProfessionDeLaSante profession) {
        demandeAutorisationRepository.findBy(demandeEnCours.getId());
        assertThat(demandeEnCours.getProfessionDeLaSante()).isEqualTo(profession);
    }

    public DemandeAutorisation getDemandeEnCours() {
        return demandeEnCours;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void attacherUneAnnexe(Annexe annexe) {
        try {
            getDemandeEnCours().validerEtAttacherAnnexe(annexe);
            accepteAnnexe();
        } catch (AnnexeNonValideException e) {
            refuseAnnexe();
        }
    }

    public void accepteAnnexe() {
        actualAcceptationAnnexe = AccepteOuRefuse.accepte;
    }

    public void refuseAnnexe() {
        actualAcceptationAnnexe = AccepteOuRefuse.refuse;
    }

    public void verifieAcceptationAnnexe(AccepteOuRefuse expectedAcceptationAnnexe) {
        assertThat(actualAcceptationAnnexe).isEqualTo(expectedAcceptationAnnexe);
    }
    // ********************************************************* Instanciation

    /**
     * Récupère l'instance de la classe en cours. A utiliser dans les
     * "BeforeScenario".
     *
     * @return
     */
    public static final DemandeAutorisationSteps getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DemandeAutorisationSteps();
        }
        return INSTANCE;
    }

    /**
     * Nettoye l'instance de la classe. A utiliser dans les "AfterScenario".
     */
    public static final void disposeInstance() {
        if (INSTANCE != null) {
            INSTANCE.clean();
            INSTANCE = null;
        }
    }

    // ***************************** **************************** Technical
    // methods

    public void setUtilisateurRepository(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public void setDemandeAutorisationRepository(DemandeAutorisationRepository demandeAutorisationRepository) {
        this.demandeAutorisationRepository = demandeAutorisationRepository;
    }

    public void setConfigDemaut(ConfigDemaut configDemaut) {
        this.configDemaut = configDemaut;
    }

    public void clean() {
        demandeAutorisationRepository.deleteAll();
        utilisateurRepository.deleteAll();
    }

    // ***************************** **************************** Private
    // methods

    //public for Spring
    public DemandeAutorisationSteps() {
    }


}
