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

    private Pays nationalite;

    @Valid
    private Email email;

    private NumeroTelephone telephonePrive;

    private NumeroTelephone telephoneMobile;

    private NumeroTelephone fax;


    // ********************************************************* Constructor

    public DonneesPersonnelles() {
        super();
    }

    //TODO: A supprimer
    public DonneesPersonnelles(Nom nom, Prenom prenom, NomDeCelibataire nomDeCelibataire, Genre genre, DateDeNaissance dateDeNaissance, Adresse adresse,
                               Pays nationalite, Email email, NumeroTelephone telephonePrive, NumeroTelephone telephoneMobile, NumeroTelephone fax) {
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
}
