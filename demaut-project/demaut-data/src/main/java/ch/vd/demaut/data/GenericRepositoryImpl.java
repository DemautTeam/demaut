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
import ch.vd.demaut.commons.validation.ValidatorFactoryDefault;
import org.springframework.transaction.annotation.Transactional;

import ch.vd.demaut.commons.repo.GenericRepository;

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
    @Transactional(readOnly = true)
    public T findBy(I id) {
        return this.getEntityManager().find(this.entityClass, id);
    }

    @Override
    @SuppressWarnings("all")
    @Transactional(readOnly = true)
    public List<T> findAll() {
        Query typedQuery = this.getEntityManager()
                .createQuery("select o from " + entityClass.getSimpleName() + " as o");
        return typedQuery.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<T> findRange(int[] range) {
        Query typedQuery = this.getEntityManager()
                .createQuery("select o from " + entityClass.getSimpleName() + " as o");
        typedQuery.setMaxResults(range[1] - range[0]);
        typedQuery.setFirstResult(range[0]);
        return typedQuery.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public long countAll() {
        Query typedQuery = this.getEntityManager()
                .createQuery("select count(o) from " + entityClass.getSimpleName() + " o");
        return ((Long) typedQuery.getSingleResult()).intValue();
    }

    @Override
    @Transactional
    public T store(T entity) {
        this.getEntityManager().persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public void storeAll(Collection<? extends T> entities) {
        this.getEntityManager().persist(entities);
    }

    @Override
    @Transactional
    public void delete(T entity) {
        this.getEntityManager().remove(entity);
    }
    
	@Override
    @Transactional
	public void delete(I id) {
        T entity =  this.getEntityManager().find(this.entityClass, id);
        if(entity != null){
            this.getEntityManager().remove(entity);
        }
	}

	@Override
    @Transactional
	public void deleteAll() {
        Query typedQuery = this.getEntityManager()
                .createQuery("select o from " + entityClass.getSimpleName() + " as o");
        List entities = typedQuery.getResultList();
        if(entities != null && !entities.isEmpty()){
            this.getEntityManager().remove(entities);
        }
	}

	@Override
	public Set<ConstraintViolation<T>> validate(T entity) {
        Validator validator = ValidatorFactoryDefault.getValidator();
        return validator.validate(entity);
	}

	@Override
    @Transactional
	public T validateAndStore(T entity) {
        Set<ConstraintViolation<T>> constraintsViolation = validate(entity);
        if (constraintsViolation == null || constraintsViolation.size() == 0) {
            return store(entity);
        } else {
            throw new ValidationEntityException();
        }
	}

	@Override
    @Transactional
	public void validateAndStoreAll(Collection<? extends T> entities) {
        for (T entity : entities) {
            validateAndStore(entity);
        }
	}
}