package ch.vd.demaut.services.annexes.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.vd.demaut.commons.exceptions.EntityNotUniqueException;
import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.AnnexeFK;
import ch.vd.demaut.domain.annexes.AnnexeMetadata;
import ch.vd.demaut.domain.annexes.ContenuAnnexe;
import ch.vd.demaut.domain.annexes.NomFichier;
import ch.vd.demaut.services.annexes.AnnexesService;
import ch.vd.demaut.services.test.AbstractServiceTest;

@ContextConfiguration({"classpath*:/servicesTest-context.xml"})
@ActiveProfiles({"data"})
@RunWith(SpringJUnit4ClassRunner.class)
public class AnnexesServiceTest extends AbstractServiceTest {

    @Autowired
    private AnnexesService annexesService;

    //FIXME l'objet File n'est pas optimal
    private byte[] byteArray;
    private File file;

    private NomFichier nomFichier;
    private Annexe annexe;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        
        byteArray = IOUtils.toByteArray(new FileInputStream("src/test/resources/demautServicesTest.cfg"));
        file = new File("src/test/resources/demautServicesTest.cfg");

        nomFichier = new NomFichier("Test_multipart.pdf");
        annexe = new Annexe(nomFichier, new ContenuAnnexe(byteArray));

        assertThat(annexesService).isNotNull();
    }

    @Test
    public void testListerLesAnnexesMetadata() throws Exception {
        //Setup fixtures
        creerDemandeEnCoursAvecAnnexe(annexe);

        //Appel service listerLesAnnexeMetadatas
        Collection<AnnexeMetadata> listerLesAnnexes = annexesService.listerLesAnnexeMetadatas(referenceDemandeEnCours);
        
        assertThat(listerLesAnnexes).hasSize(1);
    }

    @Test
    public void testerAttacherUneAnnexe() {
        //Setup fixtures
        creerDemandeEnCoursAvecAnnexe(annexe);
        
        //Attache une annexe
        annexesService.attacherUneAnnexe(referenceDemandeEnCours, file, new NomFichier("Test_multipart2.pdf"));

        //Récupère la demande pour le traitement des annexes
        Collection<AnnexeMetadata> listerLesAnnexes = annexesService.listerLesAnnexeMetadatas(referenceDemandeEnCours);     

        //Attention la demande possède déjà une annexe
        assertThat(listerLesAnnexes).hasSize(2);
    }

    @Test
    public void testerAttacherAnnexeNonUnique() {
        //Setup fixtures
        creerDemandeEnCoursAvecAnnexe(annexe);
        
        try {
            //Attache une annexe
            annexesService.attacherUneAnnexe(referenceDemandeEnCours, file, nomFichier);
            failBecauseExceptionWasNotThrown(EntityNotUniqueException.class);
        } catch (EntityNotUniqueException e) {

        }
    }

    @Test
    public void testerRecupererContenuAnnexe() {

        //Setup fixtures
        creerDemandeEnCoursAvecAnnexe(annexe);

        long tailleAnnexe = annexe.getTaille();

        //Récupère demande en cours

        //Vérifie si annexe attachée
        AnnexeFK annexeFK = new AnnexeFK(nomFichier);
        ContenuAnnexe contenuAnnexe = annexesService.recupererContenuAnnexe(referenceDemandeEnCours, annexeFK);

        assertThat(contenuAnnexe).isNotNull();
        assertThat(contenuAnnexe.getTaille()).isEqualTo(tailleAnnexe);
    }

    // ********************************************************* Private methods for fixtures
    
    private void creerDemandeEnCoursAvecAnnexe(Annexe annexeALier) {
        creerDemandeEnCours();
        annexesService.attacherUneAnnexe(referenceDemandeEnCours, annexeALier);
    }
}