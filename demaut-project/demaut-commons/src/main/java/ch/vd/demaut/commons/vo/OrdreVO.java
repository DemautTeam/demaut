package ch.vd.demaut.commons.vo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Représente un ordre dans une liste <br>
 * Peut être itilisé aussi en tant que FK dans une liste d'entités
 *
 */
public class OrdreVO extends BaseValueObject implements Comparable<OrdreVO> {

    // *********************************************** Fields
    private Integer ordre;

    // *********************************************** Constructeurs
    //For JPA Usage Only
    OrdreVO() {
        
    }
    
    public OrdreVO(int ordre) {
        this.ordre = ordre;
    }

    // *********************************************** Getters
    @NotNull
    @Min(value = 0)
    public Integer getOrdre() {
        return ordre;
    }
    
    @Override
    public int compareTo(OrdreVO o) {
        return this.getOrdre().compareTo(o.getOrdre());
    }
    
}
