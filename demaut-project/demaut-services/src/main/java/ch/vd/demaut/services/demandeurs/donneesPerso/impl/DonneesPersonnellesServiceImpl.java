package ch.vd.demaut.services.demandeurs.donneesPerso.impl;

import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;
import ch.vd.demaut.domain.demandeur.Pays;
import ch.vd.demaut.domain.demandeur.donneesPerso.*;
import ch.vd.demaut.domain.demandeur.donneesProf.DonneesProfessionnellesNotFoundException;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
import ch.vd.demaut.services.demandeurs.donneesPerso.DonneesPersonnellesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("donneesPersonnellesService")
public class DonneesPersonnellesServiceImpl implements DonneesPersonnellesService {

    @Autowired
    private DemandeAutorisationService demandeAutorisationService;

    @Autowired
    private DemandeAutorisationRepository demandeAutorisationRepository;


    @Transactional
    public DonneesPersonnelles recupererDonneesPersonnelles(Login login) {
        DemandeAutorisation demandeAutorisation = demandeAutorisationService.recupererBrouillon(login);
        if (demandeAutorisation.getDonneesPersonnelles() == null) {
            throw new DonneesProfessionnellesNotFoundException();
        }
        return demandeAutorisation.getDonneesPersonnelles();
    }

    @Transactional
    @Override
    public void renseignerLesDonneesPersonnelles(Login login, Nom nom, Prenom prenom, NomDeCelibataire nomDeCelibataire, Adresse adresse, Email email,
                                                 TelephonePrive telephonePrive, TelephoneMobile telephoneMobile, Fax fax, Genre genre, DateDeNaissance dateDeNaissance,
                                                 Pays nationalite, Langue langue, Permis permis) {
        DemandeAutorisation demandeAutorisation = demandeAutorisationService.recupererBrouillon(login);
        demandeAutorisation.getDonneesPersonnelles().renseignerLesDonneesPersonnelles(nom, prenom, nomDeCelibataire, adresse, email,
                telephonePrive, telephoneMobile, fax, genre, dateDeNaissance, nationalite, langue, permis);
        demandeAutorisation.validerDonneesPersonnelles();

        demandeAutorisationRepository.store(demandeAutorisation);
    }
}
