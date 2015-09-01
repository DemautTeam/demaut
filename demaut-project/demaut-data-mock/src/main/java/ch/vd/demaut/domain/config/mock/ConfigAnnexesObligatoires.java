package ch.vd.demaut.domain.config.mock;

import java.util.HashMap;
import java.util.Map;

import ch.vd.demaut.domain.annexes.AnnexesObligatoires;
import ch.vd.demaut.domain.demandes.autorisation.ProfessionDeLaSante;

/**
 * Configuration des annexes obligatoires par profession
 *
 */
public class ConfigAnnexesObligatoires {

	private Map<ProfessionDeLaSante, AnnexesObligatoires> config = new HashMap<ProfessionDeLaSante, AnnexesObligatoires>();

	public AnnexesObligatoires getAnnexesObligatoires(ProfessionDeLaSante profession) {
		return config.get(profession);
	}
	
	public void ajouterAnnexesObligatoires(ProfessionDeLaSante profession, AnnexesObligatoires annexesObligatoires) {
		config.put(profession, annexesObligatoires);	
	}

	
}
