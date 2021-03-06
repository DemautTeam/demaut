package ch.vd.demaut.services.demandeurs.donneesPerso.impl;

import org.springframework.transaction.annotation.Transactional;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;
import ch.vd.demaut.domain.demandeur.Email;
import ch.vd.demaut.domain.demandeur.Pays;
import ch.vd.demaut.domain.demandeur.Telephone;
import ch.vd.demaut.domain.demandeur.donneesPerso.Adresse;
import ch.vd.demaut.domain.demandeur.donneesPerso.DateDeNaissance;
import ch.vd.demaut.domain.demandeur.donneesPerso.DonneesPersonnelles;
import ch.vd.demaut.domain.demandeur.donneesPerso.Genre;
import ch.vd.demaut.domain.demandeur.donneesPerso.Langue;
import ch.vd.demaut.domain.demandeur.donneesPerso.Nom;
import ch.vd.demaut.domain.demandeur.donneesPerso.Permis;
import ch.vd.demaut.domain.demandeur.donneesPerso.Prenom;
import ch.vd.demaut.domain.exception.DonneesProfessionnellesNotFoundException;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.services.demandeurs.donneesPerso.DonneesPersonnellesService;

public class DonneesPersonnellesServiceImpl implements DonneesPersonnellesService {

    private DemandeAutorisationRepository demandeAutorisationRepository;

    @Override
    @Transactional(readOnly = true)
    public DonneesPersonnelles recupererDonneesPersonnelles(Login login, ReferenceDeDemande referenceDeDemande) {
        DemandeAutorisation demandeAutorisation = demandeAutorisationRepository.recupererDemandeParReference(referenceDeDemande);
        if (demandeAutorisation.getDonneesPersonnelles() == null) {
            throw new DonneesProfessionnellesNotFoundException();
        }
        return demandeAutorisation.getDonneesPersonnelles();
    }

    @Transactional
    @Override
    public void renseignerLesDonneesPersonnelles(Login login, ReferenceDeDemande referenceDeDemande,
                                                 Nom nom, Prenom prenom, Nom nomDeCelibataire, Adresse adresse, Email email,
                                                 Telephone telephonePrive, Telephone telephoneMobile, Telephone fax, Genre genre, DateDeNaissance dateDeNaissance,
                                                 Pays nationalite, Langue langue, Permis permis) {
        DemandeAutorisation demandeAutorisation = demandeAutorisationRepository.recupererDemandeParReference(referenceDeDemande);
        demandeAutorisation.getDonneesPersonnelles().renseignerLesDonneesPersonnelles(nom, prenom, nomDeCelibataire, adresse, email,
                telephonePrive, telephoneMobile, fax, genre, dateDeNaissance, nationalite, langue, permis);
        demandeAutorisation.validerDonneesPersonnelles();
    }

    public void setDemandeAutorisationRepository(DemandeAutorisationRepository demandeAutorisationRepository) {
        this.demandeAutorisationRepository = demandeAutorisationRepository;
    }
}
