package ch.vd.demaut.commons.repo;

import ch.vd.demaut.commons.entities.EntityFunctionalKeyAware;
import ch.vd.demaut.commons.fk.FunctionalKey;

/**
 * Gestion générique des méthodes basiques sur le repository d'une {@link EntityFunctionalKeyAware}.
 * 
 * @param <E>
 *            l'{@link ch.vd.demaut.commons.entities.Entity} à gérer
 * @param <I>
 *            l'identifiant de l'{@link ch.vd.demaut.commons.entities.Entity}
 */
public interface GenericFKARepository<E extends EntityFunctionalKeyAware, FK extends FunctionalKey<E>> extends
        GenericRepository<E, Long> {

    /**
     * Retourne une entité via sa clé fonctionnelle
     * 
     * Renvoie une exception si plusieurs entités correspondante sont trouvées
     * 
     * @param functionalKey
     * @return L'entité correspondante à la clé fonctionnelle
     */
    E findByFK(FK functionalKey);

    /**
     * Retourne une entité via sa clé fonctionnelle et une exception si elle n'est pas trouvé o
     * 
     * Renvoie une exception si aucune entité correspondante n'est trouvée Renvoie une exception si plusieurs entités
     * correspondante sont trouvées
     * 
     * @param functionalKey
     * @return L'entité correspondante à la clé fonctionnelle
     */
    E getByFK(FK functionalKey);
}