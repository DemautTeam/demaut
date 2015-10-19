package ch.vd.demaut.rest.services;

import ch.vd.demaut.rest.services.impl.ActiviteRestImpl;
import ch.vd.demaut.rest.services.impl.SharedRestImpl;
import junit.framework.TestCase;
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
import static org.junit.Assert.assertTrue;

@Ignore("TODO Should mock @Context HttpHeaders demaut-user-id")
@ContextConfiguration({"classpath*:restTest-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SharedRestImplTest {

    @Autowired
    private SharedRestImpl sharedRest;

    @Before
    public void setUp() throws Exception {
        assertThat(sharedRest).isNotNull();
    }
    @Test
    public void testListerLesNationalites() throws Exception {
        Response response = sharedRest.listerLesNationalites();
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }

    @Test
    public void testListerLesLangues() throws Exception {
        Response response = sharedRest.listerLesLangues();
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }

    @Test
    public void testListerLesPays() throws Exception {
        Response response = sharedRest.listerLesPays();
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
    }
}