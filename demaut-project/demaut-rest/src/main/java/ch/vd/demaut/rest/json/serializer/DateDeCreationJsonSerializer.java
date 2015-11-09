package ch.vd.demaut.rest.json.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import ch.vd.demaut.domain.demandes.DateDeCreation;
import ch.vd.demaut.rest.json.commons.BaseJsonSerializer;

/**
 * {@link JsonSerializer} pour le {@link DateDeCreation}
 * 
 */
public class DateDeCreationJsonSerializer extends BaseJsonSerializer<DateDeCreation> {

    @Override
    public void serialize(DateDeCreation value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
            JsonProcessingException {
        writeToJsonGenerator(value, jgen);
    }
    
    @Override
    protected void writeToJsonGenerator(DateDeCreation value, JsonGenerator jgen)
            throws IOException, JsonProcessingException {
        jgen.writeObject(value.getValue().toDate());
    }
}
