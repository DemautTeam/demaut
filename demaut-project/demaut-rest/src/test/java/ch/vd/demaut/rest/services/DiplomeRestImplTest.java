package ch.vd.demaut.rest.services;

import ch.vd.demaut.domain.demandeur.Pays;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.TitreFormationPostgradeProgres;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.TypeDiplomeAccepte;
import ch.vd.demaut.rest.services.impl.DiplomeRestImpl;
import org.joda.time.LocalDate;
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
import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Ignore("TODO Should mock @Context HttpHeaders demaut-user-id")
@ContextConfiguration({"classpath*:restTest-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DiplomeRestImplTest {

    @Autowired
    private DiplomeRestImpl diplomeRest;

    private String referenceDeDiplomeStr = UUID.randomUUID().toString();

    @Before
    public void setUp() throws Exception {
        assertNotNull(diplomeRest);
    }

    @Test
    public void testListerLesTypesDeDiplomes() throws Exception {
        Response response = diplomeRest.listerLesTypesDeDiplomes();
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }

    @Test
    public void testListerLesTitresFormations() throws Exception {
        Response response = diplomeRest.listerLesTitresFormations(TypeDiplomeAccepte.D_POSTGRADE.getRefProgresID().getId());
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }

    @Test
    public void testAjouterUnDiplome() throws Exception {
        Response response = diplomeRest.ajouterUnDiplome("9e88c31c-9cdf-4b8d-964a-b0af8fd06c1b", referenceDeDiplomeStr,
                TypeDiplomeAccepte.D_POSTGRADE.getRefProgresID().getId(),
                TitreFormationPostgradeProgres.Cardiologie.getRefProgresID().getId(), null,
                new LocalDate().toString(), Pays.Suisse.getRefProgresID().getId(), null);
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }

    @Test
    public void testSupprimerUnDiplome() throws Exception {
        Response response = diplomeRest.supprimerUnDiplome("9e88c31c-9cdf-4b8d-964a-b0af8fd06c1b", referenceDeDiplomeStr);
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }

    @Test
    public void testRecupererDiplomesSaisis() throws Exception {
        Response response = diplomeRest.recupererDiplomesSaisis("9e88c31c-9cdf-4b8d-964a-b0af8fd06c1b");
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }
}