package ch.vd.demaut.services.demandeurs.donneesPerso;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandeur.Email;
import ch.vd.demaut.domain.demandeur.Pays;
import ch.vd.demaut.domain.demandeur.donneesPerso.*;
import ch.vd.demaut.domain.demandeur.Telephone;
import ch.vd.demaut.domain.utilisateurs.Login;

public interface DonneesPersonnellesService {

    DonneesPersonnelles recupererDonneesPersonnelles(Login login, ReferenceDeDemande referenceDeDemande);

    void renseignerLesDonneesPersonnelles(Login login, ReferenceDeDemande referenceDeDemande,
                                          Nom nom, Prenom prenom, NomDeCelibataire nomDeCelibataire, Adresse adresse, Email email,
                                          Telephone telephonePrive, Telephone telephoneMobile, Telephone fax,
                                          Genre genre, DateDeNaissance dateDeNaissance, Pays nationalite, Langue langue, Permis permis);
}
