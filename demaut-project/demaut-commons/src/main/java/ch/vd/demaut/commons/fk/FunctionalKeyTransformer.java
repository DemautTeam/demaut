package ch.vd.demaut.commons.fk;

import org.apache.commons.collections4.Transformer;

;

/**
 * Transformer implementation that returns functional key of input object
 */
@SuppressWarnings("rawtypes")
public class FunctionalKeyTransformer implements Transformer {

    /**
     * Singleton predicate instance
     */
    public static final Transformer INSTANCE = new FunctionalKeyTransformer();

    /**
     * Constructor
     */
    private FunctionalKeyTransformer() {
        super();
    }

    /**
     * Factory returning the singleton instance.
     *
     * @return the singleton instance
     */
    public static Transformer getInstance() {
        return INSTANCE;
    }

    @Override
    public Object transform(Object input) {
        FunctionalKeyAware fka = (FunctionalKeyAware) input;
        return fka.getFunctionalKey();
    }

}
