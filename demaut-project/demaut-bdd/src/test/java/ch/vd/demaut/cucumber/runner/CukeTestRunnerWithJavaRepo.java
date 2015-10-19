package ch.vd.demaut.cucumber.runner;

import cucumber.api.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Permet d'exécuter les scénarios Cucumber en mode Mock
 */
@RunWith(CucumberJavaProfile.class)
@CucumberOptions(//
        features = "classpath:features", // ou classpath:**/*.feature
        glue = {"ch.vd.demaut.cucumber.steps.definitions"}, //
        strict = false, //
        monochrome = true, //
        tags = {"@donnees-perso", "~@ignoreme"},//
        plugin = {"pretty", //
                "html:target/bdd-mock-html",//
                "json:target/bdd-mock-json/demaut-report.json"})//
public class CukeTestRunnerWithJavaRepo {

}
