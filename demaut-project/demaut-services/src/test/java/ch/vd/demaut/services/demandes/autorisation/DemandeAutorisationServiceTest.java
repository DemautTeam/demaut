package ch.vd.demaut.services.demandes.autorisation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.exception.DemandeNotFoundException;
import ch.vd.demaut.domain.exception.ReferenceDemandeNotFoundException;
import ch.vd.demaut.domain.utilisateurs.Login;

@ContextConfiguration({"classpath*:/servicesTest-context.xml"})
@ActiveProfiles({"data"})
@RunWith(SpringJUnit4ClassRunner.class)
public class DemandeAutorisationServiceTest {

    // ********************************************************* Injected
    @Autowired
    private DemandeAutorisationService demandeAutorisationService;

    
    // ********************************************************* Unique login par test
    private static Login login = new Login("login1");

    // ********************************************************* Transient Fields
    
    private Profession profession;
    private DemandeAutorisation demandeEnCours;
    private ReferenceDeDemande refEnCours;

    // ********************************************************* Setups
    @Before
    public void setUp() throws Exception {
        
        login = new Login(login.getValue() + "1" + new Date());

        profession = Profession.Podologue;

        assertThat(demandeAutorisationService).isNotNull();
        assertThat(login).isNotNull();
    }

    // ********************************************************* Tests

    @Test
    public void testerInitialiserDemandeAutorisation() {
        initDemandeEncours();
        
        assertThat(demandeEnCours).isNotNull();
        assertThat(demandeEnCours.getId()).isGreaterThan(0L);
        assertThat(demandeEnCours.getReferenceDeDemande()).isNotNull();
    }


    @Test
    public void testerRecupererDemandeParReference() {
        
        initDemandeEncours();

        //Récupère demande en cours
        demandeEnCours = demandeAutorisationService.recupererDemandeParReference(refEnCours);

        assertThat(demandeEnCours).isNotNull();
    }

    @Test
    public void testerRecupererListeBrouillons() {
        initDemandeEncours();

        //Récupère demande en cours
        List<DemandeAutorisation> demandesEnCoursList = demandeAutorisationService.recupererListeBrouillons(login);
        assertThat(demandesEnCoursList).isNotNull();
        assertThat(demandesEnCoursList).isNotEmpty();
    }

    @Test
    public void testerSupprimerUnBrouillon() {
        
        initDemandeEncours();

        try {
            demandeAutorisationService.supprimerUnBrouillon(refEnCours);
            
            demandeAutorisationService.recupererDemandeParReference(refEnCours);
            
            fail("Should rise Exception no result !");
        } catch (DemandeNotFoundException | ReferenceDemandeNotFoundException | NoResultException e) {
            // OK
        }
    }

    private void initDemandeEncours() {
        demandeEnCours = demandeAutorisationService.initialiserDemandeAutorisation(profession, null, login);
        refEnCours = demandeEnCours.getReferenceDeDemande();
    }
}