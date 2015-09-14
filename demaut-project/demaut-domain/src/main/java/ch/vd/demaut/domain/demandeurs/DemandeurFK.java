package ch.vd.demaut.domain.demandeurs;

import ch.vd.demaut.commons.fk.FunctionalKeyAbstract;

public class DemandeurFK extends FunctionalKeyAbstract<Demandeur> {

    // ********************************************************* Fields
    private Login login;

    // ********************************************************* Constructor

    public DemandeurFK(Demandeur demandeur) {
        super();
        this.login = demandeur.getLogin();
    }

    // ********************************************************* Getters

    public Login getLogin() {
		return login;
	}

}
