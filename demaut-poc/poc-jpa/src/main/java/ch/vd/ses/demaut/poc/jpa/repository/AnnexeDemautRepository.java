package ch.vd.ses.demaut.poc.jpa.repository;

import ch.vd.ses.demaut.poc.jpa.entity.Annexe;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AnnexeDemautRepository extends DemautRepositoryImpl<Annexe, Long> {

    public AnnexeDemautRepository() {
        super(Annexe.class);
    }

    @Transactional(readOnly = true)
    public Annexe fetchByName(String annexeName) {
        List resultList = this.getEntityManager()
                .createQuery("select o from " + entityClass.getSimpleName() + " as o where o.name = :name ")
                .setParameter("name", annexeName)
                .getResultList();
        return resultList != null && !resultList.isEmpty() ? (Annexe) resultList.get(0) : null;
    }
}
