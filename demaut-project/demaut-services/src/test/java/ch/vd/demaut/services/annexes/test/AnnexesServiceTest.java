package ch.vd.demaut.services.annexes.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;

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
import org.springframework.transaction.annotation.Transactional;

import ch.vd.demaut.commons.exceptions.EntityNotUniqueException;
import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.AnnexeFK;
import ch.vd.demaut.domain.annexes.ContenuAnnexe;
import ch.vd.demaut.domain.annexes.NomFichier;
import ch.vd.demaut.domain.demandes.DateDeCreation;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.services.annexes.AnnexesService;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;

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
    private Login login;
    private CodeGLN glnValide = new CodeGLN("4719512002889");

    @Before
    public void setUp() throws Exception {
        byteArray = IOUtils.toByteArray(new FileInputStream("src/test/resources/demautServicesTest.cfg"));
        file = new File("src/test/resources/demautServicesTest.cfg");

        nomFichier = new NomFichier("Test_multipart.pdf");
        annexe = new Annexe(nomFichier, new ContenuAnnexe(byteArray), new DateDeCreation(new LocalDate()));

        profession = Profession.Medecin;
        login = null;

        assertThat(annexesService).isNotNull();
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void testListerLesAnnexesMetadata() throws Exception {
        //Setup fixtures
        creerDemandeEnCoursAvecAnnexe(annexe, new Login("admin4@admin"));

        Collection<?> listerLesAnnexes = annexesService.listerLesAnnexeMetadatas(login, demandeEnCours.getReferenceDeDemande());
        assertThat(listerLesAnnexes).isNotEmpty();
    }

    //FIXME Il y a un problème dans la délimitation de la transaction !
    @Test
    @Transactional
    @Rollback(value = true)
    public void testerAttacherUneAnnexe() {
        //Setup fixtures
        creerDemandeEnCoursAvecAnnexe(annexe, new Login("admin1@admin"));
        
        //Attache une annexe
        annexesService.attacherUneAnnexe(login, demandeEnCours.getReferenceDeDemande(), file, new NomFichier("Test_multipart2.pdf"));

        //Récupère demande en cours
        demandeEnCours = demandeAutorisationService.recupererBrouillon(login);

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
        //Setup fixtures
        creerDemandeEnCoursAvecAnnexe(annexe, new Login("admin2@admin"));
        try {
            //Attache une annexe
            annexesService.attacherUneAnnexe(login, demandeEnCours.getReferenceDeDemande(), file, nomFichier);
            failBecauseExceptionWasNotThrown(EntityNotUniqueException.class);
        } catch (EntityNotUniqueException e) {

        }
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void testerRecupererContenuAnnexe() {

        //Setup fixtures
        creerDemandeEnCoursAvecAnnexe(annexe, new Login("admin3@admin"));

        long tailleAnnexe = annexe.getTaille();

        //Récupère demande en cours
        demandeEnCours = demandeAutorisationService.recupererBrouillon(login);

        //Vérifie si annexe attachée
        AnnexeFK annexeFK = new AnnexeFK(nomFichier);
        ContenuAnnexe contenuAnnexe = annexesService.recupererContenuAnnexe(login, demandeEnCours.getReferenceDeDemande(), annexeFK);

        assertThat(contenuAnnexe).isNotNull();
        assertThat(contenuAnnexe.getTaille()).isEqualTo(tailleAnnexe);
    }

    // ********************************************************* Private methods for fixtures

    private void creerDemandeEnCoursAvecAnnexe(Annexe annexeALier, Login login) {
        this.login = login;

        demandeEnCours = demandeAutorisationService.initialiserDemandeAutorisation(profession, glnValide, login);
        
        annexesService.attacherUneAnnexe(login, demandeEnCours.getReferenceDeDemande(), annexeALier);
    }
}