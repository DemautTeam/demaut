package ch.vd.demaut.cucumber.junit;

import java.io.IOException;

import org.junit.runners.model.InitializationError;

import cucumber.api.junit.Cucumber;

public class CucumberDataProfile extends Cucumber {

	public CucumberDataProfile(Class<?> clazz) throws InitializationError, IOException {
		super(clazz);
		System.setProperty("spring.profiles.active", "data");
	}

}
