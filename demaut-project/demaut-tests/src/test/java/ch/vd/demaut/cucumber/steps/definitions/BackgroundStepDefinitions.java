package ch.vd.demaut.cucumber.steps.definitions;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.vd.demaut.commons.DateEtHeureCourant;
import ch.vd.demaut.cucumber.converters.commons.DateTimeConverter;
import cucumber.api.Transform;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.fr.Etantdonné;

/**
 *  Step definitions pour les Backgrounds utilisé dans plusieurs features
 *
 */
public class BackgroundStepDefinitions extends StepDefinitions {

	// ********************************************************* Fields
	
    private static final Logger LOGGER = LoggerFactory.getLogger(BackgroundStepDefinitions.class);

    private DateEtHeureCourant dateHeureCourant;

    // ********************************************************* Before

    @Before
    public void before() {
        LOGGER.debug("Before scenario execution");
    }

    // ********************************************************* After
    @After
    public void after() {
    	LOGGER.debug("After scenario execution");
    }
    
    // ********************************************************* Given
    
    @Etantdonné("^la date du jour: \"([^\"]*)\"$")
    public void date_du_jour(@Transform(DateTimeConverter.class)  DateTime dateHeureCourant) {
    	
        this.dateHeureCourant = new DateEtHeureCourant(dateHeureCourant);
        
        LOGGER.debug("La date du jour est: " + dateHeureCourant);
    }

    // ********************************************************* Getters
    
    public DateEtHeureCourant getDateHeureCourant() {
		return dateHeureCourant;
	}
    

}
