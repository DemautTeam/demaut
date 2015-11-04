package ch.vd.demaut.domain.demandeur.donneesProf.activites;

import ch.vd.demaut.commons.annotations.Entity;
import ch.vd.demaut.commons.vo.BaseValueObjectWithId;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.envisagee.ActiviteEnvisagee;

import javax.validation.Valid;

@Entity
public class ActiviteFuture extends BaseValueObjectWithId {

    @Valid
    private ReferenceDeActivite referenceDeActivite;

    @Valid
    private TypeActivite typeActivite;

    @Valid
    private Etablissement etablissement;

    @Valid
    private TypePratiqueLamal typePratiqueLamal;

    @Valid
    private ActiviteEnvisagee activiteEnvisagee;

    public ActiviteFuture() {
    }

    public ActiviteFuture(ReferenceDeActivite referenceDeActivite, TypeActivite typeActivite, Etablissement etablissement, TypePratiqueLamal typePratiqueLamal, ActiviteEnvisagee activiteEnvisagee) {
        this.referenceDeActivite = referenceDeActivite;
        this.typeActivite = typeActivite;
        this.etablissement = etablissement;
        this.typePratiqueLamal = typePratiqueLamal;
        this.activiteEnvisagee = activiteEnvisagee;
    }

    public ReferenceDeActivite getReferenceDeActivite() {
        return referenceDeActivite;
    }

    public Etablissement getEtablissement() {
        return etablissement;
    }

    public TypeActivite getTypeActivite() {
        return typeActivite;
    }

    public TypePratiqueLamal getTypePratiqueLamal() {
        return typePratiqueLamal;
    }

    public ActiviteEnvisagee getActiviteEnvisagee() {
        return activiteEnvisagee;
    }

    public void setTypeActivite(TypeActivite typeActivite) {
        this.typeActivite = typeActivite;
    }
}
