package ch.vd.demaut.rest.json;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import ch.vd.demaut.domain.demandeur.Email;
import ch.vd.demaut.domain.demandeur.Localite;
import ch.vd.demaut.domain.demandeur.Telephone;
import ch.vd.demaut.domain.demandeur.donneesPerso.Nom;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.*;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.envisagee.ActiviteEnvisagee;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.envisagee.DatePrevueDebut;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.envisagee.Superviseur;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.envisagee.TauxActiviteEnDemiJournee;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

@ContextConfiguration({"classpath*:jsonTest-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class JSonConversionTest {

    @Autowired
    private DemandeAutorisationFactory demandeAutorisationFactory;
    
    @Before
    public void setUp() throws Exception {
        assertThat(demandeAutorisationFactory).isNotNull();
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
    public void testActiviteFutureJson() {
        //Fixture

        //Build a valid activite future
        ActiviteFuture activiteFuture = buildActiviteFutureValide();

        assertJsonStr(activiteFuture, "{\"id\":null,\"version\":0,\"ordre\":1," +
                "\"etablissement\":{\"nomEtablissement\":\"Centre medical\",\"voie\":\"2\",\"complement\":null," +
                "\"localite\":\"Lausanne\",\"npaProfessionnel\":\"1234\",\"telephoneProf\":\"0123456\"," +
                "\"telephoneMobile\":\"0123456\",\"fax\":\"0123456\",\"email\":\"toto@titi.com\"," +
                "\"siteInternet\":\"www.google.com\"},\"typePratiqueLamal\":\"Non\"," +
                "\"activiteEnvisagee\":{\"typeActivite\":\"Dependant\",\"nombreJourParSemaine\":1," +
                "\"datePrevueDebut\":1443650400000,\"superviseur\":\"superviseur\"},\"functionalKey\":{\"ordre\":1}}");

    }


    private ActiviteFuture buildActiviteFutureValide() {

        ListeDesActivitesFutures listeDesActivitesFutures = new ListeDesActivitesFutures(new ArrayList<ActiviteFuture>());

        Etablissement etablissement = new Etablissement(new Nom("Centre medical"), new Voie("2"), null,
                new Localite("Lausanne"), new NPAProfessionnel("1234"), new Telephone("0123456"),
                new Telephone("0123456"), new Telephone("0123456"), new Email("toto@titi.com"),
                new SiteInternet("www.google.com"));
        ActiviteEnvisagee activiteEnvisagee = new ActiviteEnvisagee(TypeActivite.Dependant,
                new TauxActiviteEnDemiJournee(1), new DatePrevueDebut(new LocalDate(2015, 10, 1)),
                new Superviseur("superviseur"));
        ActiviteFuture activiteFuture = new ActiviteFuture(etablissement, TypePratiqueLamal.Non, activiteEnvisagee);

        activiteFuture.genererOrdre(listeDesActivitesFutures);

        return activiteFuture;
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
