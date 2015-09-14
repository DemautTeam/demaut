package ch.vd.demaut.domain.dummy.repo;

import ch.vd.demaut.commons.annotations.Repository;
import ch.vd.demaut.commons.repo.GenericReadRepository;
import ch.vd.demaut.commons.repo.GenericRepository;
import ch.vd.demaut.domain.dummy.DummyDemandeAut;

@Repository
public interface DummyDemAutRepository extends GenericRepository<DummyDemandeAut, Long>, GenericReadRepository<DummyDemandeAut, Long> {

}

