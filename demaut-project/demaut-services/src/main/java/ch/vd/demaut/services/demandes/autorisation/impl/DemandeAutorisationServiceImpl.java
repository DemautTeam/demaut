package ch.vd.demaut.services.demandes.autorisation.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.transaction.annotation.Transactional;

import ch.vd.demaut.commons.exceptions.EntityNotFoundException;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisationFactory;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.domain.exception.ReferenceDemandeNotFoundException;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;

public class DemandeAutorisationServiceImpl implements DemandeAutorisationService {

    private DemandeAutorisationRepository demandeAutorisationRepository;

    private DemandeAutorisationFactory demandeAutorisationFactory;

    @Transactional
    @Override
    public DemandeAutorisation initialiserDemandeAutorisation(Profession profession, CodeGLN codeGLN, Login login) {

		DemandeAutorisation demandeAutorisation = demandeAutorisationFactory.initierDemandeAutorisation(login, profession, codeGLN);

        demandeAutorisationRepository.store(demandeAutorisation);
        return demandeAutorisation;
    }

    @Transactional(readOnly = true)
    @Override
    public DemandeAutorisation recupererDemandeParReference(ReferenceDeDemande referenceDeDemande) {
        try {
            return demandeAutorisationRepository.recupererDemandeParReference(referenceDeDemande);
        } catch (EntityNotFoundException | NoResultException e) {
            throw new ReferenceDemandeNotFoundException();
        }
    }

    @Transactional(readOnly = true)
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
    public void supprimerUnBrouillon(ReferenceDeDemande referenceDeDemande) {
        try {
            DemandeAutorisation demande = demandeAutorisationRepository.recupererDemandeParReference(referenceDeDemande);
            demandeAutorisationRepository.delete(demande);
            //TODO trace login de modification dans DB
        } catch (EntityNotFoundException e) {
            throw new ReferenceDemandeNotFoundException();
        }
    }

    public void setDemandeAutorisationRepository(DemandeAutorisationRepository demandeAutorisationRepository) {
        this.demandeAutorisationRepository = demandeAutorisationRepository;
    }

    public void setDemandeAutorisationFactory(DemandeAutorisationFactory demandeAutorisationFactory) {
        this.demandeAutorisationFactory = demandeAutorisationFactory;
    }

    // ********************************************************* Methodes
    // privees



}
