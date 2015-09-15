package ch.vd.demaut.services.demandes.autorisation.mock;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.ProfessionDeLaSante;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;

import static org.mockito.Mockito.mock;

public class DemandeAutorisationServiceMock implements DemandeAutorisationService {

    private static DemandeAutorisationServiceMock INSTANCE = null;

    public synchronized static DemandeAutorisationServiceMock getInstance() {
        if (INSTANCE == null) {
            INSTANCE = mock(DemandeAutorisationServiceMock.class);
        }
        return INSTANCE;
    }

    @Override
    public DemandeAutorisation initialiserDemandeAutorisation(Login login, ProfessionDeLaSante professionDeLaSante) {
        // TODO
        return null;
    }

    @Override
    public DemandeAutorisation recupererDemandeParReference(ReferenceDeDemande referenceDeDemande) {
        // TODO
        return null;
    }
}
