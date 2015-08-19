package ch.vd.demaut.domain.demandes.autorisation.donneesPerso;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.StringVO;

@ValueObject
public class Prenom extends StringVO {

	// ********************************************************* Constructor
	public Prenom(String value) {
		super(value);
	}
}
