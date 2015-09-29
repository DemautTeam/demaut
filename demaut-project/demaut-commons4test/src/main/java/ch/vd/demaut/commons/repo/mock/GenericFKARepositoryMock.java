package ch.vd.demaut.commons.repo.mock;

import java.util.List;

import ch.vd.demaut.commons.entities.EntityFunctionalKeyAware;
import ch.vd.demaut.commons.exceptions.EntityNotFoundException;
import ch.vd.demaut.commons.exceptions.EntityNotUniqueException;
import ch.vd.demaut.commons.fk.FunctionalKey;
import ch.vd.demaut.commons.repo.GenericFKARepository;

/**
 * Repo mock pour les entités avec des clés fonctionnelles qui ajoute des methodes telles que findByFK
 */
public abstract class GenericFKARepositoryMock<E extends EntityFunctionalKeyAware, FK extends FunctionalKey<E>> extends
        GenericRepositoryMock<E, Long> implements GenericFKARepository<E, FK> {

    /**
     * {@inheritDoc}
     */
    @Override
    public E findByFK(FK functionalKey) {
        List<E> entities = findAll();
        E foundEntity = null;
        for (E entity : entities) {
            if (functionalKey.equals(entity.getFunctionalKey())) {
                if (foundEntity != null) {
                    throw new EntityNotUniqueException();
                }
                foundEntity = entity;
            }
        }
        return foundEntity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E getByFK(FK functionalKey) {
        E entity = findByFK(functionalKey);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        return entity;
    }
}
