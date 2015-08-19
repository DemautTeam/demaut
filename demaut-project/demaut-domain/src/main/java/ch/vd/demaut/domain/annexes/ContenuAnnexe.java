package ch.vd.demaut.domain.annexes;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;

@ValueObject
public class ContenuAnnexe extends BaseValueObject{

	// ********************************************************* Fields
	final byte[] contenu;
	
	final long taille; //en octets

	// ********************************************************* Constructor
	public ContenuAnnexe(byte[] contenu) {
		this.contenu = contenu;
		this.taille = calculerTaille(contenu);
	}

	// ********************************************************* Business Methods

	/**
	 * Vérifie que le contenu de l'annexe est valide. 
	 * Si non valide, renvoie une {@link ContenuAnnexeNonValideException} 
	 */
	public void validerContenu() {
		if (contenu == null) {
			throw new ContenuAnnexeNonValideException();
		}
	}

	// ********************************************************* Getters
	public byte[] getContenu() {
		return contenu;
	}
	
	public long getTaille() {
		return taille;
	}

	// ********************************************************* Méthodes privées
	private long calculerTaille(byte[] contenu) {
		if (contenu == null) {
			return 0L;
		} else {
			return contenu.length;
		}
	}


}
