package ch.vd.demaut.services.demandes.autorisation;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.exception.DemandeNotFoundException;
import ch.vd.demaut.domain.exception.ReferenceDemandeNotFoundException;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.services.demandes.autorisation.DemandeAutorisationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@ContextConfiguration({"classpath*:/servicesTest-context.xml"})
@ActiveProfiles({"data"})
@RunWith(SpringJUnit4ClassRunner.class)
public class DemandeAutorisationServiceTest {

    // ********************************************************* Injected
    @Autowired
    private DemandeAutorisationService demandeAutorisationService;

    private Profession profession;
    private DemandeAutorisation demandeEnCours;
    private Login login;

    // ********************************************************* Setups
    @Before
    public void setUp() throws Exception {
        profession = Profession.Podologue;
        login = new Login("admin@admin");

        assertThat(demandeAutorisationService).isNotNull();
        assertThat(login).isNotNull();
    }

    // ********************************************************* Tests

    @Test
    @Transactional
    public void testerInitialiserDemandeAutorisation() {
        demandeEnCours = demandeAutorisationService.initialiserDemandeAutorisation(profession, null, login);
        assertThat(demandeEnCours).isNotNull();
        assertThat(demandeEnCours.getId()).isGreaterThan(0L);
        assertThat(demandeEnCours.getReferenceDeDemande()).isNotNull();
    }

    @Test
    @Transactional
    public void testerRecupererDemandeParReference() {
        demandeEnCours = demandeAutorisationService.initialiserDemandeAutorisation(profession, null, login);
        ReferenceDeDemande referenceDeDemande = demandeEnCours.getReferenceDeDemande();

        //Récupère demande en cours
        demandeEnCours = demandeAutorisationService.recupererDemandeParReference(referenceDeDemande);

        assertThat(demandeEnCours).isNotNull();
    }

    @Test
    @Transactional
    public void testerRecupererBrouillon() {
        demandeEnCours = demandeAutorisationService.initialiserDemandeAutorisation(profession, null, login);
        //Récupère demande en cours
        demandeEnCours = demandeAutorisationService.recupererBrouillon(login);

        assertThat(demandeEnCours).isNotNull();
    }

    @Test(expected = DemandeNotFoundException.class)
    @Transactional
    public void testerRecupererBrouillonNonExistant() {
        demandeEnCours = demandeAutorisationService.recupererBrouillon(login);
        fail("Should rise DemandeNotFoundException exception !");
    }

    @Test
    @Transactional
    public void testerRecupererListeBrouillons() {
        demandeEnCours = demandeAutorisationService.initialiserDemandeAutorisation(profession, null, login);

        //Récupère demande en cours
        List<DemandeAutorisation> demandesEnCoursList = demandeAutorisationService.recupererListeBrouillons(login);
        assertThat(demandesEnCoursList).isNotNull();
        assertThat(demandesEnCoursList).isNotEmpty();
    }

    @Test
    @Transactional
    public void testerSupprimerUnBrouillon() {
        demandeEnCours = demandeAutorisationService.initialiserDemandeAutorisation(profession, null, login);
        //Récupère demande en cours
        demandeAutorisationService.supprimerUnBrouillon(login, demandeEnCours.getReferenceDeDemande());

        try {
            demandeEnCours = demandeAutorisationService.recupererDemandeParReference(demandeEnCours.getReferenceDeDemande());
            fail("Should rise Exception no result !");
        } catch (DemandeNotFoundException | ReferenceDemandeNotFoundException | NoResultException e) {
            // OK
        }
    }
}