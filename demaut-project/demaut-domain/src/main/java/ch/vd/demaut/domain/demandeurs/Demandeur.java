package ch.vd.demaut.domain.demandeurs;

import ch.vd.demaut.commons.annotations.Aggregate;
import ch.vd.demaut.commons.entities.EntityFunctionalKeyAware;
import ch.vd.demaut.domain.demandeurs.donneesProf.DonneesProfessionnelles;

@Aggregate
public class Demandeur extends EntityFunctionalKeyAware {

    // ********************************************************* Fields
    private Login login;

    private NomEtPrenomDemandeur nomsEtPrenoms;

    private DonneesProfessionnelles donneesProfessionnelles;

    // ********************************************************* Constructor
    public Demandeur(NomEtPrenomDemandeur nomsEtPrenoms, DonneesProfessionnelles donneesProfessionnelles) {
        super();
        this.nomsEtPrenoms = nomsEtPrenoms;
        this.donneesProfessionnelles = donneesProfessionnelles;
    }

    // ********************************************************* Getters
    public NomEtPrenomDemandeur getNomsEtPrenoms() {
        return nomsEtPrenoms;
    }

    public Login getLogin() {
        return login;
    }

    public DonneesProfessionnelles getDonneesProfessionnelles() {
        return donneesProfessionnelles;
    }

    // ********************************************************* Technical Methods
    @Override
    public DemandeurFK getFunctionalKey() {
        return new DemandeurFK(this);
    }

}
