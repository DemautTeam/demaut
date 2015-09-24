package ch.vd.demaut.services.demandeurs.donneesProf.test;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.ProfessionDeLaSante;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.services.demandeurs.donneesProf.DonneesProfessionnellesService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@Ignore("TODO implement DonneesProfessionnellesServiceMock")
@ContextConfiguration({"classpath*:/servicesTest-context.xml"})
@ActiveProfiles({"data"})
@RunWith(SpringJUnit4ClassRunner.class)
public class DonneesProfessionnellesServiceTest {

    @Inject
    private DonneesProfessionnellesService donneesProfessionnellesService;

    private ReferenceDeDemande referenceDeDemande;
    private ProfessionDeLaSante profession;
    private Login login;


    @Before
    public void setUp() throws Exception {
        referenceDeDemande = new ReferenceDeDemande("7dc53df5-703e-49b3-8670-b1c468f47f1f");
        profession = ProfessionDeLaSante.Medecin;
        login = new Login("admin@admin");

        assertThat(referenceDeDemande).isNotNull();
        assertThat(profession).isNotNull();
        assertThat(login).isNotNull();
        assertThat(donneesProfessionnellesService).isNotNull();
    }

    @Test
    public void testAfficherDonneesProfession() throws Exception {
        ProfessionDeLaSante professionDeLaSante = donneesProfessionnellesService.afficherDonneesProfession(referenceDeDemande);
        assertThat(professionDeLaSante).isNotNull();
    }

    @Test
    public void testRenseignerDonneesProfession() throws Exception {
        ReferenceDeDemande demandeReference = donneesProfessionnellesService.renseignerDonneesProfession(login, referenceDeDemande, profession, new CodeGLN("7601000000125"));
        assertThat(demandeReference).isNotNull();
    }
}
