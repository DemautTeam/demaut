package ch.vd.ses.demaut.dao.repository;

import ch.vd.ses.demaut.dao.entity.Annexe;
import org.springframework.stereotype.Component;

@Component
public class AnnexeDemautRepository extends DemautRepository<Annexe, Long> {

    public AnnexeDemautRepository() {
        super(Annexe.class);
    }
}
