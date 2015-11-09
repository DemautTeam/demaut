package ch.vd.demaut.rest.json;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisationFactory;
import ch.vd.demaut.rest.json.commons.RestUtils;

@ContextConfiguration({"classpath*:jsonTest-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class DonneesPersoJSonConversionTest {

    @Autowired
    private DemandeAutorisationFactory demandeAutorisationFactory;
    
    @Before
    public void setUp() throws Exception {
        assertThat(demandeAutorisationFactory).isNotNull();
    }

    @Test
    public void testConversionTypeAnnexe() {
        // Fixture
        TypeAnnexe type = TypeAnnexe.CV;

        // Process transform & Assert
        assertJsonStr(type, "{\"name\":\"CV\",\"id\":1,\"libl\":\"CV\"}");
    }
    
    private void assertJsonStr(Object object, String jsonStrExpected) {

        String jsonStrActual = RestUtils.buildJSonString(object);

        assertThat(jsonStrActual).isEqualTo(jsonStrExpected);
    }

}
