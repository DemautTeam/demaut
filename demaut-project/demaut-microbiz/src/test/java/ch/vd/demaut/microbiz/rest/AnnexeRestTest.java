package ch.vd.demaut.microbiz.rest;

import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.demandes.autorisation.ProfessionDeLaSante;
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

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Ignore("Senario Data")
@ContextConfiguration({"classpath*:microbizTest-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AnnexeRestTest {

    @Inject
    private AnnexeRest annexeRest;

    private byte[] byteArray;

    @Before
    public void setUp() throws Exception {
        byteArray = IOUtils.toByteArray(new FileInputStream("src/test/resources/demautMicrobizTest.cfg"));

        assertNotNull(byteArray);
        assertNotNull(annexeRest);
    }

    @Test
    public void testListerLesTypesAnnexes() throws Exception {
        Response response = annexeRest.listerLesTypesAnnexes(ProfessionDeLaSante.Medecin.name());
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }

    @Test
    public void testListerLesAnnexes() throws Exception {
        Response response = annexeRest.listerLesAnnexes("7dc53df5-703e-49b3-8670-b1c468f47f1f");
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }

    @Test
    public void testAfficherUneAnnexe() throws Exception {
        Response response = annexeRest.afficherUneAnnexe("7dc53df5-703e-49b3-8670-b1c468f47f1f", "Test_multipart.pdf");
        assertNotNull(response);
    }

    @Test
    public void testAttacherUneAnnexe() throws Exception {
        File fileMultipart = new File("target/Test_multipart.cfg");
        FileUtils.writeByteArrayToFile(fileMultipart, byteArray);
        Response response = annexeRest.attacherUneAnnexe("demandeReference", fileMultipart, "Test_multipart.pdf", String.valueOf(byteArray.length), "application/cfg", TypeAnnexe.Certificat.name());
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }

    @Test
    public void testSupprimerAnnexe() throws Exception {
        Response response = annexeRest.supprimerUneAnnexe("demandeReference", "Test_multipart.pdf", TypeAnnexe.Certificat.name());
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }
}