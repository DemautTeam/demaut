package ch.vd.demaut.domain.utilisateurs;

import ch.vd.demaut.commons.annotations.Aggregate;
import ch.vd.demaut.commons.entities.EntityFunctionalKeyAware;

@Aggregate
public class Utilisateur extends EntityFunctionalKeyAware {

    // ********************************************************* Fields
    private Login login;

    // ********************************************************* Constructors
    protected Utilisateur() {
    }

    public Utilisateur(Login login) {
        super();
        this.login = login;
    }

    // ********************************************************* Getters
    public Login getLogin() {
        return login;
    }

    // ********************************************************* Technical Methods
    @Override
    public UtilisateurFK getFunctionalKey() {
        return new UtilisateurFK(this);
    }

}
