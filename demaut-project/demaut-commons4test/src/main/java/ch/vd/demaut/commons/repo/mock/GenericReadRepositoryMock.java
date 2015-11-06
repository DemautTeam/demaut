package ch.vd.demaut.commons.repo.mock;

import java.io.Serializable;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;

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
public abstract class GenericReadRepositoryMock<T extends Entity<ID>, ID extends Serializable>
        implements GenericReadRepository<T, ID> {

    /**
     * {@inheritDoc}
     */
    @Override
    public T findBy(final ID id) {
        return (T) CollectionUtils.find(findAll(), new Predicate<T>() {
            @Override
            public boolean evaluate(T object) {
                return object.getId().equals(id);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long countAll() {
        return findAll().size();
    }
}
