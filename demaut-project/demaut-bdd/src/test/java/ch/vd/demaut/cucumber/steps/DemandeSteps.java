package ch.vd.demaut.cucumber.steps;

import static org.assertj.core.api.Assertions.assertThat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.vd.demaut.cucumber.steps.definitions.AttacherAnnexeStepDefinitions;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.ProfessionDeLaSante;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;
import ch.vd.demaut.domain.demandeurs.Demandeur;
import ch.vd.demaut.domain.demandeurs.DemandeurRepository;
import ch.vd.demaut.domain.demandeurs.NomEtPrenomDemandeur;


public class DemandeSteps {

	// ********************************************************* Static fields

	private static DemandeSteps INSTANCE = null;
	private static final Logger LOGGER = LoggerFactory.getLogger(AttacherAnnexeStepDefinitions.class);

	// ********************************************************* Fields
	private DemandeurRepository demandeurRepository;
	private DemandeAutorisationRepository demandeAutorisationRepository;

	private Demandeur demandeur;
	private DemandeAutorisation demande;

	// ********************************************************* Methods
	public void initialiserDemandeur(NomEtPrenomDemandeur nomPrenomDemandeur) {
		demandeur = new Demandeur(nomPrenomDemandeur);
		demandeurRepository.store(demandeur);
		LOGGER.debug("Le demandeur " + nomPrenomDemandeur + " a été ajouté au repository avec l'id technique:"
				+ demandeur.getId());
	}

	public void initialiserDemande(ProfessionDeLaSante profession) {
		demande = new DemandeAutorisation(demandeur, profession);
		demandeAutorisationRepository.store(demande);
		LOGGER.debug("La demande " + demande + " a été ajoutée au repository avec l'id technique:" + demande.getId());
	}

	public void verifieDemandeCree(ProfessionDeLaSante profession) {
		demandeAutorisationRepository.findBy(demande.getId());
		assertThat(demande.getProfession()).isEqualTo(profession);
	}
	
	public DemandeAutorisation getDemande() {
		return demande;
	}
	
	public Demandeur getDemandeur() {
		return demandeur;
	}

	// ********************************************************* Instanciation

	/**
	 * Récupère l'instance de la classe en cours. A utiliser dans les
	 * "BeforeScenario".
	 * 
	 * @return
	 */
	public static final DemandeSteps getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DemandeSteps();
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

	public void setDemandeurRepository(DemandeurRepository demandeurRepository) {
		this.demandeurRepository = demandeurRepository;
	}

	public void setDemandeAutorisationRepository(DemandeAutorisationRepository demandeAutorisationRepository) {
		this.demandeAutorisationRepository = demandeAutorisationRepository;
	}


	private void clean() {
		
	}
	
	// ***************************** **************************** Private
	// methods

	private DemandeSteps() {
	}

}
