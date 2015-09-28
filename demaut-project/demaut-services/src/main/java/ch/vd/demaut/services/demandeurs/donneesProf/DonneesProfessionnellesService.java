package ch.vd.demaut.services.demandeurs.donneesProf;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.domain.demandeur.donneesProf.DonneesProfessionnelles;

public interface DonneesProfessionnellesService {

    Profession afficherDonneesProfession(ReferenceDeDemande referenceDeDemande);

    DonneesProfessionnelles recupererDonneesProfessionnellesParReferenceDemande(ReferenceDeDemande referenceDeDemande);

    ReferenceDeDemande renseignerDonneesProfession(ReferenceDeDemande referenceDeDemande, Profession profession, CodeGLN codeGLN);
}
