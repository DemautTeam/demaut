package ch.vd.demaut.progreSoa.domain.test;

import ch.vd.ses.referentiel.demaut_1_0.RefRoot;
import ch.vd.ses.referentiel.demaut_1_0.VcListType;
import ch.vd.ses.referentiel.demaut_1_0.VcType;
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
    public void testValidMarshaller() throws Exception {
        RefRoot refRoot = new RefRoot();
        refRoot.setVcList(new VcListType());
        assertNotNull(refRoot.getVcList());
        assertNotNull(refRoot.getVcList().getVc());

        for (int index = 0; index < 10; index++) {

            VcType vcType = new VcType();
            vcType.setId((long) index);
            vcType.setLibl("Label" + index);
            refRoot.getVcList().getVc().add(vcType);
        }
        StringWriter stringWriter = new StringWriter();
        jaxbMarshaller.marshal(refRoot, stringWriter);
        assertThat(stringWriter.toString()).isNotNull();
        assertThat(stringWriter.toString()).isNotEmpty();
    }

    @Test
    public void testValidUnmarshaller() throws Exception {
        RefRoot refRoot = (RefRoot) jaxbUnmarshaller.unmarshal(fileInputStream);
        assertThat(refRoot).isNotNull();
        assertThat(refRoot.getVcList().getVc()).isNotEmpty();
        assertThat(refRoot.getVcList().getVc().size()).isEqualTo(26);
    }
}
