package ch.vd.demaut.microbiz.rest;

import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.microbiz.rest.impl.AnnexeRestImpl;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Ignore("Unable to instantiate Configuration ValidationException")
@ContextConfiguration({ "classpath*:microbizTest-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AnnexeRestImplTest {

    private AnnexeRestImpl annexeRest;

    @Inject
    private DemandeAutorisationService demandeAutorisationService;

    private ReferenceDeDemande referenceDeDemande;

    private byte[] byteArray;

    @Before
    public void setUp() throws Exception {
        byteArray = IOUtils.toByteArray(new FileInputStream("src/test/resources/demautMicrobizTest.cfg"));

        Profession profession = Profession.Medecin;

        assertNotNull(byteArray);
        assertNotNull(annexeRest);

        DemandeAutorisation demandeEnCours = demandeAutorisationService.initialiserDemandeAutorisation(profession);
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

    @Test
    public void testListerLesAnnexes() throws Exception {
        Response response = annexeRest.listerLesAnnexes(null, referenceDeDemande.getValue());
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }

    @Test
    public void testAfficherUneAnnexeInvalid() throws Exception {
        Response response = annexeRest.afficherUneAnnexe(null, referenceDeDemande.getValue(), "Test_multipart.pdf",
                "-1");
        assertNotNull(response);
    }

    @Test
    public void testAttacherUneAnnexe() throws Exception {
        File fileMultipart = new File("target/Test_multipart.cfg");
        FileUtils.writeByteArrayToFile(fileMultipart, byteArray);
        Response response = annexeRest.attacherUneAnnexe(null, referenceDeDemande.getValue(), fileMultipart,
                "Test_multipart.pdf", String.valueOf(byteArray.length), "application/cfg",
                String.valueOf(TypeAnnexe.CV.getRefProgresID().getId()));
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }

    @Test
    public void testSupprimerAnnexe() throws Exception {
        Response response = annexeRest.supprimerUneAnnexe(null, referenceDeDemande.getValue(), "Test_multipart.pdf",
                String.valueOf(TypeAnnexe.CV.getRefProgresID().getId()));
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }
}