package ch.vd.demaut.services.demandeurs.donneesProf.impl;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.domain.demandeur.donneesProf.DonneesProfessionnelles;
import ch.vd.demaut.domain.demandeur.donneesProf.DonneesProfessionnellesNotFoundException;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
import ch.vd.demaut.services.demandeurs.donneesProf.DonneesProfessionnellesService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service("donneesProfessionnellesService")
public class DonneesProfessionnellesServiceImpl implements DonneesProfessionnellesService {

    @Inject
    private DemandeAutorisationService demandeAutorisationService;

    @Override
    public Profession afficherDonneesProfession(ReferenceDeDemande referenceDeDemande) {
        DemandeAutorisation demandeAutorisation = demandeAutorisationService.recupererDemandeParReference(referenceDeDemande);
        return demandeAutorisation != null ? demandeAutorisation.getProfession() : null;
    }

    @Override
    public DonneesProfessionnelles recupererDonneesProfessionnellesParReferenceDemande(ReferenceDeDemande referenceDeDemande) {
        DemandeAutorisation demandeAutorisation = demandeAutorisationService.recupererDemandeParReference(referenceDeDemande);
        if (demandeAutorisation != null) {
            return demandeAutorisation.getDonneesProfessionnelles();
        } else {
            throw new DonneesProfessionnellesNotFoundException();
        }
    }

    @Override
    public ReferenceDeDemande renseignerDonneesProfession(Login login, ReferenceDeDemande referenceDeDemande, Profession profession, CodeGLN codeGLN) {
        DemandeAutorisation demandeAutorisation = demandeAutorisationService.recupererDemandeParReference(referenceDeDemande);
        if (demandeAutorisation != null) {
            // TODO setter la profession/codeGln si demandeAutorisation le permet
            return demandeAutorisation.getReferenceDeDemande();
        } else {
            DemandeAutorisation nouvelleDemande = demandeAutorisationService.initialiserDemandeAutorisation(login, profession);
            // TODO setter codeGln dans demandeAutorisation
            return nouvelleDemande.getReferenceDeDemande();
        }
    }

}
