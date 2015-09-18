package ch.vd.demaut.cucumber.runner;

import cucumber.api.junit.Cucumber;
import org.junit.runners.model.InitializationError;

import java.io.IOException;

public class CucumberJavaProfile extends Cucumber {

    public CucumberJavaProfile(Class<?> clazz) throws InitializationError, IOException {
        super(clazz);
        System.setProperty("spring.profiles.active", "java");
    }

}
