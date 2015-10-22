package ch.vd.ses.demaut.portail.appliquette;

import config.SecurityConfig;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.ResourceProvider;
import org.apache.cxf.jaxrs.spring.SpringResourceFactory;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;

import javax.annotation.PostConstruct;
import javax.ws.rs.Path;
import java.util.LinkedList;
import java.util.List;

/**
 * Servlet et bootstrapping de spring-boot, fonctionne à la fois en tand que main et comme servlet deployé sur Tomcat 8
 */
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableAutoConfiguration
@ComponentScan("ch.vd.ses.demaut.portail")
@ImportResource({"classpath:META-INF/cxf/cxf.xml"})
@Import({SecurityConfig.class})
public class DemautCyberApplication extends SpringBootServletInitializer {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ApplicationContext applicationContext;

    @Value("${baseMicrobiz}")
    private String urlPrefix;

    public static void main(String... args) {
        SpringApplication.run(DemautCyberApplication.class, args);
    }

    @PostConstruct
    private void showUrl() {
        logger.info("Backend Microbiz : {}", urlPrefix);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(DemautCyberApplication.class);
    }

    /**
     * Initialisation de CXF sur le chemin /api/*
     *
     * @param context
     * @return
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean(ApplicationContext context) {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new CXFServlet(), "/api/*");
        registrationBean.setAsyncSupported(true);
        registrationBean.setLoadOnStartup(1);
        registrationBean.setName("CXFServlet");
        return registrationBean;
    }

    /**
     * Server initialisé de manière automatique.
     * Seul les services JAX-RS annoté @Path seron pris en charge
     *
     * @return
     */
    @Bean
    public Server cyberRestService() {
        List<ResourceProvider> resourceProviders = new LinkedList<>();
        for (String beanName : applicationContext.getBeanNamesForAnnotation(Path.class)) {
            logger.info("Detection du service REST : {}", beanName);
            SpringResourceFactory factory = new SpringResourceFactory(beanName);
            factory.setApplicationContext(applicationContext);
            resourceProviders.add(factory);
        }
        if (resourceProviders.size() > 0) {
            JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
            factory.setBus(applicationContext.getBean(SpringBus.class));
            // A proiri ce n'est pas nécessaire
            //factory.setProviders(Arrays.asList(new JacksonJsonProvider()));
            factory.setResourceProviders(resourceProviders);
            return factory.create();
        } else {
            return null;
        }
    }
}
