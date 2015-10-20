package ch.vd.demaut.services.demandeurs.donneesProf.impl;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.domain.demandeur.donneesProf.DonneesProfessionnelles;
import ch.vd.demaut.domain.demandeur.donneesProf.DonneesProfessionnellesNotFoundException;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.*;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
import ch.vd.demaut.services.demandeurs.donneesProf.DonneesProfessionnellesService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("donneesProfessionnellesService")
public class DonneesProfessionnellesServiceImpl implements DonneesProfessionnellesService {

    @Autowired
    private DemandeAutorisationService demandeAutorisationService;


    @Override
    public List<Profession> listerProfessionsAvecCodeGlnObligatoire() {
        return Profession.listerProfessionsAvecCodeGlnObligatoire();
    }
    
    @Override
    public Profession recupererProfessionDeDemande(Login login, ReferenceDeDemande referenceDeDemande) {
        DemandeAutorisation demandeAutorisation = demandeAutorisationService.recupererDemandeParReference(referenceDeDemande);
        return demandeAutorisation.getProfession();
    }
 
    public DonneesProfessionnelles recupererDonneesProfessionnelles(Login login, ReferenceDeDemande referenceDeDemande) {
        DemandeAutorisation demandeAutorisation = demandeAutorisationService.recupererDemandeParReference(referenceDeDemande);
        if (demandeAutorisation.getDonneesProfessionnelles() == null) {
            throw new DonneesProfessionnellesNotFoundException();
        }
        return demandeAutorisation.getDonneesProfessionnelles();
    }

    @Override
    @Transactional
    public void ajouterUnDiplome(Login login, ReferenceDeDemande referenceDeDemande, ReferenceDeDiplome referenceDeDiplome, TypeDiplomeAccepte typeDiplomeAccepte,
                                 TitreFormation titreFormation, String complement, DateObtention dateObtention, PaysObtention paysObtention,
                                 DateReconnaissance dateReconnaissance) {
        DemandeAutorisation demandeAutorisation = demandeAutorisationService.recupererDemandeParReference(referenceDeDemande);
        Diplome diplome = new Diplome(referenceDeDiplome, typeDiplomeAccepte, titreFormation, complement, dateObtention, paysObtention, dateReconnaissance);
        demandeAutorisation.getDonneesProfessionnelles().validerEtAjouterDiplome(diplome);
        //TODO trace login de modification dans DB
    }

    @Override
    @Transactional
    public void supprimerUnDiplome(Login login, ReferenceDeDemande referenceDeDemande, ReferenceDeDiplome referenceDeDiplome) {
        DemandeAutorisation demandeAutorisation = demandeAutorisationService.recupererDemandeParReference(referenceDeDemande);
        demandeAutorisation.getDonneesProfessionnelles().supprimerUnDiplome(referenceDeDiplome);
        //TODO trace login de modification dans DB
    }

    @Override
    @Transactional
    public void validerEtRenseignerCodeGLN(Login login, ReferenceDeDemande referenceDeDemande, CodeGLN codeGLN) {
        DemandeAutorisation demandeAutorisation = demandeAutorisationService.recupererDemandeParReference(referenceDeDemande);
        demandeAutorisation.getDonneesProfessionnelles().validerEtRenseignerCodeGLN(codeGLN, demandeAutorisation.getProfession());
        //TODO trace login de modification dans DB
    }

    @Override
    @Transactional
    public List<Diplome> recupererDiplomesSaisis(Login login, ReferenceDeDemande referenceDeDemande) {
        DemandeAutorisation demandeAutorisation = demandeAutorisationService.recupererDemandeParReference(referenceDeDemande);
        return demandeAutorisation.getDonneesProfessionnelles().getListeDesDiplomes().listerDiplomes();
    }
}
