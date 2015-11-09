package ch.vd.demaut.rest.json.serializer;

import ch.vd.demaut.rest.json.commons.BaseJsonSerializer;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.LocalDate;

import java.io.IOException;

/**
 * {@link JsonSerializer} pour le {@link LocalDate}
 * 
 */
public class LocalDateJsonSerializer extends BaseJsonSerializer<LocalDate> {

    @Override
    public void serialize(LocalDate value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        writeToJsonGenerator(value, jgen);
    }
    
    @Override
    protected void writeToJsonGenerator(LocalDate value, JsonGenerator jgen)
            throws IOException {
        jgen.writeObject(value.toDate());
    }
}
