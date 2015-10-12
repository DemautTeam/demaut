package ch.vd.demaut.services.demandeurs.donneesProf.mock;

import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandeur.donneesProf.DonneesProfessionnelles;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.*;
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
    public Profession recupererDonneesProfession(Login login) {
        // TODO
        return null;
    }

    @Override
    public DonneesProfessionnelles recupererDonneesProfessionnelles(Login login) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void ajouterUnDiplome(Login login, ReferenceDeDiplome referenceDeDiplome, TypeDiplomeAccepte typeDiplomeAccepte, TitreFormation titreFormation, String complement, DateObtention dateObtention, PaysObtention paysObtention, DateReconnaissance dateReconnaissance) {
        // TODO
    }

    @Override
    public void supprimerUnDiplome(Login login, ReferenceDeDiplome referenceDeDiplome) {
        // TODO
    }
}
