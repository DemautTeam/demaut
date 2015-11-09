package ch.vd.demaut.services.demandeurs.donneesProf.activites;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.ActiviteFuture;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.ActiviteFutureFK;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.ListeDesActivitesFutures;

/**
 * Services liés aux activités futures
 *
 */
public interface ActivitesFuturesService {

    /**
     * Liste les {@link ActiviteFuture} attachées à une {@link DemandeAutorisation}
     * 
     * @param ref
     *            {@link ReferenceDeDemande} unique d'une {@link DemandeAutorisation}
     * @return La {@link ListeDesActivitesFutures}
     */
    ListeDesActivitesFutures listerLesActivitesFutures(ReferenceDeDemande ref);

    /**
     * Ajoute une {@link ActiviteFuture} à la demande
     * 
     * @param ref
     * @param activiteFuture
     */
    void ajouterActiviteFuture(ReferenceDeDemande ref, ActiviteFuture activiteFuture);

    /**
     * Supprime une {@link ActiviteFuture} identifiée par la {@link ReferenceDeDemande} et son {@link OrdreFK}
     * 
     * @param ref
     * @param activiteFutureFK
     */
    void supprimerActiviteFuture(ReferenceDeDemande ref, ActiviteFutureFK activiteFutureFK);
}
