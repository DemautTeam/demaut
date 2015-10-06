package ch.vd.demaut.microbiz.rest;

import ch.vd.demaut.microbiz.rest.impl.PersonelRestImpl;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration({"classpath*:microbizTest-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PersonelRestImplTest {

    private PersonelRestImpl personelRest;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testListerLesNationalites() throws Exception {

    }

    @Test
    public void testListerLesLangues() throws Exception {

    }
}