package ch.vd.demaut.microbiz.json;

import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.microbiz.rest.RestUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class JSonConversionTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testConversionTypeAnnexe() throws JsonProcessingException {

        ObjectMapper objMapper = RestUtils.buildJSonObjectMapper();

        TypeAnnexe type = TypeAnnexe.CV;

        assertThat(type.toString()).isEqualTo("CV");

        ObjectWriter viewWriter = objMapper.writer();

        String jsonStr = viewWriter.writeValueAsString(type);

        assertThat(jsonStr).isEqualTo("{\"name\":\"CV\",\"id\":50283749,\"libl\":\"CV\"}");

    }

    @Test
    public void testConversionProfession() throws JsonProcessingException {

        ObjectMapper objMapper = RestUtils.buildJSonObjectMapper();

        List<Profession> professions = new ArrayList<Profession>();
        professions.add(Profession.Chiropraticien);
        professions.add(Profession.Dieteticien);

        ObjectWriter viewWriter = objMapper.writer();

        String jsonStr = viewWriter.writeValueAsString(professions);

        assertThat(jsonStr).isEqualTo("[{\"name\":\"Chiropraticien\",\"id\":53843599,\"libl\":\"Chiropraticien\"},{\"name\":\"Dieteticien\",\"id\":53843600,\"libl\":\"Diététicien\"}]");

    }


}
