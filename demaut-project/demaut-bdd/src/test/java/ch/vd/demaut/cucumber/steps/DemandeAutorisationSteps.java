package ch.vd.demaut.cucumber.steps;

import ch.vd.demaut.domain.annexes.AnnexesObligatoires;
import ch.vd.demaut.domain.config.ConfigDemaut;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisationFactory;
import ch.vd.demaut.domain.demandes.autorisation.ProfessionDeLaSante;
import ch.vd.demaut.domain.demandes.autorisation.StatutDemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.domain.utilisateurs.Utilisateur;
import ch.vd.demaut.domain.utilisateurs.UtilisateurRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
public class DemandeAutorisationSteps {

    // ********************************************************* Static fields

    private static final Logger LOGGER = LoggerFactory.getLogger(DemandeAutorisationSteps.class);

    // ********************************************************* Fields
    private UtilisateurRepository utilisateurRepository;
    private DemandeAutorisationRepository demandeAutorisationRepository;
    private ConfigDemaut configDemaut;
    private DemandeAutorisationFactory demandeAutorisationFactory;

    ////////// Données temporaires pour les tests (non-thread safe)
    //FIXME utiliser l'objet ThreadLocal ?
    private Utilisateur utilisateur;
    private DemandeAutorisation demandeEnCours;

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
        demandeEnCours = demandeAutorisationFactory.initierDemandeAutorisation(utilisateur.getLogin(), profession, configDemaut);
        demandeAutorisationRepository.store(demandeEnCours);

        LOGGER.debug("La demande autorisation " + demandeEnCours + " a été ajoutée au repository avec l'id technique:" + demandeEnCours.getId());
    }

    public void verifieDemandeCree(ProfessionDeLaSante profession) {
        demandeAutorisationRepository.findBy(demandeEnCours.getId());
        assertThat(demandeEnCours.getProfessionDeLaSante()).isEqualTo(profession);
    }

    public void verifieAucuneDemandeEnCours(ProfessionDeLaSante profession, StatutDemandeAutorisation statut) {
        demandeAutorisationRepository.recupererDemandeParProfessionStatut(utilisateur.getLogin(), profession, statut);
    }

    public DemandeAutorisation getDemandeEnCours() {
        return demandeEnCours;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    // ***************************** **************************** Technical methods

    public void setUtilisateurRepository(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public void setDemandeAutorisationRepository(DemandeAutorisationRepository demandeAutorisationRepository) {
        this.demandeAutorisationRepository = demandeAutorisationRepository;
    }

    public void setDemandeAutorisationFactory(DemandeAutorisationFactory demandeAutorisationFactory) {
        this.demandeAutorisationFactory = demandeAutorisationFactory;
    }

    public void setConfigDemaut(ConfigDemaut configDemaut) {
        this.configDemaut = configDemaut;
    }

    // ***************************** **************************** Private methods

    public void clean() {
        demandeAutorisationRepository.deleteAll();
        utilisateurRepository.deleteAll();
    }
}
