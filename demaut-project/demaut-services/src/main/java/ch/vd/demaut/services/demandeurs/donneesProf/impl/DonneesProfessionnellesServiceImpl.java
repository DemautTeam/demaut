package ch.vd.demaut.services.demandeurs.donneesProf.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
import ch.vd.demaut.services.demandeurs.donneesProf.DonneesProfessionnellesService;

@Service("donneesProfessionnellesService")
public class DonneesProfessionnellesServiceImpl implements DonneesProfessionnellesService {

    @Inject
    private DemandeAutorisationService demandeAutorisationService;

    @Override
    public Profession afficherDonneesProfession(ReferenceDeDemande referenceDeDemande) {
        DemandeAutorisation demandeAutorisation = demandeAutorisationService
                .recupererDemandeParReference(referenceDeDemande);
        return demandeAutorisation.getProfession();
    }

    @Override
    public ReferenceDeDemande renseignerDonneesProfession(ReferenceDeDemande referenceDeDemande, Profession profession,
            CodeGLN codeGLN) {
        DemandeAutorisation demande = demandeAutorisationService.recupererDemandeParReference(referenceDeDemande);

        // TODO: A Implémenter
        return demande.getReferenceDeDemande();
    }
}
