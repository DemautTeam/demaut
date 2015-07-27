package ch.vd.pee.microbiz.poc.jpa.repository;

import ch.vd.pee.microbiz.poc.jpa.entity.Annexe;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AnnexeDemautRepositoryImpl extends DemautRepositoryImpl<Annexe, Long> implements AnnexeDemautRepository {

    public AnnexeDemautRepositoryImpl() {
        super(Annexe.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Annexe fetchByName(String annexeName) {
        List<?> resultList = this.getEntityManager()
                .createQuery("select o from " + entityClass.getSimpleName() + " as o where o.name = :name ")
                .setParameter("name", annexeName)
                .getResultList();
        return resultList != null && !resultList.isEmpty() ? (Annexe) resultList.get(0) : null;
    }
}
