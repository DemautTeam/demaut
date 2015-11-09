package ch.vd.demaut.services.demandeurs.donneesProf.activites;

import org.springframework.transaction.annotation.Transactional;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.ActiviteFuture;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.ActiviteFutureFK;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.ListeDesActivitesFutures;

/**
 * Implémentation de {@link ActivitesFuturesService}
 *
 */
public class ActivitesFuturesServiceImpl implements ActivitesFuturesService {

    // ********************************************************* Injected Fields
    private DemandeAutorisationRepository demandeAutorisationRepository;
    
    // ********************************************************* Implémentation des services
    @Transactional(readOnly = true)
    @Override
    public ListeDesActivitesFutures listerLesActivitesFutures(ReferenceDeDemande ref) {
        DemandeAutorisation demande = demandeAutorisationRepository.recupererDemandeParReference(ref);
        ListeDesActivitesFutures activitesFutures = fetcherLaListeDesActivitesFutures(demande);
        
        return activitesFutures;
    }

    @Transactional
    @Override
    public void ajouterActiviteFuture(ReferenceDeDemande ref, ActiviteFuture activiteFuture) {
        DemandeAutorisation demande = demandeAutorisationRepository.recupererDemandeParReference(ref);
        
        demande.validerEtAjouterActiviteFuture(activiteFuture);
    }

    @Transactional
    @Override
    public void supprimerActiviteFuture(ReferenceDeDemande ref, ActiviteFutureFK activiteFutureFK) {
        DemandeAutorisation demande = demandeAutorisationRepository.recupererDemandeParReference(ref);
        
        demande.supprimerUneActiviteFuture(activiteFutureFK);
    }
    
    // ********************************************************* Setters for Injection
    
    public void setDemandeAutorisationRepository(DemandeAutorisationRepository demandeAutorisationRepository) {
        this.demandeAutorisationRepository = demandeAutorisationRepository;
    }

    // ********************************************************* Methodes privees
    private ListeDesActivitesFutures fetcherLaListeDesActivitesFutures(DemandeAutorisation demande) {
        ListeDesActivitesFutures activitesFutures = demande.getActivitesFutures();
        for (ActiviteFuture activiteFuture : activitesFutures.listerActivitesFutures()) {
            activiteFuture.getOrdre();
        }
        return activitesFutures;
    }

}
