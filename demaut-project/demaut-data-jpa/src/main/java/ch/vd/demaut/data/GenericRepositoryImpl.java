package ch.vd.demaut.data;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import ch.vd.demaut.commons.exceptions.ValidationEntityException;
import ch.vd.demaut.commons.repo.GenericReadRepository;
import ch.vd.demaut.commons.repo.GenericRepository;
import ch.vd.demaut.commons.validation.ValidatorFactoryDefault;

public abstract class GenericRepositoryImpl<T, I extends Serializable> implements GenericRepository<T, I>, GenericReadRepository<T, I> {

    private final Class<T> entityClass;

    /**
     * le nom du persistence unit semble important pour OSGi -> prévision du passage à blueprint
     */
    @PersistenceContext(unitName = "demaut-data-jpa")
    private EntityManager entityManager;

    /**
     * Template classe, il est obligatoire de surcharger ce constructeur.
     *
     * @param entityClass
     */
    protected GenericRepositoryImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    @Override
    public T findBy(I id) {
        T entity = getEntityManager().find(this.entityClass, id);
        return entity;
    }

    @Override
    public List<T> findAll() {
        TypedQuery<T> typedQuery = getEntityManager()
                .createQuery("select o from " + entityClass.getSimpleName() + " as o", entityClass);
        return typedQuery.getResultList();
    }

    public List<T> findRange(int[] range) {
        TypedQuery<T> typedQuery = getEntityManager()
                .createQuery("select o from " + entityClass.getSimpleName() + " as o", entityClass);
        typedQuery.setMaxResults(range[1] - range[0]);
        typedQuery.setFirstResult(range[0]);
        return typedQuery.getResultList();
    }

    @Override
    public long countAll() {
        TypedQuery<Long> typedQuery = getEntityManager()
                .createQuery("select count(o) from " + entityClass.getSimpleName() + " o", Long.class);
        return typedQuery.getSingleResult();
    }

    @Override
    public void store(T entity) {
        getEntityManager().persist(entity);
    }

    @Override
    public void storeAll(Collection<? extends T> entities) {
        getEntityManager().persist(entities);
    }

    /**
     * Attention : Cette méthode nécessite une entité "attachée" à la couche de persistence et dans la même transaction.
     *
     * @param entity
     */
    @Override
    public void delete(T entity) {
        getEntityManager().remove(entity);
    }

    @Override
    public void delete(I id) {
        T entity = getEntityManager().find(this.entityClass, id);
        if (entity != null) {
            getEntityManager().remove(entity);
        }
    }

    @Override
    public void deleteAll() {
        TypedQuery<T> typedQuery = getEntityManager().createQuery("select o from " + entityClass.getSimpleName() + " as o", entityClass);
        List<T> entities = typedQuery.getResultList();
        if (entities != null && !entities.isEmpty()) {
            getEntityManager().remove(entities);
        }
    }

    @Override
    public Set<ConstraintViolation<T>> validate(T entity) {
        Validator validator = ValidatorFactoryDefault.getValidator();
        return validator.validate(entity);
    }

    @Override
    public void validateAndStore(T entity) {
        Set<ConstraintViolation<T>> constraintsViolation = validate(entity);
        if (constraintsViolation == null || constraintsViolation.size() == 0) {
            store(entity);
        } else {
            throw new ValidationEntityException();
        }
    }

    @Override
    public void validateAndStoreAll(Collection<? extends T> entities) {
        for (T entity : entities) {
            validateAndStore(entity);
        }
    }
}