package ch.vd.demaut.cucumber.runner;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", // ou classpath:**/*.feature
glue = {"ch.vd.demaut.cucumber.steps.definitions"},//
strict = false, //
monochrome = true, //
plugin = { "pretty",//
		"html:target/demaut-bdd-report",//
		"junit:target/demaut-bdd-report.xml",//
		"json:target/demaut-bdd-report.json" })
//@Profile("mock")
@ActiveProfiles("mock")
public class CukeTestRunnerWithMock {

}
