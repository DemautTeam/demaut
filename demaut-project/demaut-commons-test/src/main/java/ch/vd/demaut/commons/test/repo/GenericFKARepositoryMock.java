package ch.vd.demaut.commons.test.repo;

import ch.vd.demaut.commons.entities.EntityFunctionalKeyAware;
import ch.vd.demaut.commons.exceptions.EntityNotFoundException;
import ch.vd.demaut.commons.exceptions.EntityNotUniqueException;
import ch.vd.demaut.commons.fk.FunctionalKey;
import ch.vd.demaut.commons.repo.GenericFKARepository;
import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;

/**
 * Repo mock pour les entités avec des clés fonctionnelles qui ajoute des methodes telles que findByFK
 *
 */
public abstract class GenericFKARepositoryMock<E extends EntityFunctionalKeyAware, FK extends FunctionalKey<E>> extends
        GenericRepositoryMock<E, Long> implements GenericFKARepository<E, FK> {

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public E findByFK(FK functionalKey) {
        Collection<E> trouvees = CollectionUtils.select(findAll(), new BeanPropertyValueEqualsPredicate(
                "functionalKey", functionalKey));
        if (trouvees.size() == 0) {
            return null;
        }
        E first = trouvees.iterator().next();
        if (trouvees.size() > 1) {
            throw new EntityNotUniqueException();
        } else {
            return first;
        }
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
