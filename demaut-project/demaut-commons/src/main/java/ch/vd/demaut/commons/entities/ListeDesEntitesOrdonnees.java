package ch.vd.demaut.commons.entities;

import java.util.List;

import ch.vd.demaut.commons.vo.OrdreVO;

/**
 * Représente une liste d'Entity dont la FK est OrdreFK
 *
 * @param <E> Entite sous classe de  {@link EntityAvecOrdreFK}
 */
public abstract class ListeDesEntitesOrdonnees<E extends EntityAvecOrdreFK> extends EntityFKAList<E>  {

    // *********************************************** Constructeurs
    protected ListeDesEntitesOrdonnees(List<E> entities) {
        super(entities);
    }
    
    protected ListeDesEntitesOrdonnees() {
    }

    // *********************************************** Methodes metier
    public OrdreVO genererNouvelOrdre() {
        OrdreVO ordreMax = trouverOrdreMax();
        OrdreVO ordreActiviteFuture = new OrdreVO(ordreMax.getOrdre() + 1);
        return ordreActiviteFuture;
    }

    // *********************************************** Methodes privees
    private OrdreVO trouverOrdreMax() {
        OrdreVO ordreMax = new OrdreVO(0);
        for (E entity : listerEntities()) {
            if (ordreMax.compareTo(entity.getOrdre()) < 0) {
                ordreMax = entity.getOrdre();
            }
        }
        return ordreMax;
    }
}
