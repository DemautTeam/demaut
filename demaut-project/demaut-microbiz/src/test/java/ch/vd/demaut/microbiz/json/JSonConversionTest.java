package ch.vd.demaut.microbiz.json;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.microbiz.rest.RestUtils;

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

}
