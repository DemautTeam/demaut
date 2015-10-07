package ch.vd.demaut.microbiz.rest;

import ch.vd.demaut.domain.demandeur.Pays;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.TitreFormationPostgradeProgres;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.TypeDiplomeAccepte;
import ch.vd.demaut.microbiz.rest.impl.DiplomeRestImpl;
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

@ContextConfiguration({"classpath*:microbizTest-context.xml"})
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
        Response response = diplomeRest.listerLesTitresFormations(String.valueOf(TypeDiplomeAccepte.D_POSTGRADE.getRefProgresID().getId()));
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }

    @Test
    public void testListerLesPays() throws Exception {
        Response response = diplomeRest.listerLesPays();
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }

    @Ignore("Should mock @Context HttpHeaders demaut-user-id")
    @Test
    public void testAjouterUnDiplome() throws Exception {
        Response response = diplomeRest.ajouterUnDiplome(referenceDeDiplomeStr,
                String.valueOf(TypeDiplomeAccepte.D_POSTGRADE.getRefProgresID().getId()),
                String.valueOf(TitreFormationPostgradeProgres.Cardiologie.getRefProgresID().getId()), null,
                new LocalDate().toString(), String.valueOf(Pays.Suisse.getRefProgresID().getId()), null);
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }

    @Ignore("Should mock @Context HttpHeaders demaut-user-id")
    @Test
    public void testSupprimerUnDiplome() throws Exception {
        Response response = diplomeRest.supprimerUnDiplome(referenceDeDiplomeStr);
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }
}