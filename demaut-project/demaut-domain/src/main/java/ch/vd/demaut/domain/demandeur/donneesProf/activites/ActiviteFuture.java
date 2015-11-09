package ch.vd.demaut.domain.demandeur.donneesProf.activites;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import ch.vd.demaut.commons.annotations.Entity;
import ch.vd.demaut.commons.entities.EntityAvecOrdreFK;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.envisagee.ActiviteEnvisagee;

/**
 * Représente une activité future prévue par le demandeur  
 *
 */
@Entity
public class ActiviteFuture extends EntityAvecOrdreFK {

    // *********************************************** Fields

    private Etablissement etablissement;

    private TypePratiqueLamal typePratiqueLamal;

    private ActiviteEnvisagee activiteEnvisagee;

    // *********************************************** Constructeur

    //Used only for JPA
    protected ActiviteFuture() {
    }

    public ActiviteFuture(Etablissement etablissement, TypePratiqueLamal typePratiqueLamal, ActiviteEnvisagee activiteEnvisagee) {
        this.etablissement = etablissement;
        this.typePratiqueLamal = typePratiqueLamal;
        this.activiteEnvisagee = activiteEnvisagee;
    }
    
    // *********************************************** Getters
    
    @NotNull
    @Valid
    public Etablissement getEtablissement() {
        return etablissement;
    }

    @NotNull
    @Valid
    public TypePratiqueLamal getTypePratiqueLamal() {
        return typePratiqueLamal;
    }

    @NotNull
    @Valid
    public ActiviteEnvisagee getActiviteEnvisagee() {
        return activiteEnvisagee;
    }

    @Override
    public ActiviteFutureFK getFunctionalKey() {
        return new ActiviteFutureFK(this);
    }

}
