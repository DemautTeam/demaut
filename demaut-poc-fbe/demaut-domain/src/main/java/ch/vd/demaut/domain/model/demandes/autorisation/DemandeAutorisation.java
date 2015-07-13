package ch.vd.demaut.domain.model.demandes.autorisation;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import ch.vd.demaut.commons.annotations.Aggregate;
import ch.vd.demaut.domain.model.annexes.Annexe;
import ch.vd.demaut.domain.model.demandes.Demande;
import ch.vd.demaut.domain.model.demandes.DemandeFK;
import ch.vd.demaut.domain.model.demandeurs.Demandeur;

@Aggregate
public class DemandeAutorisation extends Demande {

	// ********************************************************* Fields	
	@NotNull
	private TypeAutorisation typeAutorisation;
	
	@NotNull
	private Demandeur demandeur;
	
	@NotNull
	private StatutDemandeAutorisation statut;

	private DateSoumissionDemande dateDeSoumission;
	
	private ListeDesAnnexes annexes; 
	

	// ********************************************************* Constructor
	public DemandeAutorisation(Demandeur demandeur, TypeAutorisation typeAutorisation) {
		super();
		annexes = new ListeDesAnnexes();
		statut = StatutDemandeAutorisation.Brouillon;
	}

	// ********************************************************* Business Methods
	
	public void attacherUneAnnexe(Annexe annexeALier) {
		annexes.ajouterAnnexe(annexeALier);
	}
	
	// ********************************************************* Private Methods

	// ********************************************************* Getters
	public Collection<Annexe> listerLesAnnexes(){
		return annexes.listerAnnexes();
	}

	public TypeAutorisation getTypeAutorisation() {
		return typeAutorisation;
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
