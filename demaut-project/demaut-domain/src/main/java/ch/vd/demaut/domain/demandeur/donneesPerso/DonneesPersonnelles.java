package ch.vd.demaut.domain.demandeur.donneesPerso;

import ch.vd.demaut.commons.annotations.Entity;
import ch.vd.demaut.commons.entities.AbstractEntity;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
public class DonneesPersonnelles extends AbstractEntity {

    // ********************************************************* Fields
    @Valid
    final private Nom nom;

    @Valid
    final private Prenom prenom;

    final private NomDeCelibataire nomDeCelibataire;

    @NotNull
    final private Genre genre;

    @Valid
    final private DateDeNaissance dateDeNaissance;

    @Valid
    final private Adresse adresse;

    @Valid
    final private Email email;

    final private NumeroTelephone telephonePrive;

    final private NumeroTelephone telephoneMobile;

    final private NumeroTelephone fax;


    // ********************************************************* Constructor

    public DonneesPersonnelles() {
        super();
        this.nom = null;
        this.prenom = null;
        this.nomDeCelibataire = null;
        this.genre = null;
        this.dateDeNaissance = null;
        this.adresse = null;
        this.email = null;
        this.telephonePrive = null;
        this.telephoneMobile = null;
        this.fax = null;
    }

    //TODO: A supprimer
    public DonneesPersonnelles(Nom nom, Prenom prenom, NomDeCelibataire nomDeCelibataire, Genre genre, DateDeNaissance dateDeNaissance, Adresse adresse, Email email, NumeroTelephone telephonePrive, NumeroTelephone telephoneMobile, NumeroTelephone fax) {
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
}
