package ch.vd.demaut.services.demandes.autorisation.service.test;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration({"classpath*:/servicesTest-context.xml"})
@ActiveProfiles({"data"})
@RunWith(SpringJUnit4ClassRunner.class)
public class DemandeAutorisationServiceTest extends TestCase {

    // ********************************************************* Injected
    @Autowired
    private DemandeAutorisationService demandeAutorisationService;

    private Profession profession;
    private DemandeAutorisation demandeEnCours;


    // ********************************************************* Setups
    @Before
    public void setUp() throws Exception {
        profession = Profession.Medecin;

        assertThat(demandeAutorisationService).isNotNull();
    }

    // ********************************************************* Tests

    @Test
    @Transactional
    public void testerInitialiserDemandeAutorisation() {
        demandeEnCours = demandeAutorisationService.initialiserDemandeAutorisation(profession, null);
        assertThat(demandeEnCours).isNotNull();
        assertThat(demandeEnCours.getId()).isGreaterThan(0L);
        assertThat(demandeEnCours.getReferenceDeDemande()).isNotNull();
    }

    @Test
    @Transactional
    public void testerRecupererDemandeParReference() {
        //Setup fixtures
        demandeEnCours = demandeAutorisationService.initialiserDemandeAutorisation(profession, null);
        ReferenceDeDemande referenceDeDemande = demandeEnCours.getReferenceDeDemande();

        //Récupère demande en cours
        demandeEnCours = demandeAutorisationService.recupererDemandeParReference(referenceDeDemande);

        assertThat(demandeEnCours).isNotNull();
    }
}