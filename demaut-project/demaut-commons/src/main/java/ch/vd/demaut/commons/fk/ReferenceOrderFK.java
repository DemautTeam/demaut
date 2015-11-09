package ch.vd.demaut.commons.fk;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 
 * TODO: Utiliser cela pour ReferenceActivites et OrdreActivite
 * 
 * @param <T>
 */
public abstract class ReferenceOrderFK<T extends FunctionalKeyAware> extends FunctionalKeyAbstract<T>
        implements Comparable<ReferenceOrderFK<T>> {

    // *********************************************** Fields
    protected Integer ordre;

    // *********************************************** Constructeurs
    // For JPA Usage Only
    ReferenceOrderFK() {

    }

    public ReferenceOrderFK(int ref) {
        this.ordre = ref;
    }

    // *********************************************** Getters
    @NotNull
    @Min(value = 0)
    public Integer getOrdre() {
        return ordre;
    }
    
    @Override
    public int compareTo(ReferenceOrderFK<T> o) {
        return this.getOrdre().compareTo(o.getOrdre());
    }

}
