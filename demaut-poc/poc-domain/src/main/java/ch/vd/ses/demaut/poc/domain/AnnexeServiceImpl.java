package ch.vd.ses.demaut.poc.domain;

import ch.vd.ses.demaut.poc.jpa.entity.Annexe;
import ch.vd.ses.demaut.poc.jpa.repository.AnnexeDemautRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnexeServiceImpl implements AnnexeService {

    @Autowired
    private AnnexeDemautRepositoryImpl annexeDemautRepositoryImpl;

    @Override
    public List<Annexe> fetchAnnexes() {
        return annexeDemautRepositoryImpl.findAll();
    }

    @Override
    public Annexe fetchAnnexeByName(String annexeName) {
        return annexeDemautRepositoryImpl.fetchByName(annexeName);
    }

    @Override
    public boolean storeAnnexe(Annexe annexe) {
        Annexe savedAnnexe = annexeDemautRepositoryImpl.save(annexe);
        return savedAnnexe != null && savedAnnexe.getId() != null;
    }
}
