package ch.vd.demaut.rest.json.converters;

import ch.vd.demaut.domain.config.TypeProgres;
import ch.vd.demaut.rest.commons.json.EnumJsonSerializer;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public class TypeProgresJsonSerializer extends EnumJsonSerializer<TypeProgres> {

    @Override
    protected void writeToJsonGenerator(TypeProgres value, JsonGenerator jgen) throws IOException, JsonProcessingException {
        jgen.writeFieldName("name");
        jgen.writeString(value.name());
        jgen.writeFieldName("id");
        jgen.writeNumber(value.getRefProgresID().getId());
        jgen.writeFieldName("libl");
        jgen.writeString(value.getLibl());
    }

}
