package ch.vd.demaut.microbiz.rest;

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

@Ignore("Senario Data")
@ContextConfiguration({"classpath*:microbizTest-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProfessionRestImplTest {

    @Autowired
    private ProfessionRest professionRest;

    @Before
    public void setUp() throws Exception {
        assertNotNull(professionRest);
    }

    @Test
    public void testListerLesProfessionsDeLaSante() throws Exception {
        Response response = professionRest.listerLesProfessionsDeLaSante();
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }

    @Test
    public void testAfficherDonneesProfession() throws Exception {
        Response response = professionRest.afficherDonneesProfession("7dc53df5-703e-49b3-8670-b1c468f47f1f");
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }

    @Test
    public void testRenseignerDonneesProfession() throws Exception {
        Response response = professionRest.renseignerDonneesProfession("7dc53df5-703e-49b3-8670-b1c468f47f1f", "53843599", "7601000000125");
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }
}