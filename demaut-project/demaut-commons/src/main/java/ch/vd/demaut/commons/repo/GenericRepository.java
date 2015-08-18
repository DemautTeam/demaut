package ch.vd.demaut.commons.repo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import javax.validation.ConstraintViolation;

import ch.vd.demaut.commons.entities.Entity;
import ch.vd.demaut.commons.exceptions.DomainException;

/**
 * Gestion générique des méthodes basiques sur le repository d'une {@link Entity}.
 * 
 * @param <E>
 *            l'{@link Entity} à gérer
 * @param <I>
 *            l'identifiant de l'{@link Entity}
 */
public interface GenericRepository<E, I extends Serializable> extends
        GenericReadRepository<E, I> {

    /**
     * Persiste une {@link Entity}.
     *
     * @param entity
     * @return
     */
    E store(E entity);

    /**
     * Persiste une liste de {@link Entity}.
     *
     * @param entities
     */
    void storeAll(Collection<? extends E> entities);

    /**
     * Supprime une {@link Entity}.
     *
     * @param entity
     */
    void delete(E entity);

    /**
     * Supprime une {@link Entity} d'après son identifiant.
     *
     * @param id
     */
    void delete(I id);

    /**
     * Supprimer tous les {@link Entity} du repo
     */
    void deleteAll();

    /**
     * Valide l'entité
     * 
     * @return True si l'entité est valide
     */
    Set<ConstraintViolation<E>> validate(E entity);

    /**
     * Valide puis persiste une {@link Entity}. Renvoie une exception {@link DomainException} si l'entité n'est pas
     * valide
     * 
     * @param entity
     * @return l'entité validée et persistée
     */
    E validateAndStore(E entity);

    /**
     * Valide puis persiste une liste de {@link Entity}. Renvoie une exception {@link DomainException} si une entité de
     * la liste n'est pas valide
     * 
     * @param entities
     */
    void validateAndStoreAll(Collection<? extends E> entities);

}