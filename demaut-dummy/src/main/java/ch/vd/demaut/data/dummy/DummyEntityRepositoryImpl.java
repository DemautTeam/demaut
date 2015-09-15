package ch.vd.demaut.data.dummy;

import org.springframework.stereotype.Repository;

import ch.vd.demaut.data.GenericRepositoryImpl;
import ch.vd.demaut.domain.dummy.DummyEntity;
import ch.vd.demaut.domain.dummy.repo.DummyEntityRepository;

@Repository
public class DummyEntityRepositoryImpl extends GenericRepositoryImpl<DummyEntity, Long>
        implements DummyEntityRepository {

	public DummyEntityRepositoryImpl() {
		super(DummyEntity.class);
	}
}
