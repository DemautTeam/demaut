package ch.vd.ses.demaut.microbiz;

import ch.vd.ses.demaut.poc.jpa.entity.Annexe;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.core.Response;
import java.io.FileInputStream;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@ContextConfiguration({"classpath*:microbizTest-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AnnexeRestTest {

    @Autowired
    private AnnexeRest annexeRest;

    @Before
    public void setUp() throws Exception {
        assertNotNull(annexeRest);
    }

    @Test
    public void shouldFetchAnnexes() throws Exception {
        Response response = annexeRest.fetchAnnexes();
        assertNotNull(response);
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }

    @Test
    public void shouldFetchAnnexeByName() throws Exception {
        Response response = annexeRest.fetchAnnexeByName("Test_annexe_find.pdf");
        assertNotNull(response);
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }

    @Test
    public void shouldFetchAnnexeBinary() throws Exception {
        Response response = annexeRest.fetchAnnexeBinary("Test_annexe_find.pdf");
        assertNotNull(response);
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }

    @Test
    public void shouldStoreAnnexe() throws Exception {
        byte[] byteArray = IOUtils.toByteArray(new FileInputStream("src/test/resources/microbiz-test.cfg"));
        assertNotNull(byteArray);

        Annexe annexe = new Annexe("Test_annexe.pdf", (long)byteArray.length, "pdf", byteArray);

        Response response = annexeRest.storeAnnexe(annexe);
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode()); // TODO bug openjpa transactional
    }
}