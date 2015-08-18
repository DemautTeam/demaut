package ch.vd.demaut.domain.model.annexes;

import ch.vd.demaut.commons.vo.BaseValueObject;
import ch.vd.demaut.commons.annotations.ValueObject;

@ValueObject
public class ContenuAnnexe extends BaseValueObject{

	// ********************************************************* Fields
	final byte[] contenu;

	// ********************************************************* Constructor
	public ContenuAnnexe(byte[] contenu) {
		this.contenu = contenu;
	}

	// ********************************************************* Getters
	public byte[] getContenu() {
		return contenu;
	}
	
}
