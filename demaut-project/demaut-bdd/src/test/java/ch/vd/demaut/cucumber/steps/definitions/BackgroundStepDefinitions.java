package ch.vd.demaut.cucumber.steps.definitions;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.vd.demaut.cucumber.converteurs.commons.DateTimeConverter;
import ch.vd.demaut.cucumber.steps.BackgroundSteps;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import cucumber.api.DataTable;
import cucumber.api.Transform;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.fr.Etantdonné;

/**
 * Step definitions pour les Backgrounds utilisé dans plusieurs features
 */
public class BackgroundStepDefinitions extends StepDefinitions {

    // ********************************************************* Injected Fields
    private BackgroundSteps backgroundSteps;

    // ********************************************************* Fields

    private static final Logger LOGGER = LoggerFactory.getLogger(BackgroundStepDefinitions.class);

    // ********************************************************* Before

    @Before
    public void before() {
        LOGGER.info("Before scenario execution");
        backgroundSteps.startTransaction();
    }

    // ********************************************************* After
    @After
    public void after() {
        LOGGER.info("After scenario execution");
        backgroundSteps.rollbackTransaction();
    }

    // ********************************************************* Given

    @Etantdonné("^la date du jour: \"([^\"]*)\"$")
    public void date_du_jour(@Transform(DateTimeConverter.class) DateTime dateHeureCourant) {

        backgroundSteps.initialiserDateDuJour(dateHeureCourant);
        
        LOGGER.debug("La date du jour est: " + dateHeureCourant);
    }
    
    @Etantdonné("^les professions nécessitant un code GLN obligatoire:$")
    public void les_professions_nécessitant_un_code_GLN_obligatoire(DataTable dataTable) throws Throwable {

        List<Profession> professionsAvecCodeGlnAttendues = dataTable.asList(Profession.class);

        //TODO: Tester dans BackgroundSteps
        List<Profession> professionsAvecCodeGlnConfigurees = Profession.listerProfessionsAvecCodeGlnObligatoire();
        assertThat(professionsAvecCodeGlnConfigurees).hasSameSizeAs(professionsAvecCodeGlnAttendues);
        assertThat(professionsAvecCodeGlnConfigurees).containsAll(professionsAvecCodeGlnAttendues);
    }

    // ********************************************************* Setters for Spring
    
    public void setBackgroundSteps(BackgroundSteps backgroundSteps) {
        this.backgroundSteps = backgroundSteps;
    }

}
