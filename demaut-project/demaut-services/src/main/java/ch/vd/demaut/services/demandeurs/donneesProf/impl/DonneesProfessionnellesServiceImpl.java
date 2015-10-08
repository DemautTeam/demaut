package ch.vd.demaut.services.demandeurs.donneesProf.impl;

import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandeur.donneesProf.DonneesProfessionnelles;
import ch.vd.demaut.domain.demandeur.donneesProf.DonneesProfessionnellesNotFoundException;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.*;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
import ch.vd.demaut.services.demandeurs.donneesProf.DonneesProfessionnellesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("donneesProfessionnellesService")
public class DonneesProfessionnellesServiceImpl implements DonneesProfessionnellesService {

    @Autowired
    private DemandeAutorisationService demandeAutorisationService;

    @Override
    public Profession afficherDonneesProfession(Login login) {
        DemandeAutorisation demandeAutorisation = demandeAutorisationService.trouverDemandeBrouillonParUtilisateur(login);
        return demandeAutorisation.getProfession();
    }

    @Transactional
    public DonneesProfessionnelles recupererDonneesProfessionnelles(Login login) {
        DemandeAutorisation demandeAutorisation = demandeAutorisationService.trouverDemandeBrouillonParUtilisateur(login);
        if (demandeAutorisation.getDonneesProfessionnelles() == null) {
            throw new DonneesProfessionnellesNotFoundException();
        }
        return demandeAutorisation.getDonneesProfessionnelles();
    }

    @Override
    @Transactional
    public void ajouterUnDiplome(Login login, ReferenceDeDiplome referenceDeDiplome, TypeDiplomeAccepte typeDiplomeAccepte,
                                 TitreFormation titreFormation, String complement, DateObtention dateObtention, PaysObtention paysObtention,
                                 DateReconnaissance dateReconnaissance) {
        DemandeAutorisation demandeAutorisation = demandeAutorisationService.trouverDemandeBrouillonParUtilisateur(login);
        Diplome diplome = new Diplome(referenceDeDiplome, typeDiplomeAccepte, titreFormation, complement, dateObtention, paysObtention, dateReconnaissance);
        demandeAutorisation.getDonneesProfessionnelles().validerEtAjouterDiplome(diplome);
    }

    @Override
    @Transactional
    public void supprimerUnDiplome(Login login, ReferenceDeDiplome referenceDeDiplome) {
        DemandeAutorisation demandeAutorisation = demandeAutorisationService.trouverDemandeBrouillonParUtilisateur(login);
        demandeAutorisation.getDonneesProfessionnelles().supprimerUnDiplome(referenceDeDiplome);
    }
}
