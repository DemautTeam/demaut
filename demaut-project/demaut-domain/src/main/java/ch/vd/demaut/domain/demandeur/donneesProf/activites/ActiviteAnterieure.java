package ch.vd.demaut.domain.demandeur.donneesProf.activites;

import ch.vd.demaut.commons.annotations.Entity;
import ch.vd.demaut.commons.entities.EntityFunctionalKeyAware;

/**
 * Activite anterieure d'un demandeur professionel
 *
 */
@Entity
public class ActiviteAnterieure extends EntityFunctionalKeyAware  {

    // ********************************************************* Fields
    private ReferenceActiviteAnterieure refenceActiviteAnterieure;
    
    // ********************************************************* Constructor
    public ActiviteAnterieure(ReferenceActiviteAnterieure refenceActiviteAnterieure) {
        this.refenceActiviteAnterieure = refenceActiviteAnterieure;
    }
    
    // ********************************************************* Getters
    @Override
    public ActiviteAnterieureFK getFunctionalKey() {
        return new ActiviteAnterieureFK(this);
    }

    public ReferenceActiviteAnterieure getReferenceActiviteAnterieure() {
        return this.refenceActiviteAnterieure;
    }

}
