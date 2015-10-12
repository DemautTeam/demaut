package ch.vd.demaut.domain.demandeur.donneesPerso;

import ch.vd.demaut.commons.annotations.Entity;
import ch.vd.demaut.commons.entities.AbstractEntity;
import ch.vd.demaut.domain.demandeur.Pays;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
public class DonneesPersonnelles extends AbstractEntity {

    // ********************************************************* Fields
    @Valid
    private Nom nom;

    @Valid
    private Prenom prenom;

    private NomDeCelibataire nomDeCelibataire;

    @NotNull
    private Genre genre;

    @Valid
    private DateDeNaissance dateDeNaissance;

    @Valid
    private Adresse adresse;

    @NotNull
    private Pays nationalite;

    @Valid
    private Email email;

    private NumeroTelephone telephonePrive;

    private NumeroTelephone telephoneMobile;

    private NumeroTelephone fax;

    @NotNull
    private Langue langue;

    private Permis permis;


    // ********************************************************* Constructor

    public DonneesPersonnelles() {
        super();
    }

    //TODO: A supprimer
    public DonneesPersonnelles(Nom nom, Prenom prenom, NomDeCelibataire nomDeCelibataire, Adresse adresse, Email email,
                               NumeroTelephone telephonePrive, NumeroTelephone telephoneMobile, NumeroTelephone fax, Genre genre,
                               DateDeNaissance dateDeNaissance, Pays nationalite, Langue langue, Permis permis) {
        super();
        this.nom = nom;
        this.prenom = prenom;
        this.nomDeCelibataire = nomDeCelibataire;
        this.genre = genre;
        this.dateDeNaissance = dateDeNaissance;
        this.adresse = adresse;
        this.email = email;
        this.telephonePrive = telephonePrive;
        this.telephoneMobile = telephoneMobile;
        this.fax = fax;
        this.nationalite = nationalite;
        this.langue = langue;
        this.permis = permis;
    }

    // ********************************************************* Getters
    public Nom getNom() {
        return nom;
    }

    public Prenom getPrenom() {
        return prenom;
    }

    public NomDeCelibataire getNomDeCelibataire() {
        return nomDeCelibataire;
    }

    public Genre getGenre() {
        return genre;
    }

    public DateDeNaissance getDateDeNaissance() {
        return dateDeNaissance;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public NumeroTelephone getTelephonePrive() {
        return telephonePrive;
    }

    public NumeroTelephone getTelephoneMobile() {
        return telephoneMobile;
    }

    public Email getEmail() {
        return email;
    }

    public NumeroTelephone getFax() {
        return fax;
    }

    public Pays getNationalite() {
        return nationalite;
    }

    public Langue getLangue() {
        return langue;
    }

    public Permis getPermis() {
        return permis;
    }

    public void renseignerLesDonneesPersonnelles(Nom nom, Prenom prenom, NomDeCelibataire nomDeCelibataire, Adresse adresse, Email email,
                                                 NumeroTelephone telephonePrive, NumeroTelephone telephoneMobile, NumeroTelephone fax, Genre genre,
                                                 DateDeNaissance dateDeNaissance, Pays nationalite, Langue langue, Permis permis) {
        this.nom = nom;
        this.prenom = prenom;
        this.nomDeCelibataire = nomDeCelibataire;
        this.adresse = adresse;
        this.email = email;
        this.telephonePrive = telephonePrive;
        this.telephoneMobile = telephoneMobile;
        this.fax = fax;
        this.genre = genre;
        this.dateDeNaissance = dateDeNaissance;
        this.nationalite = nationalite;
        this.langue = langue;
        this.permis = permis;
    }
}
