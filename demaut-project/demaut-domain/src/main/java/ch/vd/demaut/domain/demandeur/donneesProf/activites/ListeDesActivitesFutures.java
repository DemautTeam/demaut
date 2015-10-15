package ch.vd.demaut.domain.demandeur.donneesProf.activites;

import ch.vd.demaut.domain.exception.ActiviteFutureIntrouvableException;
import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ListeDesActivitesFutures {

    // ********************************************************* Fields
    private List<ActiviteFuture> ActiviteFutures;

    // ********************************************************* Constructor

    public ListeDesActivitesFutures(List<ActiviteFuture> ActiviteFutures) {
        this.ActiviteFutures = ActiviteFutures;
    }

    // ********************************************************* Getters

    /**
     * Renvoie la liste des annexes
     */
    public List<ActiviteFuture> listerActiviteFutures() {
        if (ActiviteFutures == null) {
            return Collections.unmodifiableList(new ArrayList<ActiviteFuture>());
        }
        return Collections.unmodifiableList(ActiviteFutures);
    }

    public void ajouterUneActiviteFuture(ActiviteFuture ActiviteFuture) {
        this.ActiviteFutures.add(ActiviteFuture);
    }

    @SuppressWarnings("unchecked")
    public Collection<ActiviteFuture> extraireActiviteFuturesDeType(TypeActivite typeActivite) {
        return CollectionUtils.select(ActiviteFutures, new BeanPropertyValueEqualsPredicate("typeActivite", typeActivite));
    }

    public void supprimerUneActiviteFuture(ActiviteFuture activiteFuture) {
        ActiviteFuture ActiviteFuture = trouverActiviteFuture(activiteFuture.getReferenceDeActivite());
        ActiviteFutures.remove(ActiviteFuture);
    }

    private ActiviteFuture trouverActiviteFuture(ReferenceDeActivite referenceDeActivite) {
        Object ActiviteFutureTrouve = CollectionUtils.find(ActiviteFutures, new BeanPropertyValueEqualsPredicate("referenceDeActivite", referenceDeActivite));
        if (ActiviteFutureTrouve == null) {
            throw new ActiviteFutureIntrouvableException();
        }
        return (ActiviteFuture) ActiviteFutureTrouve;
    }
}
