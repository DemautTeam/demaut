package ch.vd.demaut.domain.annexes;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;

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

    @SuppressWarnings("all")
    public Collection<AnnexeMetadata> listerAnnexesMetadata() {
        return CollectionUtils.collect(annexes, new Transformer() {
            @Override
            public Object transform(Object input) {
                return ((Annexe) input).getAnnexeMetadata();
            }
        });
    }

    /**
     * Extrait les annexes d'un type TypeAnnexe
     */
    @SuppressWarnings("unchecked")
    public Collection<Annexe> extraireAnnexesDeType(TypeAnnexe typeAnnexe) {
        return CollectionUtils.select(annexes, new BeanPropertyValueEqualsPredicate("typeAnnexe", typeAnnexe));
    }

    @SuppressWarnings("unchecked")
    public Collection<AnnexeMetadata> extraireAnnexesMetadatasDeType(TypeAnnexe typeAnnexe) {
        return CollectionUtils.select(listerAnnexesMetadata(), new BeanPropertyValueEqualsPredicate("typeAnnexe", typeAnnexe));
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

	public AnnexeMetadata extraireAnnexeMetadata(NomFichier nomFichier) {
		Annexe annexe = trouverAnnexeParNomFichier(nomFichier.getNomFichier());
		return annexe.getAnnexeMetadata();
	}

}
