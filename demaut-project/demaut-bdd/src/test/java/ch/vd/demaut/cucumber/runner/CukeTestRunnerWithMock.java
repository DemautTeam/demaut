package ch.vd.demaut.cucumber.runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;

@RunWith(CucumberMockProfile.class)
@CucumberOptions(features = "classpath:features", //
        glue = {"ch.vd.demaut.cucumber.steps.definitions"},//
        strict = false, //
        monochrome = true, //
        plugin = {"pretty",//
                "html:target/demaut-bdd-report",})
public class CukeTestRunnerWithMock {

}
