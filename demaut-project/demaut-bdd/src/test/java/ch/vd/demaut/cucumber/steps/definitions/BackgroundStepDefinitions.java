package ch.vd.demaut.cucumber.steps.definitions;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.vd.demaut.cucumber.converteurs.commons.DateTimeConverter;
import ch.vd.demaut.cucumber.converteurs.commons.OuiNonConverter;
import ch.vd.demaut.cucumber.steps.BackgroundSteps;
import ch.vd.demaut.domain.demandes.autorisation.CategorieProfession;
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
    
    @Etantdonné("^les professions disponibles avec les caractéristiques suivantes:$")
    public void les_professions_nécessitant_un_code_GLN_obligatoire(DataTable dataTable) throws Throwable {

        List<Profession> professionsAttendues = buildListeProfessionsAttenduesEtTesterCaracteristiques(dataTable);

        List<Profession> professionsConfigurees = Arrays.asList(Profession.values());
        assertThat(professionsConfigurees).hasSameSizeAs(professionsAttendues);
        assertThat(professionsConfigurees).containsAll(professionsAttendues);
    }

    // ********************************************************* Setters for Spring
    
    public void setBackgroundSteps(BackgroundSteps backgroundSteps) {
        this.backgroundSteps = backgroundSteps;
    }

    // ********************************************************* Private methods
    
    private List<Profession> buildListeProfessionsAttenduesEtTesterCaracteristiques(DataTable dataTable) {

        List<Profession> professionsAttendues = new ArrayList<Profession>();

        List<Map<String, String>> professionsMap = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> professionMap : professionsMap) {
            
            //Recupère les caractéristiques de la profession attendue
            String labelProfession = professionMap.get("Profession");
            
            Boolean codeGLNObligatoireAttendue = parseCodeGLNObligatoire(professionMap);
            
            CategorieProfession categorieAttendue = parseCategorieProfession(professionMap);
            
            //Verifie si caracteristiques de la profession sont ok
            Profession professionAttendue = Profession.getByLibele(labelProfession);
            assertThat(professionAttendue.isCodeGLNObligatoire()).isEqualTo(codeGLNObligatoireAttendue);
            assertThat(professionAttendue.getCategorie()).isEqualTo(categorieAttendue);
            
            //Ajoute dans la liste des professions attendues
            professionsAttendues.add(professionAttendue);
        }

        return professionsAttendues;
    }

    private Boolean parseCodeGLNObligatoire(Map<String, String> professionMap) {
        String codeGLNObligatoireStr = professionMap.get("CodeGLNObligatoire");
        return new OuiNonConverter().transform(codeGLNObligatoireStr);
    }

    private CategorieProfession parseCategorieProfession(Map<String, String> professionMap) {
        String categorieStr = professionMap.get("Categorie");
        return CategorieProfession.valueOf(categorieStr);
    }
    
}
