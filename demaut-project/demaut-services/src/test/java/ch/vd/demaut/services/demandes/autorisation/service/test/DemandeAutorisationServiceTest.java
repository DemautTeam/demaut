package ch.vd.demaut.services.demandes.autorisation.service.test;

import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@Ignore("Waiting for OpenJPA bug fix")
@ContextConfiguration({"classpath*:/servicesTest-context.xml"})
@ActiveProfiles({"data"})
@RunWith(SpringJUnit4ClassRunner.class)
public class DemandeAutorisationServiceTest extends TestCase {

    @Autowired
    private DemandeAutorisationService demandeAutorisationService;

    private byte[] byteArray;

    @Before
    public void setUp() throws Exception {
        byteArray = IOUtils.toByteArray(new FileInputStream("src/test/resources/demautServicesTest.cfg"));

        assertThat(demandeAutorisationService).isNotNull();
        assertNotNull(byteArray);
    }

    @Test
    public void shouldListerLesAnnexes() throws Exception {
        File fileMultipart = new File("target/Test_multipart.cfg");
        FileUtils.writeByteArrayToFile(fileMultipart, byteArray);
        Collection<?> listerLesAnnexes = demandeAutorisationService.listerLesAnnexeMetadatas("7dc53df5-703e-49b3-8670-b1c468f47f1f");
        assertThat(listerLesAnnexes).isNotEmpty();
    }

    @Test
    public void shouldAfficherUneAnnexe() throws Exception {
        Annexe annexe = demandeAutorisationService.afficherUneAnnexe("7dc53df5-703e-49b3-8670-b1c468f47f1f", "Test_multipart.pdf");
        assertThat(annexe).isNotNull();
    }

    @Test
    public void shouldAttacherUneAnnexe() throws Exception {
        File fileMultipart = new File("target/Test_multipart.cfg");
        FileUtils.writeByteArrayToFile(fileMultipart, byteArray);
        boolean response = demandeAutorisationService.attacherUneAnnexe("7dc53df5-703e-49b3-8670-b1c468f47f1f", fileMultipart, "Test_multipart.pdf", String.valueOf(byteArray.length), "application/pdf", TypeAnnexe.Certificat.name());
        assertTrue(response);
    }

    @Test
    public void shouldSupprimerAnnexe() throws Exception {
        boolean response = demandeAutorisationService.supprimerUneAnnexe("7dc53df5-703e-49b3-8670-b1c468f47f1f", "Test_multipart.pdf", TypeAnnexe.Certificat.name());
        assertTrue(response);
    }
}