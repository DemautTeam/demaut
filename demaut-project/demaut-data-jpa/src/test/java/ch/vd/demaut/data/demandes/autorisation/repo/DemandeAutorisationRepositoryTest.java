package ch.vd.demaut.data.demandes.autorisation.repo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;

@ContextConfiguration({"classpath*:/data-jpa-test-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class DemandeAutorisationRepositoryTest {

    @Autowired
    private DemandeAutorisationRepository demandeAutorisationRepository;

    @Before
    public void setUp() throws Exception {
        assertThat(demandeAutorisationRepository).isNotNull();
    }

    @Ignore
    @Test
    public void shouldFindByDemandeAutorisation() throws Exception {
        DemandeAutorisation demandeAutorisation = demandeAutorisationRepository.findBy(100L);
        assertThat(demandeAutorisation).isNotNull();
    }

    @Ignore
    @Test
    public void shouldStoreDemandeAutorisation() throws Exception {
        DemandeAutorisation demandeAutorisation = new DemandeAutorisation();
        demandeAutorisation = demandeAutorisationRepository.store(demandeAutorisation);
        assertThat(demandeAutorisation.getId()).isNotNull();
    }
}