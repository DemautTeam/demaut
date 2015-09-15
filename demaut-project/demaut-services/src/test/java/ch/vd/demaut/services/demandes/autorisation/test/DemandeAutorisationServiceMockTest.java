package ch.vd.demaut.services.demandes.autorisation.test;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.ProfessionDeLaSante;
import ch.vd.demaut.domain.utilisateurs.Login;
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

@Ignore("TODO implement DemandeAutorisationServiceMock")
@ContextConfiguration({"classpath*:/servicesTest-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({"mock"})
public class DemandeAutorisationServiceMockTest extends TestCase {

    @Autowired
    private DemandeAutorisationService demandeAutorisationService;

    private Login login;
    private ProfessionDeLaSante profession;
    private ReferenceDeDemande referenceDeDemande;

    @Before
    public void setUp() {
        referenceDeDemande = new ReferenceDeDemande("7dc53df5-703e-49b3-8670-b1c468f47f1f");
        profession = ProfessionDeLaSante.Medecin;
        login = new Login("admin@admin");
        assertThat(demandeAutorisationService).isNotNull();
    }

    @Test
    public void testInitialiserDemandeAutorisation() throws Exception {
        DemandeAutorisation demandeAutorisation = demandeAutorisationService.initialiserDemandeAutorisation(login, ProfessionDeLaSante.Medecin);
        assertThat(demandeAutorisation).isNotNull();
    }
}