package ch.vd.demaut.data.dummy;

import org.springframework.stereotype.Repository;

import ch.vd.demaut.data.GenericRepositoryImpl;
import ch.vd.demaut.domain.dummy.DummyDemandeAut;
import ch.vd.demaut.domain.dummy.repo.DummyDemAutRepository;

@Repository
public class DummyDemAutRepositoryImpl extends GenericRepositoryImpl<DummyDemandeAut, Long>
        implements DummyDemAutRepository {

	public DummyDemAutRepositoryImpl() {
		super(DummyDemandeAut.class);
	}
}
