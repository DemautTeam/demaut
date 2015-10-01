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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class DemandeAutorisationServiceImpl implements DemandeAutorisationService {

    @Autowired
    private DemandeAutorisationRepository demandeAutorisationRepository;

    @Autowired
    private DemandeAutorisationFactory demandeAutorisationFactory;

    @Autowired
    private UtilisateurService utilisateurService;

    @Transactional
    @Override
    public DemandeAutorisation initialiserDemandeAutorisation(Profession profession) {
        Utilisateur utilisateurCourant = utilisateurService.recupererUtilisateurCourant();
        DemandeAutorisation nouvelleDemande = demandeAutorisationFactory.initierDemandeAutorisation(utilisateurCourant.getLogin(), profession);
        demandeAutorisationRepository.store(nouvelleDemande);
        return nouvelleDemande;
    }

    @Transactional
    @Override
    public DemandeAutorisation recupererDemandeParReference(ReferenceDeDemande referenceDeDemande) {
        try {
            return demandeAutorisationRepository.recupererDemandeParReference(referenceDeDemande);
        } catch (EntityNotFoundException e) {
            throw new ReferenceDemandeNotFoundException();
        }
    }
}
