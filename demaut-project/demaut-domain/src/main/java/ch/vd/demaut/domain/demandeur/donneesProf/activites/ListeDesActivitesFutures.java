package ch.vd.demaut.domain.demandeur.donneesProf.activites;

import java.util.Collections;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;

/**
 * Encapsule la liste des activités futures {@link ActiviteFuture} liées a une {@link DemandeAutorisation}
 *
 */
public class ListeDesActivitesFutures {

    // ********************************************************* Fields
    private List<ActiviteFuture> activitesFutures;

    // ********************************************************* Constructor

    public ListeDesActivitesFutures(List<ActiviteFuture> ActiviteFutures) {
        this.activitesFutures = ActiviteFutures;
    }

    // ********************************************************* Business methods

    @NotNull
    @Valid
    public List<ActiviteFuture> getActivitesFutures() {
        return activitesFutures;
    }
    
    /**
     * Renvoie la liste des annexes
     */
    public List<ActiviteFuture> listerActivitesFutures() {
        return Collections.unmodifiableList(activitesFutures);
    }

    public void ajouterUneActiviteFuture(ActiviteFuture ActiviteFuture) {
        this.activitesFutures.add(ActiviteFuture);
    }

    public void supprimerActiviteFuture(ActiviteFuture ActiviteFuture) {
        this.activitesFutures.remove(ActiviteFuture);
    }

}
