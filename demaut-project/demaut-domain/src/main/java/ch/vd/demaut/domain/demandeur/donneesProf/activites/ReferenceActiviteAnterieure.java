package ch.vd.demaut.domain.demandeur.donneesProf.activites;

import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;

/**
 * Référence d'une {@link ActiviteAnterieure} au sein d'une meme {@link DemandeAutorisation}
 *
 */
public class ReferenceActiviteAnterieure implements Comparable<ReferenceActiviteAnterieure> {

    private Integer ref;
    
    public ReferenceActiviteAnterieure(int ref) {
        this.ref = ref;
    }
    
    public Integer getRef() {
        return ref;
    }

    @Override
    public int compareTo(ReferenceActiviteAnterieure o) {
        return this.getRef().compareTo(o.getRef());
    }

}
