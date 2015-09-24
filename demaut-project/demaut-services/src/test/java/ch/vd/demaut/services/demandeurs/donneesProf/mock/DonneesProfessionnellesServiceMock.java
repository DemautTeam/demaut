package ch.vd.demaut.services.demandeurs.donneesProf.mock;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.ProfessionDeLaSante;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.services.demandeurs.donneesProf.DonneesProfessionnellesService;

import static org.mockito.Mockito.mock;

public class DonneesProfessionnellesServiceMock implements DonneesProfessionnellesService {

    private static DonneesProfessionnellesServiceMock INSTANCE = null;

    public synchronized static DonneesProfessionnellesServiceMock getInstance() {
        if (INSTANCE == null) {
            INSTANCE = mock(DonneesProfessionnellesServiceMock.class);
        }
        return INSTANCE;
    }

    @Override
    public ProfessionDeLaSante afficherDonneesProfession(ReferenceDeDemande referenceDeDemande) {
        // TODO
        return null;
    }

    @Override
    public ReferenceDeDemande renseignerDonneesProfession(Login login, ReferenceDeDemande referenceDeDemande, ProfessionDeLaSante professionDeLaSante, CodeGLN codeGLN) {
        // TODO
        return null;
    }
}
