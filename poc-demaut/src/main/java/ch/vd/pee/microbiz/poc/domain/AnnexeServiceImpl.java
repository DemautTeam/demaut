package ch.vd.pee.microbiz.poc.domain;

import ch.vd.pee.microbiz.poc.jpa.entity.Annexe;
import ch.vd.pee.microbiz.poc.jpa.repository.AnnexeDemautRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnexeServiceImpl implements AnnexeService {

    @Autowired
    private AnnexeDemautRepository annexeDemautRepository;

    @Override
    public List<Annexe> fetchAnnexes() {
        return annexeDemautRepository.findAll();
    }

    @Override
    public Annexe fetchAnnexeByName(String annexeName) {
        return annexeDemautRepository.fetchByName(annexeName);
    }

    @Override
    public boolean storeAnnexe(Annexe annexe) {
        Annexe savedAnnexe = annexeDemautRepository.save(annexe);
        return savedAnnexe != null && savedAnnexe.getId() != null;
    }
}
