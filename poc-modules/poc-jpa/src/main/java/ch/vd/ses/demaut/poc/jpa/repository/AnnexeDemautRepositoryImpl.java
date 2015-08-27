package ch.vd.ses.demaut.poc.jpa.repository;

import org.springframework.stereotype.Service;

import ch.vd.ses.demaut.poc.jpa.entity.Annexe;

@Service
public class AnnexeDemautRepositoryImpl extends DemautRepositoryImpl<Annexe, Long> implements AnnexeDemautRepository {

    public AnnexeDemautRepositoryImpl() {
        super(Annexe.class);
    }

}
