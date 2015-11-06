package ch.vd.demaut.rest.json;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.vd.demaut.data.demandes.autorisation.repo.impl.DemandeAutorisationRepositoryJava;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.demandes.DateDeCreation;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisationFactory;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.domain.demandeur.donneesProf.DonneesProfessionnelles;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.rest.dto.DemandeAutorisationCockpitDTO;
import ch.vd.demaut.rest.json.commons.RestUtils;

public class JSonConversionTest {

    @Before
    public void setUp() throws Exception {
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
        DemandeAutorisationFactory demandeAutorisationFactory = new DemandeAutorisationFactory();
        demandeAutorisationFactory.setDemandeAutorisationRepository(new DemandeAutorisationRepositoryJava());

        DemandeAutorisation demande = demandeAutorisationFactory.initierDemandeAutorisation(new Login("c123"),
                Profession.Medecin, new CodeGLN("4719512002889"), new DateDeCreation(2015, 10, 1));
        DemandeAutorisationCockpitDTO dto = new DemandeAutorisationCockpitDTO(demande);

        // Process transform & Assert
        assertJsonStr(dto,
                "{\"referenceDeDemande\":{\"value\":\"201510-0001\"},\"dateDeCreation\":1443650400000,\"profession\":{\"name\":\"Medecin\",\"id\":53843613,\"libl\":\"Médecin\"},\"codeGLN\":\"4719512002889\"}");
    }

    @Test
    public void testConversionTypeAnnexe() {
        // Fixture
        TypeAnnexe type = TypeAnnexe.CV;

        // Process transform & Assert
        assertJsonStr(type, "{\"name\":\"CV\",\"id\":1,\"libl\":\"CV\"}");
    }

    @Test
    public void testConversionProfession() {

        // Fixture
        List<Profession> professions = new ArrayList<Profession>();
        professions.add(Profession.Chiropraticien);
        professions.add(Profession.Dieteticien);

        // Process transform & Assert
        assertJsonStr(professions,
                "[{\"name\":\"Chiropraticien\",\"id\":53843599,\"libl\":\"Chiropraticien\"},{\"name\":\"Dieteticien\",\"id\":53843600,\"libl\":\"Diététicien\"}]");

    }

    @Test
    public void testConversionReferenceDemande() {
        // Fixture
        ReferenceDeDemande ref = new ReferenceDeDemande("1234");

        // Process transform & Assert
        assertJsonStr(ref, "{\"value\":\"1234\"}");
    }

    private void assertJsonStr(Object object, String jsonStrExpected) {

        String jsonStrActual = RestUtils.buildJSonString(object);

        assertThat(jsonStrActual).isEqualTo(jsonStrExpected);
    }

    private void assertJsonStrContains(Object object, String jsonStrContainsExpected) {

        String jsonStrActual = RestUtils.buildJSonString(object);

        assertThat(jsonStrActual).contains(jsonStrContainsExpected);
    }

}
