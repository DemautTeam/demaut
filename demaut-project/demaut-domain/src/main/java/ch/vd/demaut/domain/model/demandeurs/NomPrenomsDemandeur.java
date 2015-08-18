package ch.vd.demaut.domain.model.demandeurs;

import org.hibernate.validator.constraints.NotBlank;

import ch.vd.demaut.commons.vo.BaseValueObject;
import ch.vd.demaut.commons.annotations.ValueObject;

@ValueObject
public class NomPrenomsDemandeur extends BaseValueObject {

	// ********************************************************* Fields
	@NotBlank
	final private String nom;
	
	@NotBlank
	final private String prenom1;

	final private String prenom2;
	
	// ********************************************************* Constructor

	public NomPrenomsDemandeur(String nom, String prenom1) {
		super();
		this.nom = nom;
		this.prenom1 = prenom1;
		this.prenom2 = null;
	}
	
	public NomPrenomsDemandeur(String nom, String prenom1, String prenom2) {
		super();
		this.nom = nom;
		this.prenom1 = prenom1;
		this.prenom2 = prenom2;
	}
	
	// ********************************************************* Getters
	public String getNom() {
		return nom;
	}
	
	public String getPrenom1() {
		return prenom1;
	}
	
	public String getPrenom2() {
		return prenom2;
	}
	
	// ********************************************************* Private Methods
	
}
