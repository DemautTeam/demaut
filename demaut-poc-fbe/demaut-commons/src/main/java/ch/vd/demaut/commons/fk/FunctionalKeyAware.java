package ch.vd.demaut.commons.fk;


/**
 * Identifies classes having and returning a functional key
 * 
 */
public interface FunctionalKeyAware {

	/**
	 * 
	 * @return The functional key of the implementing class
	 */
    FunctionalKey<? extends FunctionalKeyAware> getFunctionalKey();
}
