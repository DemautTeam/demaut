package ch.vd.demaut.domain.config;

import ch.vd.demaut.domain.annexes.AnnexesObligatoires;
import ch.vd.demaut.domain.demandes.autorisation.ProfessionDeLaSante;

/**
 * Interface de Configuration de Demaut.
 *
 */
public interface ConfigDemaut {
	
	/**
	 * Retourne la liste des types d'annexe obligatoires pour la complétud de la demande du type {@link ProfessionDeLaSante}
	 * @param profession Profession de la demande
	 * @return Listes des annexes obligatoires pour la demande
	 */
	AnnexesObligatoires getAnnexesObligatoires(ProfessionDeLaSante profession);
	
	/**
	 * Construit l'initialisation de la configuration des annexes obligatoires 
	 * @param profession
	 * @param annexesObligatoires
	 */
	void ajouterAnnexesObligatoires(ProfessionDeLaSante profession, AnnexesObligatoires annexesObligatoires);
}
