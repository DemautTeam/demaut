package ch.vd.demaut.services.demandeurs.donneesProf;

import java.util.List;

import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandeur.donneesProf.DonneesProfessionnelles;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.*;
import ch.vd.demaut.domain.utilisateurs.Login;

public interface DonneesProfessionnellesService {

    List<Profession> listerProfessionsAvecCodeGlnObligatoire();

    Profession recupererDonneesProfession(Login login);

    DonneesProfessionnelles recupererDonneesProfessionnelles(Login login);

    void ajouterUnDiplome(Login login, ReferenceDeDiplome referenceDeDiplome, TypeDiplomeAccepte typeDiplomeAccepte, TitreFormation titreFormation,
                          String complement, DateObtention dateObtention, PaysObtention paysObtention, DateReconnaissance dateReconnaissance);

    void supprimerUnDiplome(Login login, ReferenceDeDiplome referenceDeDiplome);

}
