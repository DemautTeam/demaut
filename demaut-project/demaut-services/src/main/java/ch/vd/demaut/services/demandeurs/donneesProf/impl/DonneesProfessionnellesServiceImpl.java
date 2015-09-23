package ch.vd.demaut.services.demandeurs.donneesProf.impl;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.ProfessionDeLaSante;
import ch.vd.demaut.domain.demandeurs.donneesProf.CodeGLN;
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
    public ProfessionDeLaSante afficherDonneesProfession(ReferenceDeDemande referenceDeDemande) {
        DemandeAutorisation demandeAutorisation = demandeAutorisationService.recupererDemandeParReference(referenceDeDemande);
        return demandeAutorisation != null ? demandeAutorisation.getProfessionDeLaSante() : null;
    }

    @Override
    public ReferenceDeDemande renseignerDonneesProfession(Login login, ReferenceDeDemande referenceDeDemande, ProfessionDeLaSante professionDeLaSante, CodeGLN codeGLN) {
        DemandeAutorisation demandeAutorisation = demandeAutorisationService.recupererDemandeParReference(referenceDeDemande);
        if (demandeAutorisation != null) {
            // TODO setter la profession/codeGln si demandeAutorisation le permet
            return demandeAutorisation.getReferenceDeDemande();
        } else {
            DemandeAutorisation nouvelleDemande = demandeAutorisationService.initialiserDemandeAutorisation(login, professionDeLaSante);
            // TODO setter codeGln dans demandeAutorisation
            return nouvelleDemande.getReferenceDeDemande();
        }
    }
}
