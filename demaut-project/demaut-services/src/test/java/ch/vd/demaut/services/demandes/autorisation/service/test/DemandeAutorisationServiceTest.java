package ch.vd.demaut.services.demandes.autorisation.service.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;

import javax.inject.Inject;

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
    
    private ReferenceDeDemande ref;
    private NomFichier nomFichier;
    private Login login;
    private ProfessionDeLaSante profession;
    private DemandeAutorisation demandeEnCours;
    private Annexe annexe;

    // ********************************************************* Setups
    @Before
    public void setUp() throws Exception {
    	assertThat(demandeAutorisationService).isNotNull();

    	byteArray = IOUtils.toByteArray(new FileInputStream("src/test/resources/demautServicesTest.cfg"));
    	file = new File("src/test/resources/demautServicesTest.cfg");
        assertNotNull(byteArray);
        
        nomFichier = new NomFichier("Test_multipart.pdf");
        login = new Login("joe.dalton@ch.vd");
        profession = ProfessionDeLaSante.Medecin;
        annexe = new Annexe(TypeAnnexe.Certificat, nomFichier, new ContenuAnnexe(byteArray));
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
    	annexeService.attacherUneAnnexe(ref, file, nomFichier, TypeAnnexe.Certificat);
    	
    	//Récupère demande en cours
    	demandeEnCours = demandeAutorisationService.recupererDemandeParReference(ref);
    	
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
    	demandeEnCours = demandeAutorisationService.recupererDemandeParReference(ref);

    	//Vérifie si annexe attachée
    	ContenuAnnexe contenuAnnexe = annexeService.recupererContenuAnnexe(ref, nomFichier);

    	assertThat(contenuAnnexe).isNotNull();
    	assertThat(contenuAnnexe.getTaille()).isEqualTo(tailleAnnexe);
    }
    
    @Ignore
    @Test
    public void shouldListerLesAnnexes() throws Exception {
        File fileMultipart = new File("target/Test_multipart.cfg");
        FileUtils.writeByteArrayToFile(fileMultipart, byteArray);
        Collection<?> listerLesAnnexes = annexeService.listerLesAnnexeMetadatas(ref);
        assertThat(listerLesAnnexes).isNotEmpty();
    }

    @Ignore
    @Test
    public void shouldAfficherUneAnnexe() throws Exception {
        ContenuAnnexe contenuAnnexe = annexeService.recupererContenuAnnexe(ref, nomFichier);
        assertThat(contenuAnnexe).isNotNull();
    }

    @Ignore
    @Test
    public void shouldAttacherUneAnnexe() throws Exception {
        File file = new File("target/Test_multipart.cfg");
        FileUtils.writeByteArrayToFile(file, byteArray);
        annexeService.attacherUneAnnexe(ref, file, nomFichier, TypeAnnexe.Certificat);
    }

    @Ignore
    @Test
    public void shouldSupprimerAnnexe() throws Exception {
    	annexeService.supprimerUneAnnexe(ref, nomFichier);
    }
    
    // ********************************************************* Private methods for fixtures
    
    @Transactional(propagation= Propagation.REQUIRES_NEW)
    private void intialiserDemandeEnCours(Annexe annexeALier) {
    	demandeEnCours = demandeAutorisationService.initialiserDemandeAutorisation(login, profession);
    	ref = demandeEnCours.getReferenceDeDemande();
    	if (annexeALier != null) {
    		annexeService.attacherUneAnnexe(ref, annexeALier);
    	}
    }

}