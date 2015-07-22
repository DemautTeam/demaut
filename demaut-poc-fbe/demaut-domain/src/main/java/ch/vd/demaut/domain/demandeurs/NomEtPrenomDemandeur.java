package ch.vd.demaut.domain.demandeurs;

import org.hibernate.validator.constraints.NotBlank;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;

@ValueObject
public class NomEtPrenomDemandeur extends BaseValueObject {

	// ********************************************************* Fields
	@NotBlank
	final private String nom;
	
	@NotBlank
	final private String prenom;

	// ********************************************************* Constructor

	public NomEtPrenomDemandeur(String nom, String prenom) {
		super();
		this.nom = nom;
		this.prenom = prenom;
	}

	
	// ********************************************************* Getters
	public String getNom() {
		return nom;
	}
	
	public String getPrenom() {
		return prenom;
	}
	

	// ********************************************************* Private Methods
	
}
