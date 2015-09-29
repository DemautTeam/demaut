package ch.vd.demaut.services.demandes.autorisation.impl;

import ch.vd.demaut.commons.exceptions.EntityNotFoundException;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisationFactory;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;
import ch.vd.demaut.domain.exception.ReferenceDemandeNotFoundException;
import ch.vd.demaut.domain.utilisateurs.Utilisateur;
import ch.vd.demaut.domain.utilisateurs.UtilisateurService;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

public class DemandeAutorisationServiceImpl implements DemandeAutorisationService {

    @Inject
    private DemandeAutorisationRepository demandeAutorisationRepository;

    @Inject
    private DemandeAutorisationFactory demandeAutorisationFactory;

    @Inject
    private UtilisateurService utilisateurService;

    @Transactional
    @Override
    public DemandeAutorisation initialiserDemandeAutorisation(Profession profession) {
        Utilisateur utilisateurCourant = utilisateurService.recupererUtilisateurCourant();
        DemandeAutorisation nouvelleDemande = demandeAutorisationFactory.initierDemandeAutorisation(utilisateurCourant.getLogin(), profession, null);
        demandeAutorisationRepository.store(nouvelleDemande);
        return nouvelleDemande;
    }

    @Transactional(readOnly = true)
    @Override
    public DemandeAutorisation recupererDemandeParReference(ReferenceDeDemande referenceDeDemande) {
        try {
            return demandeAutorisationRepository.recupererDemandeParReference(referenceDeDemande);
        } catch (EntityNotFoundException e) {
            throw new ReferenceDemandeNotFoundException();
        }
    }
}
