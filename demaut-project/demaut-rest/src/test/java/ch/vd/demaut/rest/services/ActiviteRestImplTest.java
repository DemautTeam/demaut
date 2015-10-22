package ch.vd.demaut.rest.services;

import ch.vd.demaut.rest.services.impl.ActiviteRestImpl;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@Ignore("TODO Should mock @Context HttpHeaders demaut-user-id")
@ContextConfiguration({"classpath*:restTest-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ActiviteRestImplTest {

    @Autowired
    private ActiviteRestImpl activiteRest;

    @Before
    public void setUp() throws Exception {
        assertThat(activiteRest).isNotNull();
    }

    @Test
    public void testListerActivites() throws Exception {
        // TODO
    }
}