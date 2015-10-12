package ch.vd.demaut.services.demandes.autorisation.impl;

import ch.vd.demaut.commons.exceptions.EntityNotFoundException;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisationFactory;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.domain.exception.DemandeNotFoundException;
import ch.vd.demaut.domain.exception.ReferenceDemandeNotFoundException;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class DemandeAutorisationServiceImpl implements DemandeAutorisationService {

    @Autowired
    private DemandeAutorisationRepository demandeAutorisationRepository;

    @Autowired
    private DemandeAutorisationFactory demandeAutorisationFactory;

    @Transactional
    @Override
    public DemandeAutorisation initialiserDemandeAutorisation(Profession profession, CodeGLN codeGLN, Login login) {
        
        DemandeAutorisation demandeAutorisation = demandeAutorisationFactory.initierDemandeAutorisation(login, profession, codeGLN);
        
        demandeAutorisationRepository.store(demandeAutorisation);

        return demandeAutorisation;
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

    @Override
    public DemandeAutorisation trouverDemandeBrouillonParUtilisateur(Login login) {
        try {
            return demandeAutorisationRepository.trouverDemandeBrouillonParUtilisateur(login);
        } catch (EntityNotFoundException e) {
            throw new DemandeNotFoundException();
        }
    }
}
