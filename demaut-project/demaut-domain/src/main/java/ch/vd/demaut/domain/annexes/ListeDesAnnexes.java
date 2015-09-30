package ch.vd.demaut.domain.annexes;

import ch.vd.demaut.domain.exception.AnnexeIntrouvableException;
import ch.vd.demaut.domain.exception.AnnexeNonUniqueException;
import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Représente une liste d'annexes uniques
 */

public class ListeDesAnnexes {

    // ********************************************************* Fields
    private List<Annexe> annexes;

    // ********************************************************* Constructor

    public ListeDesAnnexes() {
        this.annexes = new ArrayList<Annexe>();
    }

    public ListeDesAnnexes(List<Annexe> annexes2) {
        this.annexes = annexes2;
    }

    // ********************************************************* Business
    // methods

    /**
     * Ajoute une annexe à la liste d'annexes<br>
     * Renvoie une exception si l'annnexe n'est pas unique
     *
     * @param annexe
     */
    public void ajouterAnnexe(Annexe annexe) {
        validerAnnexeUnique(annexe);
        annexes.add(annexe);
    }

    /**
     * Suprimer une annexe. <br>
     * Renvoie exception si annexe pas trouvée
     *
     * @param annexeFK
     */
    public void supprimerUneAnnexe(AnnexeFK annexeFK) {
        Annexe annexeTrouvee = trouverAnnexe(annexeFK);
        annexes.remove(annexeTrouvee);
    }

    /**
     * Renvoie la liste des annexes
     */
    public List<Annexe> listerAnnexes() {
        if (annexes == null) {
            return Collections.unmodifiableList(new ArrayList<Annexe>());
        }
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

    public Annexe trouverAnnexe(AnnexeFK annexeFK) {
        Object annexeTrouvee = CollectionUtils.find(annexes, new BeanPropertyValueEqualsPredicate("functionalKey", annexeFK));
        if (annexeTrouvee == null) {
            throw new AnnexeIntrouvableException();
        }
        return (Annexe) annexeTrouvee;
    }

    public ContenuAnnexe extraireContenu(AnnexeFK annexeFK) {
        Annexe annexe = trouverAnnexe(annexeFK);
        return annexe.getContenu();
    }

    // ******************************************************* Methodes privees

    private void validerAnnexeUnique(Annexe annexe) {
        if (annexes.contains(annexe)) {
            throw new AnnexeNonUniqueException();
        }
        ;
    }


}
