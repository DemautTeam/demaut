package ch.vd.demaut.commons.vo;

import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

abstract public class DateTimeVO extends BaseValueObject {

	// ********************************************************* Fields
	@NotNull
	final private DateTime dateTime;

	// ********************************************************* Constructor

	public DateTimeVO(int annee, int mois, int jourDuMois, int heure, int minute, int seconde) {
		super();
		this.dateTime = new DateTime(annee, mois, jourDuMois, heure, minute, seconde);
	}
	
	public DateTimeVO(DateTime dateTime) {
		super();
		this.dateTime = dateTime;
	}

	// ********************************************************* Getters
	
	public LocalDate getLocalDate() {
		return dateTime.toLocalDate();
	}
	
	public DateTime getDateTime() {
		return dateTime;
	}
}
