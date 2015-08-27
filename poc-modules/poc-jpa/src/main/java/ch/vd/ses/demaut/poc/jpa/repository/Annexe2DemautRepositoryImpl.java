package ch.vd.ses.demaut.poc.jpa.repository;

import org.springframework.stereotype.Service;

import ch.vd.ses.demaut.poc.jpa.entity.Annexe3;

@Service
public class Annexe2DemautRepositoryImpl extends DemautRepositoryImpl<Annexe3, Long> implements Annexe2DemautRepository {

    public Annexe2DemautRepositoryImpl() {
        super(Annexe3.class);
    }

}
