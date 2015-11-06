package ch.vd.demaut.rest.services;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.rest.services.impl.DemandeRestImpl;
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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Ignore("TODO Should mock @Context HttpHeaders demaut-user-id")
@ContextConfiguration({"classpath*:restTest-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DemandeRestImplTest {

    @Autowired
    private DemandeRestImpl demandeRest;

    @Before
    public void setUp() throws Exception {
        assertNotNull(demandeRest);
    }

    @Test
    public void testInitialiserDemande() throws Exception {
        Profession profession = Profession.Medecin;
        Response response = demandeRest.initialiserDemande(profession.getRefProgresID().getId(), null);
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }

    @Test
    public void testRecupererCurrentBrouillon() throws Exception {
        Response response = demandeRest.recupererBrouillon(new ReferenceDeDemande("9e88c31c-9cdf-4b8d-964a-b0af8fd06c1b"));
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }

    @Test
    public void testRecupererListeBrouillons() throws Exception {
        Response response = demandeRest.recupererListeBrouillons();
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }

    @Test
    public void testSupprimerUnBrouillon() throws Exception {
        Response response = demandeRest.supprimerUnBrouillon(new ReferenceDeDemande(demandeRest.recupererBrouillon(
                new ReferenceDeDemande("9e88c31c-9cdf-4b8d-964a-b0af8fd06c1b")).getEntity().toString()));
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }
}