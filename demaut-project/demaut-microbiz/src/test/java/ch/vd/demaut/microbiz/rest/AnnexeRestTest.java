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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.vd.demaut.domain.annexes.TypeAnnexe;

@ContextConfiguration({"classpath*:microbizTest-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AnnexeRestTest {

    @Autowired
    private AnnexeRest annexeRest;

    private byte[] byteArray;

    @Before
    public void setUp() throws Exception {
        byteArray = IOUtils.toByteArray(new FileInputStream("src/test/resources/demautMicrobizTest.cfg"));

        assertNotNull(byteArray);
        assertNotNull(annexeRest);
    }

    @Ignore("//TODO : Implement business method")
    @Test
    public void shouldStoreMultipart() throws Exception {
        File fileMultipart = new File("target/Test_multipart.cfg");
        FileUtils.writeByteArrayToFile(fileMultipart, byteArray);
        Response response = annexeRest.storeMultipart(fileMultipart, "Test_multipart.cfg", String.valueOf(byteArray.length), "application/cfg", TypeAnnexe.Certificat.name());
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }
}