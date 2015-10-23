package ch.vd.demaut.services.demandeurs.donneesProf;

import java.util.List;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandeur.Pays;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.domain.demandeur.donneesProf.DonneesProfessionnelles;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.DateObtention;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.DateReconnaissance;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.Diplome;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.ReferenceDeDiplome;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.TitreFormation;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.TypeDiplomeAccepte;
import ch.vd.demaut.domain.utilisateurs.Login;

public interface DonneesProfessionnellesService {

    List<Profession> listerProfessionsAvecCodeGlnObligatoire();

    Profession recupererProfessionDeDemande(Login login, ReferenceDeDemande referenceDeDemande);

    DonneesProfessionnelles recupererDonneesProfessionnelles(Login login, ReferenceDeDemande referenceDeDemande);

    void ajouterUnDiplome(Login login, ReferenceDeDemande referenceDeDemande, ReferenceDeDiplome referenceDeDiplome, TypeDiplomeAccepte typeDiplomeAccepte, TitreFormation titreFormation,
                          String complement, DateObtention dateObtention, Pays paysObtention, DateReconnaissance dateReconnaissance);

    void supprimerUnDiplome(Login login, ReferenceDeDemande referenceDeDemande, ReferenceDeDiplome referenceDeDiplome);

    void validerEtRenseignerCodeGLN(Login login, ReferenceDeDemande referenceDeDemande, CodeGLN codeGLN);

    List<Diplome> recupererDiplomesSaisis(Login login, ReferenceDeDemande referenceDeDemande);
}
