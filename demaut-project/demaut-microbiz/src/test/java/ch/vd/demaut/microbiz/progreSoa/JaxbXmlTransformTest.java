package ch.vd.demaut.microbiz.progreSoa;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.StringWriter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

@RunWith(JUnit4.class)
public class JaxbXmlTransformTest {

    private static FileInputStream fileInputStream;

    private static Unmarshaller jaxbUnmarshaller;

    private static Marshaller jaxbMarshaller;

    {
        try {
            fileInputStream = new FileInputStream("src/test/resources/data/TYPE_PIECE.xml");
            jaxbUnmarshaller = JAXBContext.newInstance(RefRoot.class).createUnmarshaller();
            jaxbMarshaller = JAXBContext.newInstance(RefRoot.class).createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "");
        } catch (FileNotFoundException | JAXBException e) {
            e.printStackTrace();
        }
    }


    @Before
    public void setUp() throws Exception {
        assertNotNull(fileInputStream);
        assertNotNull(jaxbUnmarshaller);
        assertNotNull(jaxbMarshaller);
    }

    @Test
    public void shouldValidMarshaller() throws Exception {
        RefRoot refRoot = new RefRoot();
        assertNotNull(refRoot.getRefElements());
        for (int index = 0; index < 10; index++) {

            RefElement refElement = new RefElement();
            refElement.setId(String.valueOf(index));
            refElement.setLibl("Label" + index);
            refRoot.getRefElements().add(refElement);
        }
        StringWriter stringWriter = new StringWriter();
        jaxbMarshaller.marshal(refRoot, stringWriter);
        assertThat(stringWriter.toString()).isNotNull();
        assertThat(stringWriter.toString()).isNotEmpty();
    }

    @Test
    public void shouldValidUnmarshaller() throws Exception {
        RefRoot refRoot = (RefRoot) jaxbUnmarshaller.unmarshal(fileInputStream);
        assertThat(refRoot).isNotNull();
        assertThat(refRoot.getRefElements()).isNotEmpty();
        assertThat(refRoot.getRefElements().size()).isEqualTo(26);
    }
}
