package ch.vd.demaut.domain.utilisateurs;

public interface UtilisateurService {

    /**
     * Récupère l'utilisateur courant (i.e. connecté et identifié)
     * @return
     * @throws AucunUtilisateurCourantException 
     */
    Utilisateur recupererUtilisateurCourant();
}
