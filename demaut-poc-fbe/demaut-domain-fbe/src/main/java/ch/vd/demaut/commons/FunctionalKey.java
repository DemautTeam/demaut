package ch.vd.demaut.commons;

/**
 * 
 * Identify functional key of a class implementing {@link FunctionalKeyAware} <br>
 * Useful for searching and implementation of {@link #equals(Object)} et de {@link #hashCode()}
 * 
 */
public interface FunctionalKey<T extends FunctionalKeyAware> extends ValueObject {

    /**
     * Equals implementation must use all fields of the functional key <br>
     * 
     * @param obj
     * @return
     */
    @Override
    boolean equals(Object obj);

    /**
     * hashCode implementation must use all fields of the functional key <br>
     * 
     * @param obj
     * @return
     */
    @Override
    int hashCode();

    /**
     * 
     * @return
     */
    @Override
    String toString();

    String toStringShort();

}
