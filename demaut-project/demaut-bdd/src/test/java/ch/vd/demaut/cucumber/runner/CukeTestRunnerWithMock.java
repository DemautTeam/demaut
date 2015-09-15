package ch.vd.demaut.cucumber.runner;

import cucumber.api.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(CucumberMockProfile.class)
@CucumberOptions(//
        features = "classpath:features", // ou classpath:**/*.feature
        glue = {"ch.vd.demaut.cucumber.steps.definitions"}, //
        strict = false, //
        monochrome = true, //
        tags = {"@creation-demande,@annexes", "~@ignoreme"},//
        plugin = {"pretty", //
                "html:target/bdd-mock-html",//
                "json:target/bdd-mock-json/demaut-report.json"})//
public class CukeTestRunnerWithMock {

}
