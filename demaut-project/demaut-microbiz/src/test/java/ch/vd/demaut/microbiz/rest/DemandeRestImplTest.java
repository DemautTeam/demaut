package ch.vd.demaut.microbiz.rest;

import ch.vd.demaut.microbiz.rest.impl.DemandeRestImpl;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration({"classpath*:microbizTest-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DemandeRestImplTest {

    @Autowired
    private DemandeRestImpl demandeRest;

    @Before
    public void setUp() throws Exception {
        // TODO
    }

    @Test
    public void testInitialiserDemande() throws Exception {
        // TODO
    }

    @Test
    public void testRecupererCurrentBrouillon() throws Exception {
        // TODO
    }
}