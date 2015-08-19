package ch.vd.demaut.cucumber.steps.definitions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.java.fr.Alors;
import cucumber.api.java.fr.Etantdonné;
import cucumber.api.java.fr.Lorsque;

/**
 * Step definitions pour la fonctionnalité "Soumettre demande"
 *
 */
public class SoumettreDemandeStepDefinitions extends StepDefinitions {

	// ********************************************************* Fields

	private static final Logger LOGGER = LoggerFactory.getLogger(SoumettreDemandeStepDefinitions.class);

	// ********************************************************* Given
	@Etantdonné("^que le nom et prénom \"([^\"]*)\" sont renseignés dans les données personnelles de la demande$")
	public void que_le_nom_et_prénom_sont_renseignés_dans_les_données_personnelles_de_la_demande(String arg1) {
		LOGGER.debug("@Etantdonné que_le_nom_et_prénom_sont_renseignés_dans_les_données_personnelles_de_la_demande");
	}

	// ********************************************************* When

	@Lorsque("^que le demandeur professionnel soumet sa demande au système Demaut$")
	public void que_le_demandeur_professionnel_soumet_sa_demande_au_système_Demaut() {
	}
	// ********************************************************* Then

	@Alors("^le système Demaut accepte la soumission de la demande$")
	public void le_système_Demaut_accepte_la_soumission_de_la_demande() {
	}

	@Alors("^la demande passe dans l'état \"([^\"]*)\"$")
	public void la_demande_passe_dans_l_état(String arg1) {
	}

	@Alors("^la date de soumission de la demande est \"([^\"]*)\"$")
	public void la_date_de_soumission_de_la_demande_est(String arg1) {
	}

	@Etantdonné("^que le nom et prénom n'ont pas été renseignés dans les données personnelles de la demande$")
	public void que_le_nom_et_prénom_n_ont_pas_été_renseignés_dans_les_données_personnelles_de_la_demande()
			{
	}

	@Alors("^le système Demaut refuse la soumission de la demande$")
	public void le_système_Demaut_refuse_la_soumission_de_la_demande() {
	}

	@Alors("^la demande reste dans l'état \"([^\"]*)\"$")
	public void la_demande_reste_dans_l_état(String arg1) {
	}

	@Alors("^il n'y a pas de date de soumission de la demande$")
	public void il_n_y_a_pas_de_date_de_soumission_de_la_demande() {
	}

}
