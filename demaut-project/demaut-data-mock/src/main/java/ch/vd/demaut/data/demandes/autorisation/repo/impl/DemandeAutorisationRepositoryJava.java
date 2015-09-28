package ch.vd.demaut.data.demandes.autorisation.repo.impl;

import ch.vd.demaut.commons.repo.mock.GenericFKARepositoryMock;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisationFK;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandes.autorisation.StatutDemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;
import ch.vd.demaut.domain.utilisateurs.Login;

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
    public DemandeAutorisation recupererDemandeParReference(ReferenceDeDemande referenceDeDemande) {
        return getByFK(new DemandeAutorisationFK(referenceDeDemande));
    }

    @Override
    public DemandeAutorisation recupererDemandeParProfessionStatut(Login login, Profession profession, StatutDemandeAutorisation statut) {
        return null;
    }

}
