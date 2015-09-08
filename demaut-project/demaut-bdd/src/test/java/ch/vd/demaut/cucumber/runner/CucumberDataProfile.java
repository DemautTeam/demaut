package ch.vd.demaut.cucumber.runner;

import cucumber.api.junit.Cucumber;
import org.junit.runners.model.InitializationError;

import java.io.IOException;

public class CucumberDataProfile extends Cucumber {

    public CucumberDataProfile(Class<?> clazz) throws InitializationError, IOException {
        super(clazz);
        System.setProperty("spring.profiles.active", "data");
    }

}
