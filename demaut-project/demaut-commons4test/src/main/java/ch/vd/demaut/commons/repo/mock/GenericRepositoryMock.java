package ch.vd.demaut.commons.repo.mock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import ch.vd.demaut.commons.entities.Entity;
import ch.vd.demaut.commons.exceptions.ValidationEntityException;
import ch.vd.demaut.commons.repo.GenericRepository;
import ch.vd.demaut.commons.validation.ValidatorFactoryDefault;

/**
 * Implémentation mock du {@link GenericRepository}.
 *
 * @param <T>  l'{@link Entity} à gérer
 * @param <ID> l'identifiant de l'{@link Entity}
 */
public abstract class GenericRepositoryMock<T extends Entity<ID>, ID extends Serializable> implements
        GenericRepository<T, ID> {

    private final Map<ID, T> entities = new HashMap<ID, T>();

    /**
     * {@inheritDoc}
     */
    @Override
    public T findBy(ID id) {
        return entities.get(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> findAll() {
        return new ArrayList<T>(entities.values());
    }

    /**
     * @return retourne la prochaine séquence de l'{@link ID} pour pouvoir persister une nouvelle {@link Entity}.<br/>
     * Dans le cas où l'{@link ID} de l'{@link Entity} n'a pas besoin d'être géré, il faut implémenter la
     * méthode avec {@code "return null;"}.
     */
    protected abstract ID getNextID();

    /**
     * {@inheritDoc}<br/>
     * <p/>
     * {@link T} n'est pas persisté si elle existe déjà dans le repository.
     */
    @Override
    public T store(T entity) {
        if (findBy(entity.getId()) == null) {
            ID nextID = getNextID();
            if (nextID != null) {
                entity.setId(nextID);
            }
        } else {
            delete(entity);
        }
        entities.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public void storeAll(Collection<? extends T> entities) {
        for (T entity : entities) {
            store(entity);
        }
    }

    /**
     * {@inheritDoc}<br/>
     *
     * @param entity
     * @return L'entité persistée et null si elle n'est pas validée
     */
    @Override
    public T validateAndStore(T entity) {
        Set<ConstraintViolation<T>> constraintsViolation = validate(entity);
        if (constraintsViolation == null || constraintsViolation.size() == 0) {
            return store(entity);
        } else {
            throw new ValidationEntityException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validateAndStoreAll(Collection<? extends T> entities) {
        for (T entity : entities) {
            validateAndStore(entity);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(T entity) {
        entities.remove(entity.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(ID id) {
        if (id != null) {
            T entity = findBy(id);
            if (entity != null) {
                delete(entity);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAll() {
        entities.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long countAll() {
        return entities.values().size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<ConstraintViolation<T>> validate(T entity) {
        Validator validator = ValidatorFactoryDefault.getValidator();
        return validator.validate(entity);
    }

}
