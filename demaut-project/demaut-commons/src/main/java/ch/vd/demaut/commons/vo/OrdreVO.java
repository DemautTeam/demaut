package ch.vd.demaut.commons.vo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Représente un ordre dans une liste <br>
 * Peut être itilisé aussi en tant que FK dans une liste d'entités
 *
 */
public class OrdreVO extends BaseValueObject implements Comparable<OrdreVO>, IntegerVOInterface {

    // *********************************************** Fields
    private Integer value;

    // *********************************************** Constructeurs
    //For JPA Usage Only
    protected OrdreVO() {
        
    }
    
    public OrdreVO(int value) {
        this.value = value;
    }

    // *********************************************** Getters

    
    @Override
    public int compareTo(OrdreVO o) {
        return this.getValue().compareTo(o.getValue());
    }

    @Override
    @NotNull
    @Min(value = 0)
    public Integer getValue() {
        return value;
    }

}
