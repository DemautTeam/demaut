package ch.vd.demaut.services.demandeurs.donneesProf;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;

public interface DonneesProfessionnellesService {

    Profession afficherDonneesProfession(ReferenceDeDemande referenceDeDemande);

    ReferenceDeDemande renseignerDonneesProfession(ReferenceDeDemande referenceDeDemande, Profession profession, CodeGLN codeGLN);
}
