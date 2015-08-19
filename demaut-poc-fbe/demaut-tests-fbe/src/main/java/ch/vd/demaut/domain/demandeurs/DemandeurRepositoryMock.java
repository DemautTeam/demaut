package ch.vd.demaut.domain.demandeurs;

import ch.vd.demaut.commons.exceptions.NotYetImplementedException;
import ch.vd.demaut.commons.repo.GenericRepository;
import ch.vd.demaut.commons.test.repo.GenericRepositoryMock;

/**
 * Mock du {@link DemandeurRepository}
 */
public class DemandeurRepositoryMock extends GenericRepositoryMock<Demandeur, Long> implements DemandeurRepository, GenericRepository<Demandeur, Long> {

    private static Long idSequence = 0L;

    private static DemandeurRepositoryMock INSTANCE = null;

    public synchronized static DemandeurRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DemandeurRepositoryMock();
        }
        return INSTANCE;
    }

    @Override
    protected Long getNextID() {
        return ++idSequence;
    }

    @Override
    public Demandeur chercherUnDemandeur(String nom, String prenom) {
        throw new NotYetImplementedException();
    }
}
