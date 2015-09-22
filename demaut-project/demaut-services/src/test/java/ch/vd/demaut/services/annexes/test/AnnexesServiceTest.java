package ch.vd.demaut.services.annexes.test;

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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@Ignore
@ContextConfiguration({"classpath*:/servicesTest-context.xml"})
@ActiveProfiles({"data"})
@RunWith(SpringJUnit4ClassRunner.class)
public class AnnexesServiceTest {

    @Inject
    private DemandeAutorisationService demandeAutorisationService;

    @Inject
    private AnnexesService annexesService;

    //FIXME l'objet File n'est pas optimal
    private byte[] byteArray;
    private File file;

    private NomFichier nomFichier;
    private TypeAnnexe typeAnnexe;
    private DemandeAutorisation demandeEnCours;
    private Annexe annexe;

    private Login login;
    private ProfessionDeLaSante profession;

    @Before
    public void setUp() throws Exception {
        byteArray = IOUtils.toByteArray(new FileInputStream("src/test/resources/demautServicesTest.cfg"));
        file = new File("src/test/resources/demautServicesTest.cfg");

        nomFichier = new NomFichier("Test_multipart.pdf");
        typeAnnexe = TypeAnnexe.Certificat;
        annexe = new Annexe(TypeAnnexe.Certificat, nomFichier, new ContenuAnnexe(byteArray));

        login = new Login("joe.dalton@ch.vd");
        profession = ProfessionDeLaSante.Medecin;

        intialiserDemandeEnCours(null);

        assertThat(annexesService).isNotNull();
        assertThat(byteArray).isNotNull();
        assertThat(demandeEnCours.getReferenceDeDemande()).isNotNull();
        assertThat(nomFichier).isNotNull();

    }

    @Test
    @Transactional
    public void testListerLesAnnexesMetadata() throws Exception {
        Collection<?> listerLesAnnexes = annexesService.listerLesAnnexeMetadatas(demandeEnCours.getReferenceDeDemande());
        assertThat(listerLesAnnexes).isNotEmpty();
    }

    @Test
    @Transactional
    public void testerAttacherUneAnnexe() {
        //Setup fixtures

        //Attache une annexe
        annexesService.attacherUneAnnexe(demandeEnCours.getReferenceDeDemande(), file, nomFichier, TypeAnnexe.Certificat);

        //Récupère demande en cours
        demandeEnCours = demandeAutorisationService.recupererDemandeParReference(demandeEnCours.getReferenceDeDemande());

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
        demandeEnCours = demandeAutorisationService.recupererDemandeParReference(demandeEnCours.getReferenceDeDemande());

        //Vérifie si annexe attachée
        ContenuAnnexe contenuAnnexe = annexesService.recupererContenuAnnexe(demandeEnCours.getReferenceDeDemande(), nomFichier);

        assertThat(contenuAnnexe).isNotNull();
        assertThat(contenuAnnexe.getTaille()).isEqualTo(tailleAnnexe);
    }

    // ********************************************************* Private methods for fixtures

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void intialiserDemandeEnCours(Annexe annexeALier) {
        demandeEnCours = demandeAutorisationService.initialiserDemandeAutorisation(login, profession);
        if (annexeALier != null) {
            annexesService.attacherUneAnnexe(demandeEnCours.getReferenceDeDemande(), annexeALier);
        }
    }

}