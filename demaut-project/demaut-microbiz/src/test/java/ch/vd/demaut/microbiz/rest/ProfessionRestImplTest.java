package ch.vd.demaut.microbiz.rest;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.microbiz.rest.impl.ProfessionRestImpl;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@ContextConfiguration({"classpath*:microbizTest-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProfessionRestImplTest {

    @Autowired
    private ProfessionRestImpl professionRest;

    @Autowired
    private DemandeAutorisationService demandeAutorisationService;

    private ReferenceDeDemande referenceDeDemande;

    @Before
    public void setUp() throws Exception {
        Profession profession = Profession.Medecin;
        Login login = new Login("admin@admin");

        assertNotNull(professionRest);
        assertThat(demandeAutorisationService).isNotNull();

        DemandeAutorisation demandeEnCours = demandeAutorisationService.initialiserDemandeAutorisation(profession, null, login);
        referenceDeDemande = demandeEnCours.getReferenceDeDemande();
    }

    @Test
    public void testListerLesProfessionsDeLaSante() throws Exception {
        Response response = professionRest.listerLesProfessionsDeLaSante();
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }

    @Ignore("TODO Should mock @Context HttpHeaders demaut-user-id")
    @Test
    public void testAfficherDonneesProfession() throws Exception {
        Response response = professionRest.afficherDonneesProfession();
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }
}