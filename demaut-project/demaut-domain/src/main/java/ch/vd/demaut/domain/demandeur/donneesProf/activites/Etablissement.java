package ch.vd.demaut.domain.demandeur.donneesProf.activites;

import ch.vd.demaut.commons.annotations.Entity;
import ch.vd.demaut.commons.vo.BaseValueObjectWithId;
import ch.vd.demaut.domain.demandeur.Localite;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
public class Etablissement extends BaseValueObjectWithId {

    @NotNull
    @Valid
    private Voie voie;

    private Complement complement;

    @NotNull
    @Valid
    private Localite localite;

    @NotNull
    @Valid
    private NPAProfessionnel npaProfessionnel;

    public Etablissement(){
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
