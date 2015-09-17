package ch.vd.demaut.data.demandes.autorisation.repo.impl;

import ch.vd.demaut.commons.repo.mock.GenericFKARepositoryMock;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisationFK;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;

import static org.mockito.Mockito.mock;

/**
 * Mock du {@link DemandeAutorisationRepository}
 */
public class DemandeAutorisationRepositoryJava extends
        GenericFKARepositoryMock<DemandeAutorisation, DemandeAutorisationFK> implements DemandeAutorisationRepository {

    private static Long idSequence = 0L;

    @Override
    protected Long getNextID() {
        return ++idSequence;
    }

    @Override
    public DemandeAutorisation recupererDemandeParReference(ReferenceDeDemande ref) {
        DemandeAutorisation demandeAutorisation = getByFK(new DemandeAutorisationFK(ref));
        return demandeAutorisation;
    }

}
