package ch.vd.demaut.rest.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;

import javax.ws.rs.core.Response;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.ContenuAnnexe;
import ch.vd.demaut.domain.annexes.NomFichier;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.rest.services.impl.AnnexeRestImpl;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;

@Ignore("TODO Should mock @Context HttpHeaders demaut-user-id")
@ContextConfiguration({"classpath*:restTest-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AnnexeRestImplTest {

    @Autowired
    private AnnexeRestImpl annexeRest;

    @Autowired
    private DemandeAutorisationService demandeAutorisationService;

    private DemandeAutorisation demandeEnCours;

    private byte[] byteArray;

    @Before
    public void setUp() throws Exception {
        byteArray = IOUtils.toByteArray(new FileInputStream("src/test/resources/demautRestTest.cfg"));
        ContenuAnnexe contenu = new ContenuAnnexe(byteArray);
        NomFichier nomFichier = new NomFichier("Test_multipart.pdf");

        Profession profession = Profession.Medecin;
        Login login = new Login("admin@admin");

        assertNotNull(byteArray);
        assertNotNull(annexeRest);

        demandeEnCours = demandeAutorisationService.initialiserDemandeAutorisation(profession, null, login);
        ReferenceDeDemande referenceDeDemande = demandeEnCours.getReferenceDeDemande();
        assertNotNull(referenceDeDemande);

        Annexe annexe = new Annexe(nomFichier, contenu);
        demandeEnCours.validerEtAttacherAnnexe(annexe);
    }

    @Test
    public void testListerLesTypesAnnexes() throws Exception {
        Response response = annexeRest.listerLesTypesAnnexe();
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }

    @Test
    public void testListerLesAnnexes() throws Exception {
        Response response = annexeRest.listerLesAnnexes(demandeEnCours.getReferenceDeDemande().getValue());
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }

    @Test
    public void testAfficherUneAnnexeInvalid() throws Exception {
        Response response = annexeRest.afficherUneAnnexe(demandeEnCours.getReferenceDeDemande().getValue(), "Test_multipart.pdf", "1");
        assertNotNull(response);
    }

    @Test
    public void testAttacherUneAnnexe() throws Exception {
        File fileMultipart = new File("target/Test_multipart.cfg");
        FileUtils.writeByteArrayToFile(fileMultipart, byteArray);
        Response response = annexeRest.attacherUneAnnexe(demandeEnCours.getReferenceDeDemande().getValue(), fileMultipart,
                "Test_multipart.pdf", String.valueOf(byteArray.length), "application/cfg",
                String.valueOf(TypeAnnexe.CV.getRefProgresID().getId()));
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }

    @Test
    public void testSupprimerAnnexe() throws Exception {
        Response response = annexeRest.supprimerUneAnnexe(demandeEnCours.getReferenceDeDemande().getValue(), "Test_multipart.pdf",
                String.valueOf(TypeAnnexe.CV.getRefProgresID().getId()));
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }
}