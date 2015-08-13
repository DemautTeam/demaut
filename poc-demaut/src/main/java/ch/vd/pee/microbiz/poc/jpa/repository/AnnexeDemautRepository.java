package ch.vd.pee.microbiz.poc.jpa.repository;

import ch.vd.pee.microbiz.poc.jpa.entity.Annexe;
import org.springframework.transaction.annotation.Transactional;

/**
 * Interface du repo des Annexes
 *
 * Created by mourad on 14.07.15.
 */
public interface AnnexeDemautRepository extends DemautRepository<Annexe, Long> {
    @Transactional(readOnly = true)
    Annexe fetchByName(String annexeName);
}
