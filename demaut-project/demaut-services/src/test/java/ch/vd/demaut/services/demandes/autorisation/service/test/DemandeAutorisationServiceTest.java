package ch.vd.demaut.services.demandes.autorisation.service.test;

import ch.vd.demaut.domain.annexes.NomFichier;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.ProfessionDeLaSante;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
import junit.framework.TestCase;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileInputStream;

import static org.assertj.core.api.Assertions.assertThat;

@Ignore("Waiting for OpenJPA bug fix")
@ContextConfiguration({"classpath*:/servicesTest-context.xml"})
@ActiveProfiles({"data"})
@RunWith(SpringJUnit4ClassRunner.class)
public class DemandeAutorisationServiceTest extends TestCase {

    @Autowired
    private DemandeAutorisationService demandeAutorisationService;

    private byte[] byteArray;

    private NomFichier nomFichier;
    private Login login;
    private ProfessionDeLaSante profession;

    @Before
    public void setUp() throws Exception {
        byteArray = IOUtils.toByteArray(new FileInputStream("src/test/resources/demautServicesTest.cfg"));
        profession = ProfessionDeLaSante.Medecin;
        nomFichier = new NomFichier("Test_multipart.pdf");
        login = new Login("admin@admin");

        assertThat(demandeAutorisationService).isNotNull();
        assertNotNull(byteArray);
    }

    @Test
    public void testerInitialiserDemande() {
        DemandeAutorisation demandeEnCours = demandeAutorisationService.initialiserDemandeAutorisation(login, profession);
        assertThat(demandeEnCours).isNotNull();
        assertThat(demandeEnCours.getId()).isGreaterThan(0L);
        assertThat(demandeEnCours.getReferenceDeDemande()).isNotNull();
    }
}