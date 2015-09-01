package ch.vd.demaut.domain.demandes.autorisation;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import ch.vd.demaut.commons.annotations.Aggregate;
import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.AnnexeValidateur;
import ch.vd.demaut.domain.annexes.AnnexesObligatoires;
import ch.vd.demaut.domain.annexes.ListeDesAnnexes;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.config.ConfigDemaut;
import ch.vd.demaut.domain.demandes.Demande;
import ch.vd.demaut.domain.demandes.DemandeFK;
import ch.vd.demaut.domain.demandeurs.Demandeur;

@Aggregate
public class DemandeAutorisation extends Demande {

	// ********************************************************* Fields
	@NotNull
	// TODO: Make it final (be careful with JPA)
	private ProfessionDeLaSante professionDeLaSante;

	// TODO: Make it final (be careful with JPA)
	private Demandeur demandeur;

	private ConfigDemaut config;

	@NotNull
	private StatutDemandeAutorisation statutDemandeAutorisation;

	private DateSoumissionDemande dateSoumissionDemande;

	private ListeDesAnnexes annexes = new ListeDesAnnexes();

	// ********************************************************* Constructor
	//TODO: Remove me...but needs to refactor test and tests jpa
	public DemandeAutorisation() {
		this(null, ProfessionDeLaSante.Medecin, null);
	}
	
	public DemandeAutorisation(Demandeur demandeur, ProfessionDeLaSante profession, ConfigDemaut config) {
		super();
		annexes = new ListeDesAnnexes();
		statutDemandeAutorisation = StatutDemandeAutorisation.Brouillon;
		this.demandeur = demandeur;
		this.professionDeLaSante = profession;
		this.config = config;
	}


	// ********************************************************* Business
	// Methods

	/**
	 * Attache une annexe à la demande.
	 *
	 * @param annexeALier
	 */
	public void attacherUneAnnexe(Annexe annexeALier) {
		AnnexeValidateur.getInstance().valider(annexeALier);
		annexes.ajouterAnnexe(annexeALier);
	}

	/**
	 * Retourne la liste des type d'annexe qui sont obligatoires pour la
	 * complétude de cette demande
	 */
	public AnnexesObligatoires getAnnexesObligatoires() {
		return config.getAnnexesObligatoires(professionDeLaSante);
	}

	/**
	 * Détermine si tous les annexes obligatoires sont remplis 
	 * @return
	 */
	public boolean annexesObligatoiresCompletes() {
		AnnexesObligatoires annexesObligatoires = getAnnexesObligatoires();
		for (TypeAnnexe typeAnnexe : annexesObligatoires.listerTypesAnnexe()) {
			Collection<Annexe> annexesParType = annexes.extraireAnnexesDeType(typeAnnexe);
			if (annexesParType.isEmpty()) {
				return false;
			}
		}
		return true;
	}
	
	// ********************************************************* Private Methods

	// ********************************************************* Getters
	public Collection<Annexe> listerLesAnnexes() {
		return annexes.listerAnnexes();
	}

	public ProfessionDeLaSante getProfessionDeLaSante() {
		return professionDeLaSante;
	}

	public DateSoumissionDemande getDateSoumissionDemande() {
		return dateSoumissionDemande;
	}

	public Demandeur getDemandeur() {
		return demandeur;
	}

	// ********************************************************* Technical
	// methods
	@Override
	public DemandeFK getFunctionalKey() {
		return new DemandeFK(this);
	}

	public StatutDemandeAutorisation getStatutDemandeAutorisation() {
		return statutDemandeAutorisation;
	}

	public ListeDesAnnexes getAnnexes() {
		return annexes;
	}
}
