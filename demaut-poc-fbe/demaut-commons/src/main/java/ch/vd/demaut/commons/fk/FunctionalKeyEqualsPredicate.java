package ch.vd.demaut.commons.fk;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;

/**
 * Predicate implementation that returns true if the functional key is the same
 * 
 */
public class FunctionalKeyEqualsPredicate extends BeanPropertyValueEqualsPredicate {

    public FunctionalKeyEqualsPredicate(FunctionalKey<?> functionalKey) {
        super("functionalKey", functionalKey);
    }
}
