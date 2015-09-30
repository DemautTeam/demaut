package ch.vd.demaut.cucumber.runner;

import cucumber.api.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Permet d'exécuter les scénarios Cucumber en mode JPA
 */
@RunWith(CucumberDataProfile.class)
@CucumberOptions(
        features = "classpath:features", // ou classpath:**/*.feature
        glue = {"ch.vd.demaut.cucumber.steps.definitions"}, //
        strict = false, //
        monochrome = true, //
        tags = {"@creation-demande,@annexes,@donnees-diplomes", "~@ignoreme"},//
        plugin = {"pretty", //
                "html:target/bdd-data-html", //
                "json:target/bdd-data-json/demaut-report.json"}) //
public class CukeTestRunnerWithJpaRepo {

}
