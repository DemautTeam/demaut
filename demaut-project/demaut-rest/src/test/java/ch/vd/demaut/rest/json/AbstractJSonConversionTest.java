package ch.vd.demaut.rest.json;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisationFactory;
import ch.vd.demaut.rest.json.commons.RestUtils;

@ContextConfiguration({"classpath*:jsonTest-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class AbstractJSonConversionTest {

    @Autowired
    protected DemandeAutorisationFactory demandeAutorisationFactory;
    
    public void setUp() throws Exception {
        assertThat(demandeAutorisationFactory).isNotNull();
    }

    protected void assertJsonStr(Object object, String jsonStrExpected) {

        String jsonStrActual = RestUtils.buildJSonString(object);

        assertThat(jsonStrActual).isEqualTo(jsonStrExpected);
    }

    protected  void assertJsonStrContains(Object object, String jsonStrContainsExpected) {

        String jsonStrActual = RestUtils.buildJSonString(object);

        assertThat(jsonStrActual).contains(jsonStrContainsExpected);
    }

}
