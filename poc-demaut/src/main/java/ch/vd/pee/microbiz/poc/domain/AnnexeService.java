package ch.vd.pee.microbiz.poc.domain;

import ch.vd.pee.microbiz.poc.jpa.entity.Annexe;

import java.util.List;

public interface AnnexeService {

    List<Annexe> fetchAnnexes();

    Annexe fetchAnnexeByName(String annexeName);

    boolean storeAnnexe(Annexe annexe);
}
