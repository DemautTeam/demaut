package ch.vd.demaut.rest.json.serializer;

import ch.vd.demaut.commons.vo.BaseValueObject;
import ch.vd.demaut.commons.vo.StringVOInterface;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.rest.json.commons.BaseJsonSerializer;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * {@link JsonSerializer} pour le {@link CodeGLN}
 * TODO: Utiliser un serializer generic pour le StringVO (lorsque la persistence le permettra)
 * 
 */
abstract public class StringVOJsonSerializer<T extends StringVOInterface> extends BaseJsonSerializer<T> {

    @Override
    public void serialize(T value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
            JsonProcessingException {
        writeToJsonGenerator(value, jgen);
    }
    
    @Override
    protected void writeToJsonGenerator(T value, JsonGenerator jgen) throws IOException, JsonProcessingException {
        jgen.writeString(value.getValue());
    }
}
