package ch.vd.demaut.domain.config.mock;

import ch.vd.demaut.domain.annexes.ListeTypeAnnexesObligatoires;
import ch.vd.demaut.domain.demandes.autorisation.Profession;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration des annexes obligatoires par profession
 */
public class ConfigAnnexesObligatoires {

    private Map<Profession, ListeTypeAnnexesObligatoires> config = new HashMap<Profession, ListeTypeAnnexesObligatoires>();

    public ListeTypeAnnexesObligatoires getAnnexesObligatoires(Profession profession) {
        return config.get(profession);
    }

    public void ajouterAnnexesObligatoires(Profession profession, ListeTypeAnnexesObligatoires listeTypeAnnexesObligatoires) {
        config.put(profession, listeTypeAnnexesObligatoires);
    }


}
