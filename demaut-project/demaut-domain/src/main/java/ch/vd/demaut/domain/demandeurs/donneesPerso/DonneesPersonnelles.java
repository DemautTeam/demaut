package ch.vd.demaut.domain.demandeurs.donneesPerso;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;

@ValueObject
public class DonneesPersonnelles extends BaseValueObject {

    // ********************************************************* Fields
    final private Nom nom;

    final private Prenom prenom;

    final private NomDeCelibataire nomDeCelibataire;

    final private Genre genre;

    final private DateDeNaissance dateDeNaissance;

    final private Adresse adresse;

    final private Email email;

    final private NumeroTelephone telephonePrive;

    final private NumeroTelephone telephoneMobile;

    final private NumeroTelephone fax;


    // ********************************************************* Constructor

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
