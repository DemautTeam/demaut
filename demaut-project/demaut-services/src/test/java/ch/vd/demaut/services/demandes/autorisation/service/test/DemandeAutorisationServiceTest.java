package ch.vd.demaut.services.demandes.autorisation.service.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;

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

import ch.vd.demaut.domain.annexes.ContenuAnnexe;
import ch.vd.demaut.domain.annexes.NomFichier;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
import junit.framework.TestCase;

@Ignore("Waiting for OpenJPA bug fix")
@ContextConfiguration({"classpath*:/servicesTest-context.xml"})
@ActiveProfiles({"data"})
@RunWith(SpringJUnit4ClassRunner.class)
public class DemandeAutorisationServiceTest extends TestCase {

    @Autowired
    private DemandeAutorisationService demandeAutorisationService;

    private byte[] byteArray;
    
    private ReferenceDeDemande ref;
    
    private NomFichier nomFichier;

    @Before
    public void setUp() throws Exception {
        byteArray = IOUtils.toByteArray(new FileInputStream("src/test/resources/demautServicesTest.cfg"));
        assertThat(demandeAutorisationService).isNotNull();
        assertNotNull(byteArray);
        ref = new ReferenceDeDemande("7dc53df5-703e-49b3-8670-b1c468f47f1f");
        nomFichier = new NomFichier("Test_multipart.pdf");
    }

    @Test
    public void shouldListerLesAnnexes() throws Exception {
        File fileMultipart = new File("target/Test_multipart.cfg");
        FileUtils.writeByteArrayToFile(fileMultipart, byteArray);
        Collection<?> listerLesAnnexes = demandeAutorisationService.listerLesAnnexesMetadatas(ref);
        assertThat(listerLesAnnexes).isNotEmpty();
    }

    @Test
    public void shouldAfficherUneAnnexe() throws Exception {
        ContenuAnnexe contenuAnnexe = demandeAutorisationService.recupererContenuAnnexe(ref, nomFichier);
        assertThat(contenuAnnexe).isNotNull();
    }

    @Test
    public void shouldAttacherUneAnnexe() throws Exception {
        File file = new File("target/Test_multipart.cfg");
        FileUtils.writeByteArrayToFile(file, byteArray);
        demandeAutorisationService.attacherUneAnnexe(ref, file, nomFichier, TypeAnnexe.Certificat);
    }

    @Test
    public void shouldSupprimerAnnexe() throws Exception {
        demandeAutorisationService.supprimerUneAnnexe(ref, nomFichier);
    }
}