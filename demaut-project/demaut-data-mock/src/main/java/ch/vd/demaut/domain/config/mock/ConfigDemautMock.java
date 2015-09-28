package ch.vd.demaut.domain.config.mock;

import ch.vd.demaut.domain.annexes.AnnexesObligatoires;
import ch.vd.demaut.domain.config.ConfigDemaut;
import ch.vd.demaut.domain.demandes.autorisation.Profession;

/**
 * Implémentation Mock de ConfigDemaut
 */
public class ConfigDemautMock implements ConfigDemaut {

    private ConfigAnnexesObligatoires configAnnexesObligatoires;

    ConfigDemautMock() {
        configAnnexesObligatoires = new ConfigAnnexesObligatoires();
    }

    @Override
    public AnnexesObligatoires getAnnexesObligatoires(Profession profession) {
        return configAnnexesObligatoires.getAnnexesObligatoires(profession);
    }

    @Override
    public void ajouterAnnexesObligatoires(Profession profession, AnnexesObligatoires annexesObligatoires) {
        configAnnexesObligatoires.ajouterAnnexesObligatoires(profession, annexesObligatoires);
    }

}
