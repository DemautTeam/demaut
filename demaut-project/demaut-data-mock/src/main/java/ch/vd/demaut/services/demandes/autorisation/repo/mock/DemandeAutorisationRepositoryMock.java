package ch.vd.demaut.services.demandes.autorisation.repo.mock;

import ch.vd.demaut.commons.repo.GenericRepository;
import ch.vd.demaut.commons.repo.mock.GenericFKARepositoryMock;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisationFK;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Mock du {@link DemandeAutorisationRepository}
 */
public class DemandeAutorisationRepositoryMock extends GenericFKARepositoryMock<DemandeAutorisation, DemandeAutorisationFK>
        implements DemandeAutorisationRepository, GenericRepository<DemandeAutorisation, Long> {

    private static Long idSequence = 0L;

    private static DemandeAutorisationRepositoryMock INSTANCE = null;

    public synchronized static DemandeAutorisationRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = mock(DemandeAutorisationRepositoryMock.class);
        }
        return INSTANCE;
    }

    @Override
    protected Long getNextID() {
        return ++idSequence;
    }

    @Override
    public DemandeAutorisation afficherUneDemandeAutorisation(String demandeReference) {
        //when()
        return null; // TODO
    }

    @Override
    public DemandeAutorisation sauverLaDemandeAutorisation(DemandeAutorisation demandeAutorisation) {
        return null; // TODO
    }
}
