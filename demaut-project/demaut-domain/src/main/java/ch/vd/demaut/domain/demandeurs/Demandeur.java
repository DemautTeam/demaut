package ch.vd.demaut.domain.demandeurs;

import ch.vd.demaut.commons.annotations.Aggregate;
import ch.vd.demaut.commons.entities.EntityFunctionalKeyAware;

@Aggregate
public class Demandeur extends EntityFunctionalKeyAware {

    // ********************************************************* Fields
    private Login login;

    private NomEtPrenomDemandeur nomsEtPrenoms;

    // ********************************************************* Constructors
    protected Demandeur(){
    }
    
    public Demandeur(NomEtPrenomDemandeur nomsEtPrenoms) {
        super();
        this.nomsEtPrenoms = nomsEtPrenoms;
    }
    
    public Demandeur(Login login) {
        super();
        this.login = login;
    }

    // ********************************************************* Getters
    public NomEtPrenomDemandeur getNomsEtPrenoms() {
        return nomsEtPrenoms;
    }

    public Login getLogin() {
        return login;
    }

    // ********************************************************* Technical Methods
    @Override
    public DemandeurFK getFunctionalKey() {
        return new DemandeurFK(this);
    }

}
