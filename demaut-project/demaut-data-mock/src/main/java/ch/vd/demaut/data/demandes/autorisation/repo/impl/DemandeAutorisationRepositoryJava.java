package ch.vd.demaut.data.demandes.autorisation.repo.impl;

import ch.vd.demaut.commons.repo.mock.GenericFKARepositoryMock;
import ch.vd.demaut.domain.demandes.DemandeFK;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.StatutDemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;
import ch.vd.demaut.domain.utilisateurs.Utilisateur;

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
        DemandeFK<DemandeAutorisation> functionalKey = new DemandeFK<DemandeAutorisation>(referenceDeDemande);
        return getByFK(functionalKey);
    }

    @Override
    public DemandeAutorisation trouverDemandeEnCoursDeSaisieDunUtilisateur(Utilisateur utilisateur) {
        List<DemandeAutorisation> demandes = findAll();
        for (DemandeAutorisation demande : demandes) {
            if (demande.getLogin().equals(utilisateur.getLogin())) {
                if (demande.getStatutDemandeAutorisation() == StatutDemandeAutorisation.Brouillon) {
                    return demande;
                }
            }
        }
        return null;
    }


}
