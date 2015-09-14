package ch.vd.demaut.domain.dummy.repo;

import ch.vd.demaut.commons.annotations.Repository;
import ch.vd.demaut.commons.repo.GenericReadRepository;
import ch.vd.demaut.commons.repo.GenericRepository;
import ch.vd.demaut.domain.dummy.DummyEntity;

@Repository
public interface DummyEntityRepository extends GenericRepository<DummyEntity, Long>, GenericReadRepository<DummyEntity, Long> {

}

