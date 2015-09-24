package ch.vd.demaut.services.demandeurs.donneesProf;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.ProfessionDeLaSante;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.domain.utilisateurs.Login;

public interface DonneesProfessionnellesService {

    ProfessionDeLaSante afficherDonneesProfession(ReferenceDeDemande referenceDeDemande);

    ReferenceDeDemande renseignerDonneesProfession(Login login, ReferenceDeDemande referenceDeDemande, ProfessionDeLaSante professionDeLaSante, CodeGLN codeGLN);
}
