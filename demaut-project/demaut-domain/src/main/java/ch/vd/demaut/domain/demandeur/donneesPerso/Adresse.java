package ch.vd.demaut.domain.demandeur.donneesPerso;

import ch.vd.demaut.commons.annotations.Entity;
import ch.vd.demaut.commons.entities.AbstractEntity;
import ch.vd.demaut.domain.demandeur.Localite;
import ch.vd.demaut.domain.demandeur.Pays;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

//TODO cette classe est vraiment une entité? Cette classe pourrait être reutilisé par "Activités/futures/Etablissement"
@Entity
public class Adresse extends AbstractEntity {

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
