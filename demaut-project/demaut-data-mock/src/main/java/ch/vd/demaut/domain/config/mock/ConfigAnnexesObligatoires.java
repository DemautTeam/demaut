package ch.vd.demaut.domain.config.mock;

import ch.vd.demaut.domain.annexes.AnnexesObligatoires;
import ch.vd.demaut.domain.demandes.autorisation.Profession;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration des annexes obligatoires par profession
 */
public class ConfigAnnexesObligatoires {

    private Map<Profession, AnnexesObligatoires> config = new HashMap<Profession, AnnexesObligatoires>();

    public AnnexesObligatoires getAnnexesObligatoires(Profession profession) {
        return config.get(profession);
    }

    public void ajouterAnnexesObligatoires(Profession profession, AnnexesObligatoires annexesObligatoires) {
        config.put(profession, annexesObligatoires);
    }


}
