package ch.vd.demaut.services.annexes.test;

import ch.vd.demaut.domain.annexes.*;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.exception.AnnexeNonUniqueException;
import ch.vd.demaut.services.annexes.AnnexesService;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
import org.apache.commons.io.IOUtils;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
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
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

@ContextConfiguration({"classpath*:/servicesTest-context.xml"})
@ActiveProfiles({"data"})
@RunWith(SpringJUnit4ClassRunner.class)
public class AnnexesServiceTest {

    @Autowired
    private DemandeAutorisationService demandeAutorisationService;

    @Autowired
    private AnnexesService annexesService;

    //FIXME l'objet File n'est pas optimal
    private byte[] byteArray;
    private File file;

    private NomFichier nomFichier;
    private DemandeAutorisation demandeEnCours;
    private Annexe annexe;

    private Profession profession;

    @Before
    public void setUp() throws Exception {
        byteArray = IOUtils.toByteArray(new FileInputStream("src/test/resources/demautServicesTest.cfg"));
        file = new File("src/test/resources/demautServicesTest.cfg");

        nomFichier = new NomFichier("Test_multipart.pdf");
        annexe = new Annexe(TypeAnnexe.CV, nomFichier, new ContenuAnnexe(byteArray), new DateCreation(new LocalDate()));

        profession = Profession.Medecin;

        intialiserDemandeEnCours(annexe);

        assertThat(annexesService).isNotNull();
        assertThat(byteArray).isNotNull();
        assertThat(demandeEnCours.getReferenceDeDemande()).isNotNull();
        assertThat(nomFichier).isNotNull();
    }

    @Test
    public void testListerLesAnnexesMetadata() throws Exception {
        Collection<?> listerLesAnnexes = annexesService.listerLesAnnexeMetadatas(demandeEnCours.getReferenceDeDemande());
        assertThat(listerLesAnnexes).isNotEmpty();
    }

    //FIXME Il y a un problème dans la délimitation de la transaction !
    @Test
    @Transactional
    @Rollback(value = false)
    public void testerAttacherUneAnnexe() {
        //Setup fixtures

        //Attache une annexe
        annexesService.attacherUneAnnexe(demandeEnCours.getReferenceDeDemande(), file, nomFichier, TypeAnnexe.AttestationBonneConduite);

        //Récupère demande en cours
        demandeEnCours = demandeAutorisationService.recupererDemandeParReference(demandeEnCours.getReferenceDeDemande());

        //Vérifie si annexe attachée
        Collection<Annexe> annexes = demandeEnCours.listerLesAnnexes();
        //Attention la demande possède déjà une annexe
        assertThat(annexes).hasSize(2);
    }

    //FIXME Il y a un problème dans la délimitation de la transaction !
    @Test
    @Transactional
    @Rollback(value = true)
    public void testerAttacherAnnexeNonUnique() {
        try {
            //Attache une annexe
            annexesService.attacherUneAnnexe(demandeEnCours.getReferenceDeDemande(), file, nomFichier, TypeAnnexe.CV);
            failBecauseExceptionWasNotThrown(AnnexeNonUniqueException.class);
        } catch (AnnexeNonUniqueException e) {

        }
    }

    @Test
    public void testerRecupererContenuAnnexe() {

        long tailleAnnexe = annexe.getTaille();

        //Récupère demande en cours
        demandeEnCours = demandeAutorisationService.recupererDemandeParReference(demandeEnCours.getReferenceDeDemande());

        //Vérifie si annexe attachée
        AnnexeFK annexeFK = new AnnexeFK(nomFichier, TypeAnnexe.CV);
        ContenuAnnexe contenuAnnexe = annexesService.recupererContenuAnnexe(demandeEnCours.getReferenceDeDemande(), annexeFK);

        assertThat(contenuAnnexe).isNotNull();
        assertThat(contenuAnnexe.getTaille()).isEqualTo(tailleAnnexe);
    }

    // ********************************************************* Private methods for fixtures

    @Transactional(propagation = Propagation.REQUIRED)
    private void intialiserDemandeEnCours(Annexe annexeALier) {
        demandeEnCours = demandeAutorisationService.initialiserDemandeAutorisation(profession);
        if (annexeALier != null) {
            annexesService.attacherUneAnnexe(demandeEnCours.getReferenceDeDemande(), annexeALier);
        }
    }

}