package ch.vd.demaut.microbiz.rest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;

import javax.ws.rs.core.Response;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.microbiz.rest.impl.AnnexeRestImpl;

@Ignore("Scenario Data")
@ContextConfiguration({"classpath*:microbizTest-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AnnexeRestTest {

    private AnnexeRestImpl annexeRest;

    private byte[] byteArray;

    @Before
    public void setUp() throws Exception {
        byteArray = IOUtils.toByteArray(new FileInputStream("src/test/resources/demautMicrobizTest.cfg"));

        assertNotNull(byteArray);
        assertNotNull(annexeRest);
    }

    @Test
    public void testListerLesTypesAnnexes() throws Exception {
        Response response = annexeRest.listerLesTypesAnnexe();
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }

    @Test
    public void testAfficherUneAnnexe() throws Exception {
        Response response = annexeRest.afficherUneAnnexe(null, "7dc53df5-703e-49b3-8670-b1c468f47f1f", "Test_multipart.pdf");
        assertNotNull(response);
    }

    @Test
    public void testAttacherUneAnnexe() throws Exception {
        File fileMultipart = new File("target/Test_multipart.cfg");
        FileUtils.writeByteArrayToFile(fileMultipart, byteArray);
        Response response = annexeRest.attacherUneAnnexe(null, "demandeReference", fileMultipart, "Test_multipart.pdf", String.valueOf(byteArray.length), "application/cfg", TypeAnnexe.CV.name());
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }

    @Test
    public void testSupprimerAnnexe() throws Exception {
        Response response = annexeRest.supprimerUneAnnexe(null, "demandeReference", "Test_multipart.pdf", TypeAnnexe.CV.name());
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }
}