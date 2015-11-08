package ch.vd.demaut.domain.demandeur.donneesProf.activites;

import java.util.List;

import ch.vd.demaut.commons.entities.ListeDesEntitesOrdonnees;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;

/**
 * Encapsule la liste des activités futures {@link ActiviteFuture} liées a une {@link DemandeAutorisation}
 *
 */
public class ListeDesActivitesFutures extends ListeDesEntitesOrdonnees<ActiviteFuture> {

    // ********************************************************* Fields

    // ********************************************************* Constructor

    public ListeDesActivitesFutures(List<ActiviteFuture> activiteFutures) {
        super(activiteFutures);
    }

    // ********************************************************* Business methods

    /**
     * Renvoie la liste des {@link ActiviteFuture}
     */
    public List<ActiviteFuture> listerActivitesFutures() {
        return listerEntities();
    }

    public void ajouterUneActiviteFuture(ActiviteFuture activiteFuture) {
        ajouterEntity(activiteFuture);
    }

    public void supprimerActiviteFuture(ActiviteFutureFK activiteFutureFK) {
        supprimerEntity(activiteFutureFK);
    }

}
