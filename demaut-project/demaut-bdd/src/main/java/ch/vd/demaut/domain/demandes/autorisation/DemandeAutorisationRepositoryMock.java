package ch.vd.demaut.domain.demandes.autorisation;

import ch.vd.demaut.commons.repo.GenericRepository;
import ch.vd.demaut.commons.test.repo.GenericFKARepositoryMock;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;

/**
 * Mock du {@link DemandeAutorisationRepository}
 */
public class DemandeAutorisationRepositoryMock extends GenericFKARepositoryMock<DemandeAutorisation, DemandeAutorisationFK>
		implements DemandeAutorisationRepository, GenericRepository<DemandeAutorisation, Long> {

	private static Long idSequence = 0L;

	private static DemandeAutorisationRepositoryMock INSTANCE = null;

	public synchronized static DemandeAutorisationRepository getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DemandeAutorisationRepositoryMock();
		}
		return INSTANCE;
	}

	@Override
	protected Long getNextID() {
		return ++idSequence;
	}

}
