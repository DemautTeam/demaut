package ch.vd.ses.demaut.dao;

import ch.vd.ses.demaut.dao.entity.Annexe;
import ch.vd.ses.demaut.dao.repository.AnnexeDemautRepository;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.junit.Assert.assertNotNull;

@ContextConfiguration({"classpath*:/persistenceTest-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AnnexeDemautRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private AnnexeDemautRepository annexeDemautRepository;

    @Before
    public void setUp() throws Exception {
        assertNotNull(entityManager);
        assertNotNull(annexeDemautRepository);
    }

    @Test
    @Transactional
    public void shouldFindAnnexeById() throws Exception {
        Annexe annexe = annexeDemautRepository.find((long) 123);
        //assertNotNull(annexe);
    }

    @Test
    @Transactional
    public void shouldSaveNewAnnexe() throws Exception {
        byte[] content = "Ce fichier doit contenir un content non vide".getBytes();
        Annexe annexe = new Annexe("Test_annexe.pdf", (long)content.length, "pdf", content);
        annexe = annexeDemautRepository.save(annexe);
        List<Annexe> annexes = annexeDemautRepository.findAll();
        //assertNotNull(annexe.getId());
    }
}