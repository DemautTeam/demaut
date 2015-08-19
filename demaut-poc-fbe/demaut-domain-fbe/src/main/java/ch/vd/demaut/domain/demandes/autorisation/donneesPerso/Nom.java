package ch.vd.demaut.domain.demandes.autorisation.donneesPerso;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.StringVO;

@ValueObject
public class Nom extends StringVO {

	// ********************************************************* Constructor
	public Nom(String value) {
		super(value);
	}
}
