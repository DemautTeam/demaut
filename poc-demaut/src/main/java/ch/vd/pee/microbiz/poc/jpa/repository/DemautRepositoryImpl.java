package ch.vd.pee.microbiz.poc.jpa.repository;

//import com.google.common.collect.Ordering;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public abstract class DemautRepositoryImpl<T, I> implements DemautRepository<T, I> {

    protected Class<T> entityClass;

    @PersistenceContext
    private EntityManager entityManager;

    public DemautRepositoryImpl(Class<T> entityClass) {
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
    public T find(I id) {
        return this.getEntityManager().find(this.entityClass, id);
    }

    @Override
    @Transactional
    public T save(T entity) {
        this.getEntityManager().persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public boolean delete(I id) {
        if (id == null) {
            return false;
        }

        T entity = this.find(id);
        if (entity == null) {
            return false;
        }
        this.getEntityManager().remove(entity);
        return true;
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
}