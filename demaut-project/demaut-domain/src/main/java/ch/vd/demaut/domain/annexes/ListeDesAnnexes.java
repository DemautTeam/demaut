package ch.vd.demaut.domain.annexes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;

/**
 * Représente une liste d'annexes
 */

public class ListeDesAnnexes {

    // ********************************************************* Fields
    private List<Annexe> annexes;

    // ********************************************************* Constructor
    
    public ListeDesAnnexes(List<Annexe> annexes2) {
    	this.annexes = annexes2;
	}

	// ********************************************************* Business
    // methods
    public void ajouterAnnexe(Annexe annexe) {
        annexes.add(annexe);
    }

    // ********************************************************* Getters

    /**
     * Renvoie la liste des annexes
     */
    public List<Annexe> listerAnnexes() {
        return Collections.unmodifiableList(annexes);
    }

    /**
     * Extrait les annexes d'un type TypeAnnexe
     */
    @SuppressWarnings("unchecked")
    public Collection<Annexe> extraireAnnexesDeType(TypeAnnexe typeAnnexe) {
        return CollectionUtils.select(annexes, new BeanPropertyValueEqualsPredicate("typeAnnexe", typeAnnexe));
    }

    public void supprimerUneAnnexeParNomFichier(final String nomFichierAnnexe) {
    	Annexe annexeTrouvee = trouverAnnexeParNomFichier(nomFichierAnnexe);
        annexes.remove(annexeTrouvee);
    }
    
	public Annexe trouverAnnexeParNomFichier(final String annexeFileName) {
		NomFichier nomFichier = new NomFichier(annexeFileName);
        Object annexeTrouvee = CollectionUtils.find(annexes, new BeanPropertyValueEqualsPredicate("nomFichier", nomFichier));
        if (annexeTrouvee == null) {
        	throw new AnnexeIntrouvableException();
        }
		return (Annexe)annexeTrouvee;
	}

	// ********************************************************* Private
}
