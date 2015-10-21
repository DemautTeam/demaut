package ch.vd.demaut.rest.providers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.springframework.beans.factory.FactoryBean;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

@Provider
public class CustomJacksonJaxbJsonProvider implements FactoryBean<JacksonJaxbJsonProvider> {

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
