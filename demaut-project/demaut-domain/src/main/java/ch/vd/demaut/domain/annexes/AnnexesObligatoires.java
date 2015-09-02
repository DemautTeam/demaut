package ch.vd.demaut.domain.annexes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;

/**
 * Liste de types d'annexe ({@link Annexe}) obligatoire pour la complétude d'une {@link DemandeAutorisation}
 *
 */
@ValueObject
public class AnnexesObligatoires extends BaseValueObject {

	// ********************************************************* Fields

	private final List<TypeAnnexe> typesAnnexe;

	// ********************************************************* Constructor
	
	public AnnexesObligatoires(List<TypeAnnexe> types) {
		this.typesAnnexe = types;
	}

	public AnnexesObligatoires(TypeAnnexe ...types) {
		this(Arrays.asList(types));
	}
	
	// ********************************************************* Methode metier

	public List<TypeAnnexe> listerTypesAnnexe() {
		return Collections.unmodifiableList(typesAnnexe);
	}
	
	// ********************************************************* Getters

	// ********************************************************* Builder
	 public static class Builder {
		 private List<TypeAnnexe> typesAnnexe = new ArrayList<TypeAnnexe>();
		 
		 public Builder ajouterAnnexeObligatoire(TypeAnnexe typeAnnexe) {
			 typesAnnexe.add(typeAnnexe);
			 return this;
		 }
		 
		 public AnnexesObligatoires build() {
			 return new AnnexesObligatoires(typesAnnexe);
		 }
	 }
	
}