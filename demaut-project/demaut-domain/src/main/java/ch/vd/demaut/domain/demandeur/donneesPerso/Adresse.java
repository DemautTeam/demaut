package ch.vd.demaut.domain.demandeur.donneesPerso;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;
import ch.vd.demaut.domain.demandeur.Localite;
import ch.vd.demaut.domain.demandeur.Pays;

@ValueObject
public class Adresse extends BaseValueObject {

    private String voie;

    private String complement;

    private NPA npa;

    private Localite localite;

    private Pays pays;

    public Adresse() {
    }

    public Adresse(String voie, String complement, Localite localite, NPA npa, Pays pays) {
        super();
        this.voie = voie;
        this.complement = complement;
        this.localite = localite;
        this.npa = npa;
        this.pays = pays;
    }

    @NotNull
    public String getVoie() {
        return voie;
    }

    @Valid
    public Localite getLocalite() {
        return localite;
    }

    @Valid
    public NPA getNpa() {
        return npa;
    }

    @Valid
    public Pays getPays() {
        return pays;
    }

    public String getComplement() {
        return complement;
    }
}
