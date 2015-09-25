package ch.vd.demaut.microbiz.json.converters;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;

import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.microbiz.commons.json.EnumJsonSerializer;

public class TypeAnnexeJsonSerializer extends EnumJsonSerializer<TypeAnnexe> {

    @Override
    protected void writeToJsonGenerator(TypeAnnexe value, JsonGenerator jgen)
            throws IOException, JsonProcessingException {
        jgen.writeFieldName("name");
        jgen.writeString(value.name());
        jgen.writeFieldName("id");
        jgen.writeNumber(value.getProgresId().getId());
        jgen.writeFieldName("libl");
        jgen.writeString(value.getLibl());
    }

}
