package ch.vd.demaut.data;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import ch.vd.demaut.commons.exceptions.ValidationEntityException;
import ch.vd.demaut.commons.repo.GenericReadRepository;
import ch.vd.demaut.commons.repo.GenericRepository;
import ch.vd.demaut.commons.validation.ValidatorFactoryDefault;

public abstract class GenericRepositoryImpl<T, I extends Serializable> implements GenericRepository<T, I>, GenericReadRepository<T, I> {

    protected Class<T> entityClass;

    @PersistenceContext
    private EntityManager entityManager;

    public GenericRepositoryImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    public void setEntityManager(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public T findBy(I id) {
        T entity = getEntityManager().find(this.entityClass, id);
        return entity;
    }

    @Override
    @SuppressWarnings("all")
    public List<T> findAll() {
        Query typedQuery = getEntityManager()
                .createQuery("select o from " + entityClass.getSimpleName() + " as o");
        return typedQuery.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<T> findRange(int[] range) {
        Query typedQuery = getEntityManager()
                .createQuery("select o from " + entityClass.getSimpleName() + " as o");
        typedQuery.setMaxResults(range[1] - range[0]);
        typedQuery.setFirstResult(range[0]);
        return typedQuery.getResultList();
    }

    @Override
    public long countAll() {
        Query typedQuery = getEntityManager()
                .createQuery("select count(o) from " + entityClass.getSimpleName() + " o");
        return ((Long) typedQuery.getSingleResult()).intValue();
    }

    @Override
    public void store(T entity) {
        getEntityManager().persist(entity);
    }

    @Override
    public void storeAll(Collection<? extends T> entities) {
        getEntityManager().persist(entities);
    }

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
        Query typedQuery = getEntityManager().createQuery("select o from " + entityClass.getSimpleName() + " as o");
        @SuppressWarnings("unchecked")
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