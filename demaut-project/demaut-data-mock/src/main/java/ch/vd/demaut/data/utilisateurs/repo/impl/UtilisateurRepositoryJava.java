package ch.vd.demaut.data.utilisateurs.repo.impl;

import ch.vd.demaut.commons.repo.mock.GenericRepositoryMock;
import ch.vd.demaut.domain.utilisateurs.Utilisateur;
import ch.vd.demaut.domain.utilisateurs.UtilisateurRepository;

/**
 * Mock du {@link UtilisateurRepository}
 */
public class UtilisateurRepositoryJava extends GenericRepositoryMock<Utilisateur, Long>
        implements UtilisateurRepository {

    private static Long idSequence = 0L;

    @Override
    protected Long getNextID() {
        return ++idSequence;
    }

}
