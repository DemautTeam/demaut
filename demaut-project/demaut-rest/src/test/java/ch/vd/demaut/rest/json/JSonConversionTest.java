package ch.vd.demaut.rest.json;

import org.junit.Before;
import org.junit.Test;

import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.demandes.DateDeCreation;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.domain.demandeur.donneesProf.DonneesProfessionnelles;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.rest.dto.DemandeAutorisationCockpitDTO;

public class JSonConversionTest extends AbstractJSonConversionTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void testCodeGLN() {
        // Fixture
        CodeGLN codeGLN = new CodeGLN("4719512002889");

        // Process transform & Assert
        assertJsonStr(codeGLN, "\"4719512002889\"");
    }

    @Test
    public void testCodeGLNDansDonneesProfessionnelles() {
        // Fixture
        CodeGLN codeGLN = new CodeGLN("4719512002889");
        DonneesProfessionnelles donneesPro = new DonneesProfessionnelles();
        donneesPro.validerEtRenseignerCodeGLN(codeGLN, Profession.TherapeuteDeLaMotricite);

        // Process transform & Assert
        assertJsonStrContains(donneesPro, "\"codeGLN\":\"4719512002889\"");

    }

    @Test
    public void testDemandeAutorisationCockpitDTO() {
        // Fixture
        DemandeAutorisation demande = demandeAutorisationFactory.initierDemandeAutorisation(new Login("c123"),
                Profession.Medecin, new CodeGLN("4719512002889"), new DateDeCreation(2015, 10, 1));
        DemandeAutorisationCockpitDTO dto = new DemandeAutorisationCockpitDTO(demande);

        // Process transform & Assert
        assertJsonStr(dto,
                "{\"referenceDeDemande\":{\"value\":\"201510-0001\"},\"dateDeCreation\":1443650400000,\"profession\":{\"name\":\"Medecin\",\"id\":53843613,\"libl\":\"Médecin\"},\"codeGLN\":\"4719512002889\",\"statut\":\"Brouillon\"}");
    }

    @Test
    public void testConversionTypeAnnexe() {
        // Fixture
        TypeAnnexe type = TypeAnnexe.CV;

        // Process transform & Assert
        assertJsonStr(type, "{\"name\":\"CV\",\"id\":1,\"libl\":\"CV\"}");
    }

    @Test
    public void testConversionReferenceDemande() {
        // Fixture
        ReferenceDeDemande ref = new ReferenceDeDemande("1234");

        // Process transform & Assert
        assertJsonStr(ref, "{\"value\":\"1234\"}");
    }


}
