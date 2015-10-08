package ch.vd.demaut.microbiz.commons.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Classe abstraite qui implémente {@link JsonSerializer} et fournit les implémentations par défaut de
 * {@link #handledType()} et {@link #serialize(Object, JsonGenerator, SerializerProvider)}
 * <p/>
 * La plupart des JsonSerializer doivent hériter de cette classe
 *
 * @param <T>
 */
abstract public class BaseJsonSerializer<T> extends JsonSerializer<T> {


    public BaseJsonSerializer() {
    }

    abstract protected void writeToJsonGenerator(T value, JsonGenerator jgen) throws IOException,
            JsonProcessingException;

    @Override
    public void serialize(T value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
            JsonProcessingException {

        jgen.writeStartObject();
        writeToJsonGenerator(value, jgen);
        jgen.writeEndObject();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<T> handledType() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        Type type = pt.getActualTypeArguments()[0];
        return (Class<T>) type;
    }
}
