package ch.vd.demaut.microbiz.progreSoa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import ch.vd.ses.referentiel.demaut_1_0.RefListType;
import ch.vd.ses.referentiel.demaut_1_0.RefRoot;

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

    @Ignore
    @Test
    public void shouldValidMarshaller() throws Exception {
        RefRoot refRoot = new RefRoot();
        assertNotNull(refRoot.getRefList());
        for (int index = 0; index < 10; index++) {

            RefListType refElement = new RefListType();
            refElement.setId(String.valueOf(index));
            refElement.setLibl("Label" + index);
            refRoot.getRefList().getRefListType().add(refElement);
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
        assertThat(refRoot.getRefList().getRefListType()).isNotEmpty();
        assertThat(refRoot.getRefList().getRefListType().size()).isEqualTo(26);
    }
}
