package ch.vd.demaut.services.demandes.autorisation;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;

public interface DemandeAutorisationService {

    /**
     * Initialise une demande d'autorisation à l'état Brouillon pour l'utilisateur donné
     *
     * @param profession Profession
     * @return DemandeAutorisation
     */
    DemandeAutorisation initialiserDemandeAutorisation(Profession profession, CodeGLN codeGLN);

    /**
     * Récupère une demande via sa référence <br/>
     * Renvoie exception si pas trouvée<br/>
     *
     * @param referenceDeDemande ReferenceDeDemande
     * @return DemandeAutorisation
     */
    DemandeAutorisation recupererDemandeParReference(ReferenceDeDemande referenceDeDemande);

}

