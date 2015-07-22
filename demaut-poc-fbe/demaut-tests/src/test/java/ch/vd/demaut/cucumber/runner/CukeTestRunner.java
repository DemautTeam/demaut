package ch.vd.demaut.cucumber.runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", // ou classpath:**/*.feature
glue = "ch.vd.demaut.cucumber.steps.definitions", //
strict = true, //
monochrome = true, //
plugin = { "pretty",//
		"html:target/demaut-tests-report",//
		"junit:target/demaut-tests-report.xml",//
		"json:target/demaut-tests-report.json" })
public class CukeTestRunner {

}
