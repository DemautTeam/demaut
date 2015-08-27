package ch.vd.demaut.cucumber.runner;

import java.io.IOException;

import org.junit.runners.model.InitializationError;

import cucumber.api.junit.Cucumber;

public class CucumberMockProfile extends Cucumber {

	public CucumberMockProfile(Class<?> clazz) throws InitializationError, IOException {
		super(clazz);
		System.setProperty("spring.profiles.active", "mock");
	}

}
