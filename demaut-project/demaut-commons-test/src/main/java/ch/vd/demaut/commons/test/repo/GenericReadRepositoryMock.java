package ch.vd.demaut.commons.test.repo;

import java.io.Serializable;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;

import ch.vd.demaut.commons.entities.Entity;
import ch.vd.demaut.commons.repo.GenericReadRepository;

/**
 * Implémentation mock du {@link GenericReadRepository}.
 * 
 * @param <T>
 *            l'{@link Entity} à gérer
 * @param <ID>
 *            l'identifiant de l'{@link Entity}
 */
public abstract class GenericReadRepositoryMock<T extends Entity<ID>, ID extends Serializable> implements
        GenericReadRepository<T, ID> {

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public T findBy(ID id) {
        return (T) CollectionUtils.find(findAll(), new BeanPropertyValueEqualsPredicate("id", id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T findFirst() {
        if (findAll().isEmpty()) {
            return null;
        }
        return findAll().get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long countAll() {
        return findAll().size();
    }

}
