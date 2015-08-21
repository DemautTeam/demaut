package ch.vd.demaut.commons.repo.mock;

import ch.vd.demaut.commons.entities.Entity;
import ch.vd.demaut.commons.repo.GenericRepository;
import ch.vd.demaut.commons.repo.RepositoryAvecSequence;

import java.io.Serializable;

/**
 * Implémentation mock du {@link GenericRepository}.
 *
 * @param <T>  l'{@link Entity} à gérer
 * @param <ID> l'identifiant de l'{@link Entity}
 */
public abstract class GenericRepositoryMockAvecSequence<T extends Entity<ID>, ID extends Serializable> extends
        GenericRepositoryMock<T, ID> implements RepositoryAvecSequence {

    Long numSequence = 0L;

    public Long incrementerProchainNumeroSequence() {
        return ++numSequence;
    }

    public Long getNumeroSequenceCourant() {
        return numSequence;
    }

    public void setNumeroSequenceCourant(Long numSequenceCourant) {
        numSequence = numSequenceCourant;
    }
}
