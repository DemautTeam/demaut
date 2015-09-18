package ch.vd.demaut.services.demandes.autorisation.service.test;

import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.ContenuAnnexe;
import ch.vd.demaut.domain.annexes.NomFichier;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.ProfessionDeLaSante;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.services.annexes.AnnexesService;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration({"classpath*:/servicesTest-context.xml"})
@ActiveProfiles({"data"})
@RunWith(SpringJUnit4ClassRunner.class)
public class DemandeAutorisationServiceTest extends TestCase {

    // ********************************************************* Injected
    @Inject
    private DemandeAutorisationService demandeAutorisationService;

    @Inject
    private AnnexesService annexeService;

    // ********************************************************* Fixtures
    private byte[] byteArray;
    private File file;

    private ReferenceDeDemande referenceDeDemande;
    private NomFichier nomFichier;
    private Login login;
    private ProfessionDeLaSante profession;
    private DemandeAutorisation demandeEnCours;
    private Annexe annexe;

    // ********************************************************* Setups
    @Before
    public void setUp() throws Exception {
        assertThat(demandeAutorisationService).isNotNull();

        assertNotNull(byteArray);

        nomFichier = new NomFichier("Test_multipart.pdf");
        login = new Login("joe.dalton@ch.vd");
        profession = ProfessionDeLaSante.Medecin;

    }

    // ********************************************************* Tests
    @Test
    public void testerInitialiserDemande() {
        demandeEnCours = demandeAutorisationService.initialiserDemandeAutorisation(login, profession);
        assertThat(demandeEnCours).isNotNull();
        assertThat(demandeEnCours.getId()).isGreaterThan(0L);
        assertThat(demandeEnCours.getReferenceDeDemande()).isNotNull();
    }


    @Test
    @Transactional
    public void testerAttacherUneAnnexe() {
        //Setup fixtures
        intialiserDemandeEnCours(null);

        //Attache une annexe
        annexeService.attacherUneAnnexe(referenceDeDemande, file, nomFichier, TypeAnnexe.Certificat);

        //Récupère demande en cours
        demandeEnCours = demandeAutorisationService.recupererDemandeParReference(referenceDeDemande);

        //Vérifie si annexe attachée
        Collection<Annexe> annexes = demandeEnCours.listerLesAnnexes();
        assertThat(annexes).hasSize(1);
    }


    @Test
    @Transactional
    public void testerRecupererContenuAnnexe() {

        long tailleAnnexe = annexe.getTaille();

        //Setup fixtures
        intialiserDemandeEnCours(annexe);

        //Récupère demande en cours
        demandeEnCours = demandeAutorisationService.recupererDemandeParReference(referenceDeDemande);

        //Vérifie si annexe attachée
        ContenuAnnexe contenuAnnexe = annexeService.recupererContenuAnnexe(referenceDeDemande, nomFichier);

        assertThat(contenuAnnexe).isNotNull();
        assertThat(contenuAnnexe.getTaille()).isEqualTo(tailleAnnexe);
    }

    // ********************************************************* Private methods for fixtures

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void intialiserDemandeEnCours(Annexe annexeALier) {
        demandeEnCours = demandeAutorisationService.initialiserDemandeAutorisation(login, profession);
        referenceDeDemande = demandeEnCours.getReferenceDeDemande();
        if (annexeALier != null) {
            annexeService.attacherUneAnnexe(referenceDeDemande, annexeALier);
        }
    }

}