package ch.vd.demaut.domain.demandeur.donneesProf.activites;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;
import ch.vd.demaut.domain.demandeur.Email;
import ch.vd.demaut.domain.demandeur.Localite;
import ch.vd.demaut.domain.demandeur.Telephone;
import ch.vd.demaut.domain.demandeur.donneesPerso.Nom;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

//TODO : Renommer Etablissement en Adresse (et AdresseSuisse) & Ajouter JavaDoc
@ValueObject
public class Etablissement extends BaseValueObject {

    // ********************************************************* Fields

    private Nom nomEtablissement;

    // TODO refactorer les champs en classe Adresse
    private Voie voie;

    private Complement complement;

    private Localite localite;

    private NPAProfessionnel npaProfessionnel;

    private Telephone telephoneProf;

    private Telephone telephoneMobile;

    private Telephone fax;

    private Email email;

    private SiteInternet siteInternet;


    // ********************************************************* Constructors
    //For JPA usage only
    Etablissement(){
    }

    public Etablissement(Nom nomEtablissement, Voie voie, Complement complement, Localite localite, NPAProfessionnel npaProfessionnel, Telephone telephoneProf, Telephone telephoneMobile, Telephone fax, Email email, SiteInternet siteInternet) {
        this.nomEtablissement = nomEtablissement;
        this.voie = voie;
        this.complement = complement;
        this.localite = localite;
        this.npaProfessionnel = npaProfessionnel;
        this.telephoneProf = telephoneProf;
        this.telephoneMobile = telephoneMobile;
        this.fax = fax;
        this.email = email;
        this.siteInternet = siteInternet;
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
    public Telephone getTelephoneProf() {
        return telephoneProf;
    }

    @Valid
    public Telephone getTelephoneMobile() {
        return telephoneMobile;
    }

    @Valid
    public Telephone getFax() {
        return fax;
    }

    @NotNull
    @Valid
    public Email getEmail() {
        return email;
    }

    @NotNull
    @Valid
    public Nom getNomEtablissement() {
        return nomEtablissement;
    }

    @Valid
    public SiteInternet getSiteInternet() {
        return siteInternet;
    }
}
