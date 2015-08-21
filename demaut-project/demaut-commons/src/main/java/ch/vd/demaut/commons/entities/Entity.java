package ch.vd.demaut.commons.entities;

import java.io.Serializable;

/**
 * A implémenter par toutes les {@link Entity}.
 *
 * @param <I> l'identifiant de l'{@link Entity}
 */
public interface Entity<I extends Serializable> {

    /**
     * @return l'identifiant de l'{@link Entity}.
     */
    I getId();

    /**
     * @param id
     */
    void setId(I id);

}
