package ch.vd.ses.demaut.dao.repository;

import com.google.common.collect.Ordering;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class DemautRepository<T, I> implements IDemautRepository<T, I> {

    protected Class<T> entityClass;

    private EntityManager entityManager;

    public DemautRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    @PersistenceContext
    public void setEntityManager(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @SuppressWarnings("all")
    @Transactional(readOnly = true)
    public List<T> findAll() {
        Query typedQuery = this.getEntityManager().createQuery("select o from " + entityClass.getSimpleName() + " as o");
        return Ordering.usingToString().sortedCopy(typedQuery.getResultList());
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
        this.getEntityManager().flush();
        return entity;
    }

    @Override
    @Transactional
    public void delete(I id) {
        if (id == null) {
            return;
        }

        T entity = this.find(id);
        if (entity == null) {
            return;
        }

        this.getEntityManager().flush();
    }
}