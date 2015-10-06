package ch.vd.demaut.microbiz.json;

import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.microbiz.rest.RestUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Ignore
public class JSonConversionTest {

    private ObjectMapper objMapper;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testConversionTypeAnnexe() throws JsonProcessingException {
        //Fixture
        TypeAnnexe type = TypeAnnexe.CV;

        //Process transform & Assert
        assertJsonStr(type, "{\"name\":\"CV\",\"id\":50283749,\"libl\":\"CV\"}");

    }

    @Test
    public void testConversionProfession() throws JsonProcessingException {

        //Fixture
        List<Profession> professions = new ArrayList<Profession>();
        professions.add(Profession.Chiropraticien);
        professions.add(Profession.Dieteticien);

        //Process transform & Assert
        assertJsonStr(professions, "[{\"name\":\"Chiropraticien\",\"id\":53843599,\"libl\":\"Chiropraticien\"},{\"name\":\"Dieteticien\",\"id\":53843600,\"libl\":\"Diététicien\"}]");

    }

    @Test
    public void testConversionReferenceDemande() throws JsonProcessingException {
        //Fixture
        ReferenceDeDemande ref = new ReferenceDeDemande("1234");

        //Process transform & Assert
        assertJsonStr(ref, "{\"value\":\"1234\"}");
    }


    private void assertJsonStr(Object object, String jsonStrExpected) throws JsonProcessingException {
        ObjectWriter viewWriter = objMapper.writer();
        String jsonStrActual = viewWriter.writeValueAsString(object);
        assertThat(jsonStrActual).isEqualTo(jsonStrExpected);
    }
}
