package ch.vd.demaut.domain.model.annexes;

import ch.vd.demaut.commons.vo.BaseValueObject;
import ch.vd.demaut.commons.annotations.ValueObject;

/**
 *  Représente une Annexe associée à une demande
 *
 */
@ValueObject
public class Annexe extends BaseValueObject {
	
	// ********************************************************* Fields
	final private TypeAnnexe typeAnnexe;
	
	final private DateDeReceptionAnnexe dateDeReception;
	
	final private ContenuAnnexe contenu;

	// ********************************************************* Constructor
	public Annexe(TypeAnnexe typeAnnexe, DateDeReceptionAnnexe dateDeReception, byte[] contenu) {
		super();
		this.typeAnnexe = typeAnnexe;
		this.dateDeReception = dateDeReception;
		this.contenu = new ContenuAnnexe(contenu);
	}
	// ********************************************************* Business Methods

	// ********************************************************* Private Methods

	// ********************************************************* Getters
	public TypeAnnexe getTypeAnnexe() {
		return typeAnnexe;
	}
	
	public DateDeReceptionAnnexe getDateDeReception() {
		return dateDeReception;
	}
	
	public ContenuAnnexe getContenu() {
		return contenu;
	}

	// ********************************************************* Technical methods

}
