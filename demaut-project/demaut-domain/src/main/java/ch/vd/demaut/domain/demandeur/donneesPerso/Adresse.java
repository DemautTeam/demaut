package ch.vd.demaut.domain.demandeur.donneesPerso;

import ch.vd.demaut.commons.vo.BaseValueObject;
import ch.vd.demaut.domain.demandeur.Pays;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class Adresse extends BaseValueObject {

    @NotNull
    private final String voie;

    private final String complement;

    @Valid
    private final NPA npa;

    @Valid
    private final Localite localite;

    @Valid
    private final Pays pays;

    public Adresse(String voie, String complement, Localite localite, NPA npa, Pays pays) {
        super();
        this.voie = voie;
        this.complement = complement;
        this.localite = localite;
        this.npa = npa;
        this.pays = pays;
    }

    public String getVoie() {
        return voie;
    }

    public Localite getLocalite() {
        return localite;
    }

    public NPA getNpa() {
        return npa;
    }

    public Pays getPays() {
        return pays;
    }

    public String getComplement() {
        return complement;
    }
}
