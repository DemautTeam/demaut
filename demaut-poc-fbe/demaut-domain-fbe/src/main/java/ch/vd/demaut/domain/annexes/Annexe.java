package ch.vd.demaut.domain.annexes;

import javax.validation.constraints.NotNull;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;

/**
 *  Représente une Annexe associée à une demande
 *
 */
@ValueObject
public class Annexe extends BaseValueObject {
	
	// ********************************************************* Fields
	@NotNull
	final private TypeAnnexe typeAnnexe;
	
	@NotNull
	final private ContenuAnnexe contenu;
	
	@NotNull
	final private TypeFichier typeFichier;
	
	// ********************************************************* Constructor
	public Annexe(TypeAnnexe typeAnnexe, byte[] contenu) {
		super();
		this.typeAnnexe = typeAnnexe;
		this.contenu = new ContenuAnnexe(contenu);
		this.typeFichier = TypeFichier.PDF;
	}
	// ********************************************************* Business Methods
	
	/**
	 * Valide le contenu de l'annexe.
	 * Renvoie l'exception {@link ContenuAnnexeNonValideException} si non valide
	 */
	public void validerContenu() {
		contenu.validerContenu();
	}
	

	// ********************************************************* Getters
	
	public TypeAnnexe getTypeAnnexe() {
		return typeAnnexe;
	}
	
	public ContenuAnnexe getContenu() {
		return contenu;
	}
	
	public TypeFichier getTypeFichier() {
		return typeFichier;
	}


	// ********************************************************* Technical methods

	// ********************************************************* Private Methods
}
