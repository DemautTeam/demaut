package ch.vd.ses.demaut.poc.domain;

import ch.vd.ses.demaut.poc.jpa.entity.Annexe;

import java.util.List;

public interface AnnexeService {

    List<Annexe> fetchAnnexes();

    Annexe fetchAnnexeByName(String annexeName);

    boolean storeAnnexe(Annexe annexe);
}
