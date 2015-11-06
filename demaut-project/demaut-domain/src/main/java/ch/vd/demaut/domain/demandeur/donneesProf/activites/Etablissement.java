package ch.vd.demaut.domain.demandeur.donneesProf.activites;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;
import ch.vd.demaut.domain.demandeur.Fax;
import ch.vd.demaut.domain.demandeur.Localite;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.etablissement.TelephoneMobile;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.etablissement.TelephonePrive;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

//TOOD : Renommer Etablissement en Adresse (et AdresseSuisse) & Ajouter JavaDoc
@ValueObject
public class Etablissement extends BaseValueObject {

    // ********************************************************* Fields
    private Voie voie;

    private Complement complement;

    private Localite localite;

    private NPAProfessionnel npaProfessionnel;

    private TelephonePrive telephonePrive;

    private TelephoneMobile telephoneMobile;

    private Fax fax;

    // ********************************************************* Constructors
    //For JPA usage only
    Etablissement(){
    }

    public Etablissement(Voie voie, Complement complement, Localite localite, NPAProfessionnel npaProfessionnel, TelephonePrive telephonePrive, TelephoneMobile telephoneMobile, Fax fax) {
        this.voie = voie;
        this.complement = complement;
        this.localite = localite;
        this.npaProfessionnel = npaProfessionnel;
        this.telephonePrive = telephonePrive;
        this.telephoneMobile = telephoneMobile;
        this.fax = fax;
    }

    // ********************************************************* Getters & Contraintes

    @NotNull
    @Valid
    public Voie getVoie() {
        return voie;
    }

    @Valid
    public Complement getComplement() {
        return complement;
    }

    @NotNull
    @Valid
    public NPAProfessionnel getNpaProfessionnel() {
        return npaProfessionnel;
    }

    @NotNull
    @Valid
    public Localite getLocalite() {
        return localite;
    }

    @NotNull
    @Valid
    public TelephonePrive getTelephonePrive() {
        return telephonePrive;
    }

    @Valid
    public TelephoneMobile getTelephoneMobile() {
        return telephoneMobile;
    }

    @Valid
    public Fax getFax() {
        return fax;
    }
}
