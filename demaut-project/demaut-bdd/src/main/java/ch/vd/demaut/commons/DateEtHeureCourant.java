package ch.vd.demaut.commons;

import org.joda.time.DateTime;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.DateTimeVO;

@ValueObject
public class DateEtHeureCourant extends DateTimeVO {

	// ********************************************************* Constructor
	public DateEtHeureCourant(DateTime dateHeureCourant) {
		super(dateHeureCourant);
	}

	// ********************************************************* Business Methods
	

}
