package ch.vd.demaut.microbiz.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.microbiz.rest.impl.ProfessionRestImpl;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;

@ContextConfiguration({"classpath*:microbizTest-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProfessionRestImplTest {

    @Inject
    private ProfessionRestImpl professionRestImpl;

    @Inject
    private DemandeAutorisationService demandeAutorisationService;

    private ReferenceDeDemande referenceDeDemande;

    @Before
    public void setUp() throws Exception {
        Profession profession = Profession.Medecin;

        assertNotNull(professionRestImpl);
        assertThat(demandeAutorisationService).isNotNull();

        DemandeAutorisation demandeEnCours = demandeAutorisationService.initialiserDemandeAutorisation(profession);
        referenceDeDemande = demandeEnCours.getReferenceDeDemande();
    }

    @Test
    public void testListerLesProfessionsDeLaSante() throws Exception {
        Response response = professionRestImpl.listerLesProfessionsDeLaSante(null);
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }

    @Test
    public void testAfficherDonneesProfession() throws Exception {
        Response response = professionRestImpl.afficherDonneesProfession(null, referenceDeDemande.getValue());
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }

    @Test
    public void testRenseignerDonneesProfession() throws Exception {
        Response response = professionRestImpl.renseignerDonneesProfession(null, referenceDeDemande.getValue(), "53843599", "7601000000125");
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }
}