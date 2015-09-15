package ch.vd.demaut.services.annexes.test;

import ch.vd.demaut.domain.annexes.ContenuAnnexe;
import ch.vd.demaut.domain.annexes.NomFichier;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.services.annexes.AnnexesService;
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

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@Ignore("Waiting for OpenJPA bug fix")
@ContextConfiguration({"classpath*:/servicesTest-context.xml"})
@ActiveProfiles({"data"})
@RunWith(SpringJUnit4ClassRunner.class)
public class AnnexesServiceTest {

    @Autowired
    private AnnexesService annexesService;

    private byte[] byteArray;

    private ReferenceDeDemande referenceDeDemande;
    private NomFichier nomFichier;
    private TypeAnnexe typeAnnexe;

    @Before
    public void setUp() throws Exception {
        byteArray = IOUtils.toByteArray(new FileInputStream("src/test/resources/demautServicesTest.cfg"));
        referenceDeDemande = new ReferenceDeDemande("7dc53df5-703e-49b3-8670-b1c468f47f1f");
        nomFichier = new NomFichier("Test_multipart.pdf");
        typeAnnexe = TypeAnnexe.Certificat;

        assertThat(annexesService).isNotNull();
        assertThat(byteArray).isNotNull();
        assertThat(referenceDeDemande).isNotNull();
        assertThat(nomFichier).isNotNull();
    }

    @Test
    public void testListerLesAnnexes() throws Exception {
        File fileMultipart = new File("target/Test_multipart.cfg");
        FileUtils.writeByteArrayToFile(fileMultipart, byteArray);
        Collection<?> listerLesAnnexes = annexesService.listerLesAnnexeMetadatas(referenceDeDemande);
        assertThat(listerLesAnnexes).isNotEmpty();
    }

    @Test
    public void testAfficherUneAnnexe() throws Exception {
        ContenuAnnexe contenuAnnexe = annexesService.afficherUneAnnexe(referenceDeDemande, nomFichier);
        assertThat(contenuAnnexe).isNotNull();
    }

    @Test
    public void testAttacherUneAnnexe() throws Exception {
        File fileMultipart = new File("target/Test_multipart.cfg");
        FileUtils.writeByteArrayToFile(fileMultipart, byteArray);
        boolean response = annexesService.attacherUneAnnexe(referenceDeDemande, fileMultipart, nomFichier, typeAnnexe);
        assertThat(response).isTrue();
    }

    @Test
    public void testSupprimerAnnexe() throws Exception {
        boolean response = annexesService.supprimerUneAnnexe(referenceDeDemande, nomFichier, typeAnnexe);
        assertThat(response).isTrue();
    }
}