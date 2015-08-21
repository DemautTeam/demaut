package ch.vd.demaut.services.demandes.autorisation.impl;

import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.factory.DemandeAutorisationFactory;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
import org.springframework.beans.factory.annotation.Autowired;

public class DemandeAutorisationServiceImpl implements DemandeAutorisationService {

    @Autowired
    private DemandeAutorisationRepository demandeAutorisationRepository;

    private DemandeAutorisationFactory demandeAutorisationFactory = DemandeAutorisationFactory.getInstance();

    @Override
    public DemandeAutorisation initialiserDemandeAutorisation() {
        return demandeAutorisationFactory.inititierDemandeAutorisation();
    }

    @Override
    public DemandeAutorisation suavegarderDemandeAutorisation(DemandeAutorisation demandeAutorisation) {
        return demandeAutorisationRepository.store(demandeAutorisation);
    }
}
