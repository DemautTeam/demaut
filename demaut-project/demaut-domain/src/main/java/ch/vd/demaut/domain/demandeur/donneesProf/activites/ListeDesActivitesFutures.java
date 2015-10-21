package ch.vd.demaut.domain.demandeur.donneesProf.activites;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;

import ch.vd.demaut.domain.exception.ActiviteFutureIntrouvableException;

public class ListeDesActivitesFutures {

    // ********************************************************* Fields
    private List<ActiviteFuture> activitesFutures;

    // ********************************************************* Constructor

    public ListeDesActivitesFutures(List<ActiviteFuture> ActiviteFutures) {
        this.activitesFutures = ActiviteFutures;
    }

    // ********************************************************* Getters

    /**
     * Renvoie la liste des annexes
     */
    public List<ActiviteFuture> listerActivitesFutures() {
        return Collections.unmodifiableList(activitesFutures);
    }

    public void ajouterUneActiviteFuture(ActiviteFuture ActiviteFuture) {
        this.activitesFutures.add(ActiviteFuture);
    }

    @SuppressWarnings("unchecked")
    public Collection<ActiviteFuture> extraireActiviteFuturesDeType(TypeActivite typeActivite) {
        return CollectionUtils.select(activitesFutures,
                new BeanPropertyValueEqualsPredicate("typeActivite", typeActivite));
    }

    public void supprimerUneActiviteFuture(ActiviteFuture activiteFuture) {
        ActiviteFuture ActiviteFuture = trouverActiviteFuture(activiteFuture.getReferenceDeActivite());
        activitesFutures.remove(ActiviteFuture);
    }

    private ActiviteFuture trouverActiviteFuture(ReferenceDeActivite referenceDeActivite) {
        Object ActiviteFutureTrouve = CollectionUtils.find(activitesFutures,
                new BeanPropertyValueEqualsPredicate("referenceDeActivite", referenceDeActivite));
        if (ActiviteFutureTrouve == null) {
            throw new ActiviteFutureIntrouvableException();
        }
        return (ActiviteFuture) ActiviteFutureTrouve;
    }
}
