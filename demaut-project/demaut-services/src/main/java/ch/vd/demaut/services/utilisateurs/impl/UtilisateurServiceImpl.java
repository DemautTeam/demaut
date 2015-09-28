package ch.vd.demaut.services.utilisateurs.impl;

import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.domain.utilisateurs.Utilisateur;
import ch.vd.demaut.domain.utilisateurs.UtilisateurService;

public class UtilisateurServiceImpl implements UtilisateurService {

    @Override
    public Utilisateur recupererUtilisateurCourant() {
        //TODO: Changer pour retourner le VRAI utilisateur courant (via IAM).
        //Utiliser pattens ADAPTER/FACADE pour l'impléementation, ainsi qu'un Mock du service 

        Login login = new Login("admin@admin");

        return new Utilisateur(login);
    }

}
