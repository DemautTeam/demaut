package ch.vd.demaut.data;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;

import ch.vd.demaut.commons.repo.GenericReadRepository;
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
    @SuppressWarnings("all")
    @Transactional(readOnly = true)
    public List<T> findAll() {
        Query typedQuery = this.getEntityManager()
                .createQuery("select o from " + entityClass.getSimpleName() + " as o");
        return typedQuery.getResultList();
//        return Ordering.usingToString().sortedCopy(typedQuery.getResultList());
    }

    @Override
    @Transactional(readOnly = true)
    public T findBy(I id) {
        return this.getEntityManager().find(this.entityClass, id);
    }

    @Override
    @Transactional
    public T store(T entity) {
        this.getEntityManager().persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public void delete(T entity) {
        this.getEntityManager().remove(entity);
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

    @Transactional(readOnly = true)
    public int count() {
        Query typedQuery = this.getEntityManager()
                .createQuery("select count(*) from " + entityClass.getSimpleName());
        return ((Long) typedQuery.getSingleResult()).intValue();
    }
    
	@Override
	public void delete(I arg0) {
		//TODO : Implement me
	}

	@Override
	public void deleteAll() {
		//TODO : Implement me
	}

	@Override
	public void storeAll(Collection<? extends T> arg0) {
		//TODO : Implement me
	}

	@Override
	public Set<ConstraintViolation<T>> validate(T arg0) {
		//TODO : Implement me
		return null;
	}

	@Override
	public T validateAndStore(T arg0) {
		//TODO : Implement me
		return null;
	}

	@Override
	public void validateAndStoreAll(Collection<? extends T> arg0) {
		//TODO : Implement me
	}

	@Override
	public long countAll() {
		//TODO : Implement me
		return 0;
	}

	@Override
	public T findFirst() {
		//TODO : Implement me
		return null;
	}

	@Override
	public T getById(I arg0) {
		//TODO : Implement me
		return null;
	}
}