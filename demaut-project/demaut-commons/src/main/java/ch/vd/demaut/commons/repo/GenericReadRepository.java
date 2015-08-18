package ch.vd.demaut.commons.repo;

import java.util.List;

import ch.vd.demaut.commons.entities.Entity;

/**
 * Gestion générique des méthodes basiques de lecture sur le repository d'une {@link Entity}.
 * 
 * @param <E>
 *            l'{@link Entity} à gérer
 * @param <I>
 *            l'identifiant de l'{@link Entity}
 */
public interface GenericReadRepository<E, I> {

    /**
     * @return le premier {@link Entity} trouvé dans le repository.
     */
    E findFirst();

    /**
     * Recherche une {@link Entity} par identifiant.
     * 
     * @param id
     * @return
     */
    E findBy(I id);

    /**
     * Recherche toute la liste d'{@link Entity}s.
     * 
     * @return
     */
    List<E> findAll();

    /**
     * @return le nombre d'{@link Entity} dans le repository.
     */
    long countAll();

    /**
     * Retourne une {@link Entity} par identifiant.
     * 
     * Si elle n'est pas trouvee, alors leve une exception
     * 
     * @param id
     * @return
     */
    E getById(I id);

}