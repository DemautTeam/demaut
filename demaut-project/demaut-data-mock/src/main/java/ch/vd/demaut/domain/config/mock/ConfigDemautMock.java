package ch.vd.demaut.domain.config.mock;

import ch.vd.demaut.domain.annexes.AnnexesObligatoires;
import ch.vd.demaut.domain.config.ConfigDemaut;
import ch.vd.demaut.domain.demandes.autorisation.ProfessionDeLaSante;

/**
 * Implémentation Mock de ConfigDemaut
 *
 */
public class ConfigDemautMock implements ConfigDemaut {
	
	private ConfigAnnexesObligatoires configAnnexesObligatoires;
	
	ConfigDemautMock() {
		configAnnexesObligatoires = new ConfigAnnexesObligatoires();
	}
	
	@Override
	public AnnexesObligatoires getAnnexesObligatoires(ProfessionDeLaSante profession) {
		return configAnnexesObligatoires.getAnnexesObligatoires(profession);
	}
	
	@Override
	public void ajouterAnnexesObligatoires(ProfessionDeLaSante profession, AnnexesObligatoires annexesObligatoires) {
		configAnnexesObligatoires.ajouterAnnexesObligatoires(profession, annexesObligatoires);
	}

}
