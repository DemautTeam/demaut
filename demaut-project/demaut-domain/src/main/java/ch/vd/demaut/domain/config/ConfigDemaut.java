package ch.vd.demaut.domain.config;

import ch.vd.demaut.domain.annexes.MoteurReglesPourAnnexesObligatoires;
import ch.vd.demaut.domain.demandes.autorisation.Profession;

/**
 * Interface de Configuration de Demaut.
 */
public interface ConfigDemaut {

    /**
     * Retourne la liste des types d'annexe obligatoires pour la complétud de la demande du type {@link Profession}
     *
     * @param profession Profession de la demande
     * @return Listes des annexes obligatoires pour la demande
     */
    MoteurReglesPourAnnexesObligatoires getAnnexesObligatoires(Profession profession);

    /**
     * Construit l'initialisation de la configuration des annexes obligatoires
     *
     * @param profession
     * @param listeTypeAnnexesObligatoires
     */
    void ajouterAnnexesObligatoires(Profession profession, MoteurReglesPourAnnexesObligatoires listeTypeAnnexesObligatoires);
}
