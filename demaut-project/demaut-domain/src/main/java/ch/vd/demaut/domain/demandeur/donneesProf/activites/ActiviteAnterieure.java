package ch.vd.demaut.domain.demandeur.donneesProf.activites;

import ch.vd.demaut.commons.annotations.Entity;
import ch.vd.demaut.commons.entities.EntityAvecOrdreFK;
import ch.vd.demaut.commons.vo.OrdreVO;

/**
 * Activite anterieure d'un demandeur professionel
 *
 */
@Entity
public class ActiviteAnterieure extends EntityAvecOrdreFK  {

    // ********************************************************* Fields
    
    // ********************************************************* Constructor
    
    //TODO: Changer ce constructeur lorsque fields implémentés
    public ActiviteAnterieure() {
    }
    
    // ********************************************************* Getters
    @Override
    public ActiviteAnterieureFK getFunctionalKey() {
        return new ActiviteAnterieureFK(this);
    }

}
