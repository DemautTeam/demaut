package ch.vd.demaut.domain.demandeur.donneesProf.activites;

import ch.vd.demaut.commons.fk.FunctionalKeyAbstract;

/**
 * Clé fonctionnelle de {@link ActiviteAnterieure}
 *
 */
public class ActiviteAnterieureFK extends FunctionalKeyAbstract<ActiviteAnterieure>  {

    private ReferenceActiviteAnterieure referenceActiviteAnterieure;
    
    public ActiviteAnterieureFK(ActiviteAnterieure activiteAnterieure) {
        this.referenceActiviteAnterieure = activiteAnterieure.getReferenceActiviteAnterieure();
    }

    public ReferenceActiviteAnterieure getReferenceActiviteAnterieure() {
        return referenceActiviteAnterieure;
    }
}
