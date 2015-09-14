package ch.vd.demaut.services.demandes.autorisation.service.test;

import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.ProfessionDeLaSante;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration({"classpath*:/servicesTest-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({"mock"})
public class DemandeAutorisationServiceMockTest extends TestCase {

    @Autowired
    private DemandeAutorisationService demandeAutorisationService;

    @Before
    public void setUp() {
        assertThat(demandeAutorisationService).isNotNull();
    }

    @Ignore
    @Test
    public void shouldInitialiserDemandeAutorisation() throws Exception {
        DemandeAutorisation demandeAutorisation = demandeAutorisationService.initialiserDemandeAutorisation();
        assertThat(demandeAutorisation).isNotNull();
    }

    @Ignore
    @Test
    public void shouldSauverLaDemandeAutorisation() throws Exception {
    	DemandeAutorisation demandeAutorisation = new DemandeAutorisation(null, ProfessionDeLaSante.Medecin, null);
        demandeAutorisationService.sauverLaDemandeAutorisation(demandeAutorisation);
        assertThat(demandeAutorisation).isNotNull();
    }
}