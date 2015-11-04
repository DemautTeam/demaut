package ch.vd.demaut.domain.demandeur.donneesProf.activites;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;
import ch.vd.demaut.domain.demandeur.Localite;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@ValueObject
public class Etablissement extends BaseValueObject {

    @NotNull
    @Valid
    private Voie voie;

    @Valid
    private Complement complement;

    @NotNull
    @Valid
    private Localite localite;

    @NotNull
    @Valid
    private NPAProfessionnel npaProfessionnel;

    public Etablissement(){
    }

    public Etablissement(Voie voie, Complement complement, Localite localite, NPAProfessionnel npaProfessionnel) {
        this.voie = voie;
        this.complement = complement;
        this.localite = localite;
        this.npaProfessionnel = npaProfessionnel;
    }

    public Voie getVoie() {
        return voie;
    }

    public Complement getComplement() {
        return complement;
    }

    public NPAProfessionnel getNpaProfessionnel() {
        return npaProfessionnel;
    }

    public Localite getLocalite() {
        return localite;
    }

}
