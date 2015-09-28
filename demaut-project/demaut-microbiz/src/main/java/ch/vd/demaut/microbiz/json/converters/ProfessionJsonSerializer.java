package ch.vd.demaut.microbiz.json.converters;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;

import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.microbiz.commons.json.EnumJsonSerializer;

//TODO: Factoriser avec TypeAnnexeJsonSerializer
public class ProfessionJsonSerializer extends EnumJsonSerializer<Profession> {

    @Override
    protected void writeToJsonGenerator(Profession value, JsonGenerator jgen)
            throws IOException, JsonProcessingException {
        jgen.writeFieldName("name");
        jgen.writeString(value.name());
        jgen.writeFieldName("id");
        jgen.writeNumber(value.getProgresId().getId());
        jgen.writeFieldName("libl");
        jgen.writeString(value.getLibl());
    }

    @Override
    public Class<Profession> handledType() {
        return Profession.class;
    }
    
}
