package ch.vd.demaut.domain.utilisateurs;

import ch.vd.demaut.commons.fk.FunctionalKeyAbstract;

public class UtilisateurFK extends FunctionalKeyAbstract<Utilisateur> {

    // ********************************************************* Fields
    private Login login;

    // ********************************************************* Constructor

    public UtilisateurFK(Utilisateur utilisateur) {
        super();
        this.login = utilisateur.getLogin();
    }

    // ********************************************************* Getters

    public Login getLogin() {
        return login;
    }

}
