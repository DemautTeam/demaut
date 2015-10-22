package ch.vd.demaut.domain.demandeur.donneesProf.diplome;

import ch.vd.demaut.commons.fk.FunctionalKeyAbstract;

/**
 * Clée fonction d'un {@link Diplome}
 *
 */
public class DiplomeFK extends FunctionalKeyAbstract<Diplome> {

    private ReferenceDeDiplome referenceDeDiplome;

    
    public DiplomeFK(Diplome diplome) {
        this.referenceDeDiplome = diplome.getReferenceDeDiplome();
    }

    public DiplomeFK(ReferenceDeDiplome referenceDeDiplome) {
        this.referenceDeDiplome = referenceDeDiplome;
    }

    public ReferenceDeDiplome getReferenceDeDiplome() {
        return referenceDeDiplome;
    }

}
