package ch.vd.demaut.domain.annexes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;

/**
 * Représente une liste d'annexes
 *
 */
public class ListeDesAnnexes {

	// ********************************************************* Fields
	private List<Annexe> annexes = new ArrayList<Annexe>();

	// ********************************************************* Constructor

	// ********************************************************* Business
	// methods
	public void ajouterAnnexe(Annexe annexe) {
		annexes.add(annexe);
	}

	// ********************************************************* Getters
	/**
	 * Renvoie la liste des annexes
	 */
	public Collection<Annexe> listerAnnexes() {
		return Collections.unmodifiableList(annexes);
	}

	/**
	 * Extrait les annexes d'un type TypeAnnexe
	 */
	public Collection<Annexe> extraireAnnexesDeType(TypeAnnexe typeAnnexe) {
		@SuppressWarnings("unchecked")
		Collection<Annexe> res = CollectionUtils.select(annexes,
				new BeanPropertyValueEqualsPredicate("typeAnnexe", typeAnnexe));
		return res;
	}

}
