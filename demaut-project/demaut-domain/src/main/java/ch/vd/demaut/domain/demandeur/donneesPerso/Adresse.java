package ch.vd.demaut.domain.demandeur.donneesPerso;

import ch.vd.demaut.commons.annotations.Entity;
import ch.vd.demaut.commons.entities.AbstractEntity;
import ch.vd.demaut.domain.demandeur.Pays;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
public class Adresse extends AbstractEntity {

    @NotNull
    private String voie;

    private String complement;

    @Valid
    private NPA npa;

    @Valid
    private Localite localite;

    @Valid
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
