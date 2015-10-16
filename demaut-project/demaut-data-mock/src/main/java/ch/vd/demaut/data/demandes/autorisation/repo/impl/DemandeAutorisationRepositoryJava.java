package ch.vd.demaut.data.demandes.autorisation.repo.impl;

import ch.vd.demaut.commons.repo.mock.GenericFKARepositoryMock;
import ch.vd.demaut.domain.demandes.DemandeFK;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.StatutDemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;
import ch.vd.demaut.domain.exception.DemandeBrouillonExisteDejaException;
import ch.vd.demaut.domain.exception.DemandeNotFoundException;
import ch.vd.demaut.domain.utilisateurs.Login;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock du {@link DemandeAutorisationRepository}
 */
public class DemandeAutorisationRepositoryJava extends
        GenericFKARepositoryMock<DemandeAutorisation, DemandeFK<DemandeAutorisation>> implements DemandeAutorisationRepository {

    private static Long idSequence = 0L;

    @Override
    protected Long getNextID() {
        return ++idSequence;
    }

    @Override
    public DemandeAutorisation recupererDemandeParReference(ReferenceDeDemande referenceDeDemande) {
        DemandeFK<DemandeAutorisation> functionalKey = new DemandeFK<>(referenceDeDemande);
        return getByFK(functionalKey);
    }

    @Override
    public DemandeAutorisation recupererBrouillon(Login login) {
        DemandeAutorisation brouillon = trouverDemandeBrouillonParUtilisateur(login);
        if (brouillon == null) {
            throw new DemandeNotFoundException();
        }
        return null;
    }

    @Override
    public List<DemandeAutorisation> recupererListeBrouillons(Login login) {
        List<DemandeAutorisation> demandesBrouillons = new ArrayList<>();
        List<DemandeAutorisation> demandes = findAll();
        for (DemandeAutorisation demande : demandes) {
            if (demande.getLogin().equals(login)) {
                if (demande.getStatutDemandeAutorisation() == StatutDemandeAutorisation.Brouillon) {
                    demandesBrouillons.add(demande);
                }
            }
        }
        return demandesBrouillons;
    }

    @Override
    public boolean brouillonExiste(Login login) {
        DemandeAutorisation brouillon = trouverDemandeBrouillonParUtilisateur(login);
        return brouillon != null;
    }

    private DemandeAutorisation trouverDemandeBrouillonParUtilisateur(Login login) {
        DemandeAutorisation brouillon = null;
        List<DemandeAutorisation> demandes = findAll();
        for (DemandeAutorisation demande : demandes) {
            if (demande.getLogin().equals(login)) {
                if (demande.getStatutDemandeAutorisation() == StatutDemandeAutorisation.Brouillon) {
                    if (brouillon != null) {
                        throw new DemandeBrouillonExisteDejaException();
                    }
                    brouillon = demande;
                    break;
                }
            }
        }
        return brouillon;
    }
}
