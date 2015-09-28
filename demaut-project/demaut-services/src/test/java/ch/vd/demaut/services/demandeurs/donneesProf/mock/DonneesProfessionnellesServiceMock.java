package ch.vd.demaut.services.demandeurs.donneesProf.mock;

import static org.mockito.Mockito.mock;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.services.demandeurs.donneesProf.DonneesProfessionnellesService;

public class DonneesProfessionnellesServiceMock implements DonneesProfessionnellesService {

    private static DonneesProfessionnellesServiceMock INSTANCE = null;

    public synchronized static DonneesProfessionnellesServiceMock getInstance() {
        if (INSTANCE == null) {
            INSTANCE = mock(DonneesProfessionnellesServiceMock.class);
        }
        return INSTANCE;
    }

    @Override
    public Profession afficherDonneesProfession(ReferenceDeDemande referenceDeDemande) {
        // TODO
        return null;
    }

    @Override
    public ReferenceDeDemande renseignerDonneesProfession(ReferenceDeDemande referenceDeDemande, Profession profession, CodeGLN codeGLN) {
        // TODO
        return null;
    }
}
