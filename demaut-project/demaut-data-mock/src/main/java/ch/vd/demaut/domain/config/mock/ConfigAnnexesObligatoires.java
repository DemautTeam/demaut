package ch.vd.demaut.domain.config.mock;

import ch.vd.demaut.domain.annexes.MoteurReglesPourAnnexesObligatoires;
import ch.vd.demaut.domain.demandes.autorisation.Profession;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration des annexes obligatoires par profession
 */
public class ConfigAnnexesObligatoires {

    private Map<Profession, MoteurReglesPourAnnexesObligatoires> config = new HashMap<Profession, MoteurReglesPourAnnexesObligatoires>();

    public MoteurReglesPourAnnexesObligatoires getAnnexesObligatoires(Profession profession) {
        return config.get(profession);
    }

    public void ajouterAnnexesObligatoires(Profession profession, MoteurReglesPourAnnexesObligatoires listeTypeAnnexesObligatoires) {
        config.put(profession, listeTypeAnnexesObligatoires);
    }


}
