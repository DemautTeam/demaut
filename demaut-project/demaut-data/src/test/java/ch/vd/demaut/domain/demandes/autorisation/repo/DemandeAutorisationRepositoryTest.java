package ch.vd.demaut.domain.demandes.autorisation.repo;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;

@ContextConfiguration({"classpath*:/persistenceTest-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class DemandeAutorisationRepositoryTest {

    @Autowired
    private DemandeAutorisationRepository repo;

    @Before
    public void setUp() throws Exception {
        assertNotNull(repo);
    }
    
    @Test
    public void trouverDemandeAvecUnId() throws Exception {
    	DemandeAutorisation demande = repo.findBy(100L);
        assertNotNull(demande);
    }

    @Test
    public void sauverUneDemande() throws Exception {
        DemandeAutorisation demande = new DemandeAutorisation();
        demande = repo.store(demande);
        assertNotNull(demande.getId());
    }
}