package ch.vd.demaut.domain.config.mock;

import ch.vd.demaut.domain.annexes.MoteurReglesPourAnnexesObligatoires;
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
    public MoteurReglesPourAnnexesObligatoires getAnnexesObligatoires(Profession profession) {
        return configAnnexesObligatoires.getAnnexesObligatoires(profession);
    }

    @Override
    public void ajouterAnnexesObligatoires(Profession profession, MoteurReglesPourAnnexesObligatoires listeTypeAnnexesObligatoires) {
        configAnnexesObligatoires.ajouterAnnexesObligatoires(profession, listeTypeAnnexesObligatoires);
    }

}
