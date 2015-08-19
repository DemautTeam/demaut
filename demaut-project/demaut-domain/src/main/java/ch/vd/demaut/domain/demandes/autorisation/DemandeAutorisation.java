package ch.vd.demaut.domain.demandes.autorisation;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import ch.vd.demaut.commons.annotations.Aggregate;
import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.demandes.Demande;
import ch.vd.demaut.domain.demandes.DemandeFK;
import ch.vd.demaut.domain.demandeurs.Demandeur;

@Aggregate
public class DemandeAutorisation extends Demande {

	// ********************************************************* Fields	
	@NotNull
	final private ProfessionDeLaSante profession;
	
	final private Demandeur demandeur;
	
	@NotNull
	private StatutDemandeAutorisation statut;

	private DateSoumissionDemande dateDeSoumission;
	
	private ListeDesAnnexes annexes; 

	// ********************************************************* Constructor
	public DemandeAutorisation(Demandeur demandeur, ProfessionDeLaSante profession) {
		super();
		annexes = new ListeDesAnnexes();
		statut = StatutDemandeAutorisation.Brouillon;
		this.demandeur = demandeur;
		this.profession = profession;
	}

	//TODO : Remove me. Used only for testing currently
	public DemandeAutorisation() {
		super();
		annexes = new ListeDesAnnexes();
		statut = StatutDemandeAutorisation.Brouillon;
		this.demandeur = null;
		this.profession = ProfessionDeLaSante.Medecin;
		
	}
	
	// ********************************************************* Business Methods
	

	/**
	 * Attache une annexe à la demande.
	 * @param annexeALier
	 */
	public void attacherUneAnnexe(Annexe annexeALier) {
		annexeALier.validerContenu();
		annexes.ajouterAnnexe(annexeALier);
	}
	
	// ********************************************************* Private Methods

	// ********************************************************* Getters
	public Collection<Annexe> listerLesAnnexes(){
		return annexes.listerAnnexes();
	}

	public ProfessionDeLaSante getProfession() {
		return profession;
	}
	
	public DateSoumissionDemande getDateDeSoumission() {
		return dateDeSoumission;
	}
	
	public Demandeur getDemandeur() {
		return demandeur;
	}

	// ********************************************************* Technical methods
	@Override
	public DemandeFK getFunctionalKey() {
		return new DemandeFK(this);
	}
	
	
}
