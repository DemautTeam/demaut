package ch.vd.demaut.services.demandes.autorisation.service.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
import junit.framework.TestCase;

@ContextConfiguration({ "classpath*:/servicesTest-context.xml" })
@ActiveProfiles({ "data" })
@RunWith(SpringJUnit4ClassRunner.class)
public class DemandeAutorisationServiceTest extends TestCase {

	@Autowired
	private DemandeAutorisationService demandeAutorisationService;

	@Before
	public void setUp() {
		assertThat(demandeAutorisationService).isNotNull();
	}

	@Test
	public void should_initialiser_demande_autorisation() throws Exception {
		DemandeAutorisation demandeAutorisation = demandeAutorisationService.initialiserDemandeAutorisation();
		assertThat(demandeAutorisation).isNotNull();
	}

	@Test
	public void should_sauvegarder_demande_autorisation() throws Exception {
		DemandeAutorisation demandeAutorisation = demandeAutorisationService
				.sauvegarderDemandeAutorisation(new DemandeAutorisation());
		assertThat(demandeAutorisation).isNotNull();
	}
}