package ch.vd.ses.demaut.poc.jpa;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.vd.ses.demaut.poc.jpa.entity.Annexe;
import ch.vd.ses.demaut.poc.jpa.entity.Annexe2;
import ch.vd.ses.demaut.poc.jpa.entity.Annexe3;
import ch.vd.ses.demaut.poc.jpa.repository.Annexe2DemautRepository;
import ch.vd.ses.demaut.poc.jpa.repository.AnnexeDemautRepository;

@ContextConfiguration({"classpath*:/persistenceTest-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AnnexeDemautRepositoryTest {

    @Autowired
    private AnnexeDemautRepository annexeDemautRepository;

    @Autowired
    private Annexe2DemautRepository annexe2DemautRepository;

    @Before
    public void setUp() throws Exception {
        assertNotNull(annexeDemautRepository);
        assertNotNull(annexe2DemautRepository);
    }

    @Test
    public void shouldFindAnnexeById() throws Exception {
        Annexe annexe = annexeDemautRepository.find((long) 100);
        assertNotNull(annexe);
    }

    @Test
    public void shouldSaveNewAnnexe() throws Exception {
        byte[] content = "Ce fichier doit contenir un content non vide".getBytes();
        Annexe annexe = new Annexe("Test_annexe.pdf", (long)content.length, "pdf", content);
        annexe = annexeDemautRepository.save(annexe);
        assertNotNull(annexe.getId());
    }
    
    @Test
    public void shouldSaveNewAnnexe2() throws Exception {
        Annexe3 annexe2 = new Annexe3();
        annexe2 = annexe2DemautRepository.save(annexe2);
        assertNotNull(annexe2.getId());
    }
    
    
}