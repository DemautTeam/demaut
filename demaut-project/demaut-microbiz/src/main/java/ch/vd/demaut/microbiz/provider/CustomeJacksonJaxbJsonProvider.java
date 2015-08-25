package ch.vd.demaut.microbiz.provider;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.springframework.beans.factory.FactoryBean;

import javax.ws.rs.core.MediaType;

public class CustomeJacksonJaxbJsonProvider implements FactoryBean<JacksonJaxbJsonProvider> {

    @Override
    public JacksonJaxbJsonProvider getObject() throws Exception {

        JacksonJaxbJsonProvider jacksonJaxbJsonProvider = new JacksonJaxbJsonProvider();

        try {

            jacksonJaxbJsonProvider.locateMapper(JsonNode.class, MediaType.APPLICATION_JSON_TYPE);

        } catch (Throwable e) {
            e.printStackTrace();
        }

        return jacksonJaxbJsonProvider;
    }

    @Override
    public Class<?> getObjectType() {
        return JacksonJaxbJsonProvider.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
