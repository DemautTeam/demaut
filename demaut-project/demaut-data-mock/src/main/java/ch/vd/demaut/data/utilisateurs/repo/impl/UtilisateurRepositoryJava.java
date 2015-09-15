package ch.vd.demaut.data.utilisateurs.repo.impl;

import ch.vd.demaut.commons.repo.mock.GenericRepositoryMock;
import ch.vd.demaut.domain.utilisateurs.Utilisateur;
import ch.vd.demaut.domain.utilisateurs.UtilisateurRepository;

/**
 * Mock du {@link DemandeurRepository}
 */
public class UtilisateurRepositoryJava extends GenericRepositoryMock<Utilisateur, Long>
		implements UtilisateurRepository {

	private static Long idSequence = 0L;

	private static UtilisateurRepositoryJava INSTANCE = null;

	public synchronized static UtilisateurRepository getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new UtilisateurRepositoryJava();
		}
		return INSTANCE;
	}

	@Override
	protected Long getNextID() {
		return ++idSequence;
	}

}
