package ch.vd.demaut.domain.demandeur.donneesProf.activites;

import ch.vd.demaut.commons.annotations.Entity;
import ch.vd.demaut.commons.vo.BaseValueObjectWithId;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.envisagee.ActiviteEnvisagee;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Représente une activité future prévue par le demandeur  
 *
 */
@Entity
public class ActiviteFuture extends BaseValueObjectWithId {

    // *********************************************** Fields

    private TypeActivite typeActivite;

    private Etablissement etablissement;

    private TypePratiqueLamal typePratiqueLamal;

    private ActiviteEnvisagee activiteEnvisagee;

    // *********************************************** Constructeur

    //Used only for JPA
    ActiviteFuture() {
    }

    public ActiviteFuture(TypeActivite typeActivite, Etablissement etablissement, TypePratiqueLamal typePratiqueLamal, ActiviteEnvisagee activiteEnvisagee) {
        this.typeActivite = typeActivite;
        this.etablissement = etablissement;
        this.typePratiqueLamal = typePratiqueLamal;
        this.activiteEnvisagee = activiteEnvisagee;
    }

    // *********************************************** Getters
    
    @NotNull
    @Valid
    public TypeActivite getTypeActivite() {
        return typeActivite;
    }
    
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

}
