package ch.vd.demaut.rest.json.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import ch.vd.demaut.commons.vo.LocalDateVOInterface;
import ch.vd.demaut.rest.json.commons.BaseJsonSerializer;

/**
 * {@link JsonSerializer} pour les types qui implémentent {@link LocalDateVOInterface}
 */
abstract public class LocalDateVOJsonSerializer<T extends LocalDateVOInterface> extends BaseJsonSerializer<T> {

    @Override
    public void serialize(T value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        writeToJsonGenerator(value, jgen);
    }
    
    @Override
    protected void writeToJsonGenerator(T value, JsonGenerator jgen) throws IOException {
        jgen.writeObject(value.getValue());
    }
}
