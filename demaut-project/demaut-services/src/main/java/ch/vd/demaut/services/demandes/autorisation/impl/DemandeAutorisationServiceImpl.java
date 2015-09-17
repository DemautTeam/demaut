package ch.vd.demaut.services.demandes.autorisation.impl;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisationFactory;
import ch.vd.demaut.domain.demandes.autorisation.ProfessionDeLaSante;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

public class DemandeAutorisationServiceImpl implements DemandeAutorisationService {

    @Inject
    private DemandeAutorisationRepository demandeAutorisationRepository;

    @Inject
    private DemandeAutorisationFactory demandeAutorisationFactory;

    @Transactional
    @Override
    public DemandeAutorisation initialiserDemandeAutorisation(Login login, ProfessionDeLaSante professionDeLaSante) {
        DemandeAutorisation demandeAutorisation = demandeAutorisationFactory.initierDemandeAutorisation(login, professionDeLaSante, null);
        demandeAutorisationRepository.store(demandeAutorisation);
        return demandeAutorisation;
    }

    @Override
    public DemandeAutorisation recupererDemandeParReference(ReferenceDeDemande referenceDeDemande) {
        return demandeAutorisationRepository.recupererDemandeParReference(referenceDeDemande);
    }

}
