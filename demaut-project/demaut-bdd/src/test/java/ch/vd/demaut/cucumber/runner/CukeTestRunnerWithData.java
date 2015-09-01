package ch.vd.demaut.cucumber.runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;

@RunWith(CucumberDataProfile.class)
@CucumberOptions(
        features = "classpath:features", // ou classpath:**/*.feature
        glue = {"ch.vd.demaut.cucumber.steps.definitions"},
        strict = false,
        monochrome = true,
        tags = {"~@soumission-demande,~@creation-demande"},
        plugin = {"pretty", //
        		"html:target/bdd-data-html",
                "json:target/bdd-data-json/demaut-report.json"})
public class CukeTestRunnerWithData {

}
