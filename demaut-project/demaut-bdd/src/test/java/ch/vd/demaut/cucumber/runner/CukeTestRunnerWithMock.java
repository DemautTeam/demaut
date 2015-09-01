package ch.vd.demaut.cucumber.runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;

@RunWith(CucumberMockProfile.class)
@CucumberOptions(features = "classpath:features", //
        glue = {"ch.vd.demaut.cucumber.steps.definitions"},//
        strict = false, //
        monochrome = true, //
        tags = {"~@soumission-demande","~@creation-demande"},
        //name = "attacher-annexes",
        plugin = {"pretty", //
                "html:target/bdd-mock-html",
                "json:target/bdd-mock-json/demaut-report.json"})
public class CukeTestRunnerWithMock {

}
