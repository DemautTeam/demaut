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

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

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
        } catch (EntityNotFoundException | NoResultException e) {
            throw new ReferenceDemandeNotFoundException();
        }
    }

    @Override
    public DemandeAutorisation recupererBrouillon(Login login) {
        try {
            return demandeAutorisationRepository.recupererBrouillon(login);
        } catch (EntityNotFoundException e) {
            throw new DemandeNotFoundException();
        }
    }

    @Override
    public List<DemandeAutorisation> recupererListeBrouillons(Login login) {
        try {
            return demandeAutorisationRepository.recupererListeBrouillons(login);
        } catch (EntityNotFoundException e) {
            return new ArrayList<>();
        }
    }

    @Transactional
    @Override
    public void supprimerUnBrouillon(Login login, ReferenceDeDemande referenceDeDemande) {
        try {
            demandeAutorisationRepository.delete(demandeAutorisationRepository.recupererDemandeParReference(referenceDeDemande));
            //TODO trace login de modification dans DB
        } catch (EntityNotFoundException e) {
            throw new ReferenceDemandeNotFoundException();
        }
    }
}
