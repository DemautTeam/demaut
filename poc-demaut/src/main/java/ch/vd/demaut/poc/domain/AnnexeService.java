package ch.vd.demaut.poc.domain;

import ch.vd.demaut.poc.jpa.entity.Annexe;

import java.util.List;

public interface AnnexeService {

    List<Annexe> fetchAnnexes();

    Annexe fetchAnnexeByName(String annexeName);

    boolean storeAnnexe(Annexe annexe);
}
