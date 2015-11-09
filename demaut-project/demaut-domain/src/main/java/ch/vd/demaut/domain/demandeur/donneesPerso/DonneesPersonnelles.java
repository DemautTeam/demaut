package ch.vd.demaut.domain.demandeur.donneesPerso;

import ch.vd.demaut.commons.annotations.Entity;
import ch.vd.demaut.commons.entities.AbstractEntity;
import ch.vd.demaut.domain.demandeur.Email;
import ch.vd.demaut.domain.demandeur.Pays;
import ch.vd.demaut.domain.demandeur.Telephone;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@AlternativePhone(prive = "telephonePrive", mobile = "telephoneMobile")
public class DonneesPersonnelles extends AbstractEntity {

    // ********************************************************* Fields
    private Nom nom;

    private Prenom prenom;

    private Nom nomDeCelibataire;

    private Genre genre;

    private DateDeNaissance dateDeNaissance;

    private Adresse adresse;

    private Pays nationalite;

    private Email email;

    private Telephone telephonePrive;

    private Telephone telephoneMobile;

    private Telephone fax;

    private Langue langue;

    private Permis permis;


    // ********************************************************* Constructor

    public DonneesPersonnelles() {
        super();
    }

    //TODO: A supprimer
    public DonneesPersonnelles(Nom nom, Prenom prenom, Nom nomDeCelibataire, Adresse adresse, Email email,
                               Telephone telephonePrive, Telephone telephoneMobile, Telephone fax, Genre genre,
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
    @NotNull
    @Valid
    public Nom getNom() {
        return nom;
    }

    @NotNull
    @Valid
    public Prenom getPrenom() {
        return prenom;
    }

    @Valid
    public Nom getNomDeCelibataire() {
        return nomDeCelibataire;
    }

    @NotNull
    @Valid
    public Genre getGenre() {
        return genre;
    }

    @Valid
    public DateDeNaissance getDateDeNaissance() {
        return dateDeNaissance;
    }

    @NotNull
    @Valid
    public Adresse getAdresse() {
        return adresse;
    }

    @Valid
    public Telephone getTelephonePrive() {
        return telephonePrive;
    }

    @Valid
    public Telephone getTelephoneMobile() {
        return telephoneMobile;
    }

    @Valid
    public Email getEmail() {
        return email;
    }

    @Valid
    public Telephone getFax() {
        return fax;
    }

    @NotNull
    @Valid
    public Pays getNationalite() {
        return nationalite;
    }

    @NotNull
    @Valid
    public Langue getLangue() {
        return langue;
    }

    @Valid
    public Permis getPermis() {
        return permis;
    }

    public boolean estEtranger() {
        return nationalite.estEtranger();
    }
    
    public void renseignerLesDonneesPersonnelles(Nom nom, Prenom prenom, Nom nomDeCelibataire, Adresse adresse, Email email,
                                                 Telephone telephonePrive, Telephone telephoneMobile, Telephone fax, Genre genre,
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
