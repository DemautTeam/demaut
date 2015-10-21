package ch.vd.demaut.services.demandeurs.donneesPerso.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandeur.Pays;
import ch.vd.demaut.domain.demandeur.donneesPerso.Adresse;
import ch.vd.demaut.domain.demandeur.donneesPerso.DateDeNaissance;
import ch.vd.demaut.domain.demandeur.donneesPerso.DonneesPersonnelles;
import ch.vd.demaut.domain.demandeur.donneesPerso.Email;
import ch.vd.demaut.domain.demandeur.donneesPerso.Fax;
import ch.vd.demaut.domain.demandeur.donneesPerso.Genre;
import ch.vd.demaut.domain.demandeur.donneesPerso.Langue;
import ch.vd.demaut.domain.demandeur.donneesPerso.Nom;
import ch.vd.demaut.domain.demandeur.donneesPerso.NomDeCelibataire;
import ch.vd.demaut.domain.demandeur.donneesPerso.Permis;
import ch.vd.demaut.domain.demandeur.donneesPerso.Prenom;
import ch.vd.demaut.domain.demandeur.donneesPerso.TelephoneMobile;
import ch.vd.demaut.domain.demandeur.donneesPerso.TelephonePrive;
import ch.vd.demaut.domain.exception.DonneesProfessionnellesNotFoundException;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
import ch.vd.demaut.services.demandeurs.donneesPerso.DonneesPersonnellesService;

@Service("donneesPersonnellesService")
public class DonneesPersonnellesServiceImpl implements DonneesPersonnellesService {

    @Autowired
    private DemandeAutorisationService demandeAutorisationService;

    public DonneesPersonnelles recupererDonneesPersonnelles(Login login, ReferenceDeDemande referenceDeDemande) {
        DemandeAutorisation demandeAutorisation = demandeAutorisationService.recupererDemandeParReference(referenceDeDemande);
        if (demandeAutorisation.getDonneesPersonnelles() == null) {
            throw new DonneesProfessionnellesNotFoundException();
        }
        return demandeAutorisation.getDonneesPersonnelles();
    }

    @Transactional
    @Override
    public void renseignerLesDonneesPersonnelles(Login login, ReferenceDeDemande referenceDeDemande,
                                                 Nom nom, Prenom prenom, NomDeCelibataire nomDeCelibataire, Adresse adresse, Email email,
                                                 TelephonePrive telephonePrive, TelephoneMobile telephoneMobile, Fax fax, Genre genre, DateDeNaissance dateDeNaissance,
                                                 Pays nationalite, Langue langue, Permis permis) {
        DemandeAutorisation demandeAutorisation = demandeAutorisationService.recupererDemandeParReference(referenceDeDemande);
        demandeAutorisation.getDonneesPersonnelles().renseignerLesDonneesPersonnelles(nom, prenom, nomDeCelibataire, adresse, email,
                telephonePrive, telephoneMobile, fax, genre, dateDeNaissance, nationalite, langue, permis);
        demandeAutorisation.validerDonneesPersonnelles();


    }
}
