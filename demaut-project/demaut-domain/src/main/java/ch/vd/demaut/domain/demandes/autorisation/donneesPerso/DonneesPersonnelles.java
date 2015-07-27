package ch.vd.demaut.domain.demandes.autorisation.donneesPerso;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;

@ValueObject
public class DonneesPersonnelles extends BaseValueObject {

	// ********************************************************* Fields
	final private Nom nom;

	final private Prenom prenom;

	final private Sexe sexe;

	final private DateDeNaissance dateDeNaissance;

	final private CodeGLN codeGLN;

	// ********************************************************* Constructor

	public DonneesPersonnelles(Nom nom, Prenom prenom, Sexe sexe, DateDeNaissance dateDeNaissance, CodeGLN codeGLN) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.sexe = sexe;
		this.dateDeNaissance = dateDeNaissance;
		this.codeGLN = codeGLN;
	}

	// ********************************************************* Getters
	public Nom getNom() {
		return nom;
	}

	public Prenom getPrenom() {
		return prenom;
	}

	public Sexe getSexe() {
		return sexe;
	}

	public DateDeNaissance getDateDeNaissance() {
		return dateDeNaissance;
	}

	public CodeGLN getCodeGLN() {
		return codeGLN;
	}

}
