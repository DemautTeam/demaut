package ch.vd.demaut.services.demandes.autorisation.repo.test;

import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.ProfessionDeLaSante;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;
import ch.vd.demaut.domain.demandeurs.Demandeur;
import ch.vd.demaut.domain.demandeurs.NomEtPrenomDemandeur;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration({"classpath*:/servicesTest-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({"mock"})
public class DemandeAutorisationRepositoryMockTest extends TestCase {

    @Autowired
    private DemandeAutorisationRepository demandeAutorisationRepository;

    private DemandeAutorisation demandeAutorisation;

    private String nom = "DALTON";
    private String prenom = "Joe";

    @Before
    public void setUp() {
        assertThat(demandeAutorisationRepository).isNotNull();

        Demandeur demandeur = new Demandeur(new NomEtPrenomDemandeur(nom, prenom));
        assertThat(demandeur).isNotNull();

        demandeAutorisation = new DemandeAutorisation(demandeur, ProfessionDeLaSante.Medecin);
        demandeAutorisation.setId(100L);
        assertThat(demandeAutorisation).isNotNull();
    }

    @Test
    public void should_find_by_demande_autorisation() throws Exception {
        demandeAutorisationRepository.store(demandeAutorisation);

        assertThat(demandeAutorisationRepository.findBy(1L)).isNotNull();
    }

    @Test
    public void should_find_all_demandes_autorisation() throws Exception {
        assertThat(demandeAutorisationRepository.findAll()).isNotEmpty();
    }

    @Test
    public void should_count_all_demandes_autorisation() throws Exception {
        demandeAutorisationRepository.store(demandeAutorisation);

        assertThat(demandeAutorisationRepository.countAll()).isNotEqualTo(0);
    }

    @Test
    public void should_store_demande_autorisation() throws Exception {
        assertThat(demandeAutorisationRepository.store(demandeAutorisation)).isNotNull();
    }

    @SuppressWarnings("all")
    @Test
    public void should_store_all_demandes_autorisation() throws Exception {
        List<DemandeAutorisation> demandeAutorisations = Arrays.asList(demandeAutorisation);
        assertThat(demandeAutorisations).isNotEmpty();

        demandeAutorisationRepository.storeAll(demandeAutorisations);
    }

    @Test
    public void should_delete_demande_autorisation() throws Exception {
        demandeAutorisationRepository.delete(demandeAutorisation);
    }

    @Test
    public void should_delete_all_demandes_autorisation() throws Exception {
        demandeAutorisationRepository.deleteAll();
    }

    @Test
    public void should_validate_demande_autorisation() throws Exception {
        assertThat(demandeAutorisationRepository.validate(demandeAutorisation)).isEmpty();
    }

    @Test
    public void should_validate_and_store_demande_autorisation() throws Exception {
        assertThat(demandeAutorisationRepository.validateAndStore(demandeAutorisation)).isNotNull();
    }

    @SuppressWarnings("all")
    @Test
    public void should_validate_and_store_all_demandes_autorisation() throws Exception {
        List<DemandeAutorisation> demandeAutorisations = Arrays.asList(demandeAutorisation);
        assertThat(demandeAutorisations).isNotEmpty();

        demandeAutorisationRepository.validateAndStoreAll(demandeAutorisations);
    }
}