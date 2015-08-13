package ch.vd.demaut.domain.model.demandes.autorisation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import ch.vd.demaut.domain.model.annexes.Annexe;

public class ListeDesAnnexes {

	// ********************************************************* Fields
	private List<Annexe> annexes = new ArrayList<Annexe>(); 

	// ********************************************************* Constructor

	// ********************************************************* Business methods
	public void ajouterAnnexe(Annexe annexe) {
		annexes.add(annexe);
	}
	
	// ********************************************************* Getters
	public Collection<Annexe> listerAnnexes() {
		return Collections.unmodifiableList(annexes);
	}
	
}
