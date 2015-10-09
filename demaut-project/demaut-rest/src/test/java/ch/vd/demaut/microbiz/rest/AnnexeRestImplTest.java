package ch.vd.demaut.microbiz.rest;

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
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.rest.services.impl.AnnexeRestImpl;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;

@Ignore("Unable to instantiate Configuration ValidationException")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AnnexeRestImplTest {

    @Autowired
    private AnnexeRestImpl annexeRest;

    @Autowired
    private DemandeAutorisationService demandeAutorisationService;

    private ReferenceDeDemande referenceDeDemande;

    private byte[] byteArray;

    @Before
    public void setUp() throws Exception {
        byteArray = IOUtils.toByteArray(new FileInputStream("src/test/resources/demautMicrobizTest.cfg"));

        Profession profession = Profession.Medecin;
        Login login = new Login("admin@admin");

        assertNotNull(byteArray);
        assertNotNull(annexeRest);

        DemandeAutorisation demandeEnCours = demandeAutorisationService.initialiserDemandeAutorisation(profession, null, login);
        referenceDeDemande = demandeEnCours.getReferenceDeDemande();
        assertNotNull(referenceDeDemande);
        Annexe annexe = new Annexe(TypeAnnexe.CV, "Test_multipart.pdf", byteArray, "01.01.2015 11:00");
        demandeEnCours.validerEtAttacherAnnexe(annexe);
    }

    @Test
    public void testListerLesTypesAnnexes() throws Exception {
        Response response = annexeRest.listerLesTypesAnnexe();
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }

    @Ignore("Should mock @Context HttpHeaders demaut-user-id")
    @Test
    public void testListerLesAnnexes() throws Exception {
        Response response = annexeRest.listerLesAnnexes();
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }

    @Ignore("Should mock @Context HttpHeaders demaut-user-id")
    @Test
    public void testAfficherUneAnnexeInvalid() throws Exception {
        Response response = annexeRest.afficherUneAnnexe("Test_multipart.pdf", "-1");
        assertNotNull(response);
    }

    @Ignore("Should mock @Context HttpHeaders demaut-user-id")
    @Test
    public void testAttacherUneAnnexe() throws Exception {
        File fileMultipart = new File("target/Test_multipart.cfg");
        FileUtils.writeByteArrayToFile(fileMultipart, byteArray);
        Response response = annexeRest.attacherUneAnnexe(fileMultipart,
                "Test_multipart.pdf", String.valueOf(byteArray.length), "application/cfg",
                String.valueOf(TypeAnnexe.CV.getRefProgresID().getId()));
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }

    @Ignore("Should mock @Context HttpHeaders demaut-user-id")
    @Test
    public void testSupprimerAnnexe() throws Exception {
        Response response = annexeRest.supprimerUneAnnexe("Test_multipart.pdf",
                String.valueOf(TypeAnnexe.CV.getRefProgresID().getId()));
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }
}