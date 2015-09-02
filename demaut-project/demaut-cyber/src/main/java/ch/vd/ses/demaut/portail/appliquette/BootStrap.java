package ch.vd.ses.demaut.portail.appliquette;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.Path;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.ResourceProvider;
import org.apache.cxf.jaxrs.spring.SpringResourceFactory;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@ImportResource({"classpath:META-INF/cxf/cxf.xml"})
public class BootStrap extends SpringBootServletInitializer {

    @Autowired
    private ApplicationContext applicationContext;

    /*public static void main(String... args) {
        SpringApplication.run(BootStrap.class, args);
    }*/

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(BootStrap.class);
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean(ApplicationContext context) {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new CXFServlet(), "/api/*");
        registrationBean.setAsyncSupported(true);
        registrationBean.setLoadOnStartup(1);
        registrationBean.setName("CXFServlet");
        return registrationBean;
    }

    @Bean
    public Server helloRestService() {
        List<ResourceProvider> resourceProviders = new LinkedList<>();
        for (String beanName : applicationContext.getBeanDefinitionNames()) {
            if (applicationContext.findAnnotationOnBean(beanName, Path.class) != null) {
                SpringResourceFactory factory = new SpringResourceFactory(beanName);
                factory.setApplicationContext(applicationContext);
                resourceProviders.add(factory);
            }
        }
        if (resourceProviders.size() > 0) {
            JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
            factory.setBus(applicationContext.getBean(SpringBus.class));
            //factory.setProviders(Arrays.asList(new JacksonJsonProvider()));
            factory.setResourceProviders(resourceProviders);
            return factory.create();
        } else {
            return null;
        }
    }
}