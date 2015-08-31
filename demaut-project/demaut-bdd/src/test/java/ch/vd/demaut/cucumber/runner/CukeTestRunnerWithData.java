package ch.vd.demaut.cucumber.runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;

@RunWith(CucumberDataProfile.class)
@CucumberOptions(
        features = "src/test/resources/features", // ou classpath:**/*.feature
        glue = {"ch.vd.demaut.cucumber.steps.definitions", "cucumber.api.spring"},
        strict = false,
        monochrome = true,
        plugin = {"pretty",
                "html:target/demaut-bdd-report"})
public class CukeTestRunnerWithData {

}
