package ch.vd.demaut.services.demandes.autorisation.service.mock;

import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;

public class DemandeAutorisationServiceMock implements DemandeAutorisationService {

    @Override
    public DemandeAutorisation initialiserDemandeAutorisation() {
        return new DemandeAutorisation();
    }

    @Override
    public DemandeAutorisation sauvegarderDemandeAutorisation(DemandeAutorisation demandeAutorisation) {
        return demandeAutorisation;
    }
}
