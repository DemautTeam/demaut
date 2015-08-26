package ch.vd.demaut.cucumber.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features", // ou classpath:**/*.feature
        glue = {"ch.vd.demaut.cucumber.steps.definitions", "cucumber.api.spring"},
        strict = false,
        monochrome = true,
        plugin = {"pretty",
                "html:target/demaut-bdd-report"})
public class CukeTestRunnerWithData {

    //TODO: Make it run with profile "jpa"
}
