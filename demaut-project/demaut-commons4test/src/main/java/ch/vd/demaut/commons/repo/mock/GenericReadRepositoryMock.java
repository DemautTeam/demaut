package ch.vd.demaut.commons.repo.mock;

import ch.vd.demaut.commons.entities.Entity;
import ch.vd.demaut.commons.repo.GenericReadRepository;
import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;

/**
 * Implémentation mock du {@link GenericReadRepository}.
 *
 * @param <T>  l'{@link Entity} à gérer
 * @param <ID> l'identifiant de l'{@link Entity}
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
    public long countAll() {
        return findAll().size();
    }

}
