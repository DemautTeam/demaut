package ch.vd.demaut.poc.jpa;

import ch.vd.demaut.poc.jpa.entity.Annexe;
import ch.vd.demaut.poc.jpa.repository.AnnexeDemautRepository;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@ContextConfiguration({"classpath*:/persistenceTest-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AnnexeDemautRepositoryTest {

    @Autowired
    private AnnexeDemautRepository annexeDemautRepository;

    @Before
    public void setUp() throws Exception {
        assertNotNull(annexeDemautRepository);
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
}