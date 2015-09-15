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

@Ignore("Waiting for OpenJPA bug fix")
@ContextConfiguration({"classpath*:/servicesTest-context.xml"})
@ActiveProfiles({"data"})
@RunWith(SpringJUnit4ClassRunner.class)
public class DemandeAutorisationServiceTest extends TestCase {

    @Autowired
    private DemandeAutorisationService demandeAutorisationService;

    private Login login;
    private ProfessionDeLaSante profession;
    private ReferenceDeDemande referenceDeDemande;

    @Before
    public void setUp() throws Exception {
        referenceDeDemande = new ReferenceDeDemande("7dc53df5-703e-49b3-8670-b1c468f47f1f");
        profession = ProfessionDeLaSante.Medecin;
        login = new Login("admin@admin");

        assertThat(referenceDeDemande).isNotNull();
        assertThat(login).isNotNull();
        assertThat(demandeAutorisationService).isNotNull();
    }

    @Test
    public void testInitialiserDemandeAutorisation() {
        DemandeAutorisation demandeAutorisation = demandeAutorisationService.initialiserDemandeAutorisation(login, profession);
        assertThat(demandeAutorisation).isNotNull();
        assertThat(demandeAutorisation.getId()).isGreaterThan(0L);
        assertThat(demandeAutorisation.getReferenceDeDemande()).isNotNull();
    }

    @Test
    public void testAfficherUneDemandeAutorisation() {
        DemandeAutorisation demandeAutorisation = demandeAutorisationService.recupererDemandeParReference(referenceDeDemande);
        assertThat(demandeAutorisation).isNotNull();
    }
}