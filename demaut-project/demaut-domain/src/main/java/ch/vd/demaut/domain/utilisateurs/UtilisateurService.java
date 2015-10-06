package ch.vd.demaut.domain.utilisateurs;

import ch.vd.demaut.domain.exception.UtilisateurNotFoundException;

public interface UtilisateurService {

    /**
     * Récupère l'utilisateur courant (i.e. connecté et identifié)
     *
     * @return
     * @throws UtilisateurNotFoundException
     */
    Utilisateur recupererUtilisateurCourant();
}
