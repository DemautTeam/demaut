package ch.vd.demaut.rest.json.serializer;

import ch.vd.demaut.domain.demandes.DateDeCreation;
import ch.vd.demaut.domain.demandeur.donneesPerso.Nom;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.envisagee.DatePrevueDebut;
import ch.vd.demaut.rest.json.commons.BaseJsonSerializer;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.LocalDate;

import java.io.IOException;

/**
 * {@link JsonSerializer} pour le {@link DatePrevueDebut}
 *
 */
public class DatePrevueDebutJsonSerializer extends BaseJsonSerializer<DatePrevueDebut> {
    @Override
    public void serialize(DatePrevueDebut value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        writeToJsonGenerator(value, jgen);
    }

    @Override
    protected void writeToJsonGenerator(DatePrevueDebut value, JsonGenerator jgen)
            throws IOException {
        jgen.writeObject(value.getValue());
    }

}
