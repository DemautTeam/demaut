package ch.vd.demaut.domain.annexes;

import java.util.Collection;
import java.util.List;



import ch.vd.demaut.commons.entities.EntityFKAList;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;

/**
 * Représente une liste d'annexes uniques
 */

public class ListeDesAnnexes extends EntityFKAList<Annexe> {

    
    public ListeDesAnnexes(List<Annexe> annexes) {
        super(annexes);
    }
    
    public ListeDesAnnexes() {
        super();
    }

    // ********************************************************* Business
    // methods

    /**
     * Ajoute une annexe à la liste d'annexes<br>
     * Renvoie une exception si l'annnexe n'est pas unique
     *
     * @param annexe Annexe
     */
    public void ajouterAnnexe(Annexe annexe) {
        ajouterEntity(annexe);
    }

    /**
     * Suprimer une annexe. <br>
     * Renvoie exception si annexe pas trouvée
     *
     * @param annexeFK
     */
    public void supprimerUneAnnexe(AnnexeFK annexeFK) {
        supprimerEntity(annexeFK);
    }

    /**
     * Renvoie la liste des annexes
     */
    public List<Annexe> listerAnnexes() {
        return listerEntities();
    }

    public Collection<AnnexeMetadata> listerAnnexesMetadata() {
        return CollectionUtils.collect(listerEntities(), new Transformer<Annexe, AnnexeMetadata>() {
            @Override
            public AnnexeMetadata transform(Annexe input) {
                return input.getAnnexeMetadata();
            }
        });
    }

    public ContenuAnnexe extraireContenu(AnnexeFK annexeFK) {
        Annexe annexe = trouverEntity(annexeFK);
        return annexe.getContenu();
    }

    // ******************************************************* Methodes privees

}
