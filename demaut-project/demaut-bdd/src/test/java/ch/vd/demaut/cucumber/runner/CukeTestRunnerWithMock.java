package ch.vd.demaut.cucumber.runner;

import cucumber.api.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(CucumberMockProfile.class)
@CucumberOptions(features = "classpath:features", //
        glue = {"ch.vd.demaut.cucumber.steps.definitions"},//
        strict = false, //
        monochrome = true, //
        tags = {"~@soumission-demande", "~@creation-demande"},
        //name = "attacher-annexes",
        plugin = {"pretty", //
                "html:target/bdd-mock-html",
                "json:target/bdd-mock-json/demaut-report.json"})
public class CukeTestRunnerWithMock {

}
