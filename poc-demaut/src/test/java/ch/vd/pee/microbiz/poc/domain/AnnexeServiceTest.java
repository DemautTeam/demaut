package ch.vd.pee.microbiz.poc.domain;

import ch.vd.pee.microbiz.poc.jpa.entity.Annexe;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@ContextConfiguration({"classpath*:/persistenceTest-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AnnexeServiceTest {

    @Autowired
    private AnnexeServiceImpl annexeService;

    @Before
    public void setUp() throws Exception {
        assertNotNull(annexeService);
    }

    @Test
    public void shouldFetchAnnexes() throws Exception {
        List<Annexe> annexes = annexeService.fetchAnnexes();
        assertNotNull(annexes);
        assertFalse(annexes.isEmpty());
    }

    @Test
    public void shouldFetchAnnexeByName() throws Exception {
        Annexe annexe = annexeService.fetchAnnexeByName("Test_annexe_find.pdf");
        assertNotNull(annexe);
    }

    @Test
    public void shouldStoreAnnexe() throws Exception {
        byte[] content = "Ce fichier doit contenir un content non vide".getBytes();
        Annexe annexe = new Annexe("Test_annexe.pdf", (long)content.length, "pdf", content);
        assertTrue(annexeService.storeAnnexe(annexe));
    }
}