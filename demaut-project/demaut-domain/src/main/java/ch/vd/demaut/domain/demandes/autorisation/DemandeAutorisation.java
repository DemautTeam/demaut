package ch.vd.demaut.domain.demandes.autorisation;

import java.util.Collection;

import ch.vd.demaut.commons.annotations.Aggregate;
import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.demandes.Demande;
import ch.vd.demaut.domain.demandes.DemandeFK;

@Aggregate
public class DemandeAutorisation extends Demande {

	// ********************************************************* Fields	
	private ListeDesAnnexes annexes; 

	// ********************************************************* Constructor
	public DemandeAutorisation() {
		super();
		annexes = new ListeDesAnnexes();
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

	// ********************************************************* Technical methods
	@Override
	public DemandeFK getFunctionalKey() {
		return new DemandeFK(this);
	}
	
	
}
