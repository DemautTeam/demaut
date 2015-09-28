package ch.vd.demaut.services.demandes.autorisation;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.Profession;

public interface DemandeAutorisationService {

    /**
     * Initialise une demande d'autorisation à l'état Brouillon pour l'utilisateur donné
     *
     * @param login
     * @param profession
     * @return
     */
    DemandeAutorisation initialiserDemandeAutorisation(Profession profession);

    /**
     * Récupère une demande via sa référence <br/>
     * Renvoie exception si pas trouvée<br/>
     * @param referenceDeDemande
     * @return
     * @throw 
     */
    DemandeAutorisation recupererDemandeParReference(ReferenceDeDemande referenceDeDemande);

}

