package ch.vd.demaut.domain.demandeur.donneesProf.activites;

import ch.vd.demaut.commons.annotations.Entity;
import ch.vd.demaut.commons.vo.BaseValueObjectWithId;

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

    public ActiviteFuture() {
    }

    public ActiviteFuture(ReferenceDeActivite referenceDeActivite, TypeActivite typeActivite, Etablissement etablissement, TypePratiqueLamal typePratiqueLamal) {
        this.referenceDeActivite = referenceDeActivite;
        this.typeActivite = typeActivite;
        this.etablissement = etablissement;
        this.typePratiqueLamal = typePratiqueLamal;
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
}
