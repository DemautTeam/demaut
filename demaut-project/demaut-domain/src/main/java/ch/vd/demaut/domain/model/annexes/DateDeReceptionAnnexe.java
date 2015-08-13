package ch.vd.demaut.domain.model.annexes;

import ch.vd.demaut.commons.DateTimeVO;
import ch.vd.demaut.commons.annotations.ValueObject;

@ValueObject
public class DateDeReceptionAnnexe extends DateTimeVO {

	// ********************************************************* Constructor
	public DateDeReceptionAnnexe(int annee, int mois, int jourDuMois, int heure, int minute, int seconde) {
		super(annee, mois, jourDuMois, heure, minute, seconde);
	}

	// ********************************************************* Business Methods
	

}
