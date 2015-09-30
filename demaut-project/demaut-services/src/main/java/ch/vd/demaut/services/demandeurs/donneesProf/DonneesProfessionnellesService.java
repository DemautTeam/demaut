package ch.vd.demaut.services.demandeurs.donneesProf;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandeur.donneesProf.DonneesProfessionnelles;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.*;
import org.springframework.transaction.annotation.Transactional;

public interface DonneesProfessionnellesService {

    Profession afficherDonneesProfession(ReferenceDeDemande referenceDeDemande);

    DonneesProfessionnelles recupererDonneesProfessionnellesParReferenceDemande(ReferenceDeDemande referenceDeDemande);

    void ajouterUnDiplome(ReferenceDeDemande referenceDeDemande, ReferenceDeDiplome referenceDeDiplome, TypeDiplomeAccepte typeDiplomeAccepte,
                          TitreFormation titreFormation, DateObtention dateObtention, PaysObtention paysObtention, DateReconnaissance dateReconnaissance);

    void supprimerUnDiplome(ReferenceDeDemande referenceDeDemande, ReferenceDeDiplome referenceDeDiplome);
}
