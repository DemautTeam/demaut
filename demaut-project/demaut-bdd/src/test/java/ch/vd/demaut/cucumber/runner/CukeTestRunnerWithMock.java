package ch.vd.demaut.cucumber.runner;

import org.junit.runner.RunWith;

import ch.vd.demaut.cucumber.junit.CucumberMockProfile;
import cucumber.api.CucumberOptions;

@RunWith(CucumberMockProfile.class)
@CucumberOptions(features = "src/test/resources/features", // ou classpath:**/*.feature
        glue = {"ch.vd.demaut.cucumber.steps.definitions"},//
        strict = false, //
        monochrome = true, //
        plugin = {"pretty",//
                "html:target/demaut-bdd-report",})
public class CukeTestRunnerWithMock {

}
