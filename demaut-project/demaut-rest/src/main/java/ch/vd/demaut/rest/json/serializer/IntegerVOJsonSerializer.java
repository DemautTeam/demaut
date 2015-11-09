package ch.vd.demaut.rest.json.serializer;

import ch.vd.demaut.commons.vo.IntegerVOInterface;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.rest.json.commons.BaseJsonSerializer;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * {@link JsonSerializer} pour le {@link CodeGLN}
 */
abstract public class IntegerVOJsonSerializer<T extends IntegerVOInterface> extends BaseJsonSerializer<T> {

    @Override
    public void serialize(T value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        writeToJsonGenerator(value, jgen);
    }
    
    @Override
    protected void writeToJsonGenerator(T value, JsonGenerator jgen) throws IOException {
        int val = value.getValue() != null ? value.getValue().intValue():value.getValue();
        jgen.writeNumber(val);
    }
}
