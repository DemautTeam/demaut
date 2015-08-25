package ch.vd.ses.demaut.portail.appliquette;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication
public class SampleJspApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SampleJspApplication.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SampleJspApplication.class, args);
    }

}