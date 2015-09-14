package ch.vd.demaut.data.demandes.autorisation.repo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.ProfessionDeLaSante;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;
import ch.vd.demaut.domain.demandeurs.Demandeur;
import ch.vd.demaut.domain.demandeurs.DemandeurRepository;
import ch.vd.demaut.domain.demandeurs.Login;

@ContextConfiguration({"classpath*:/data-jpa-test-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class DemandeAutorisationRepositoryTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DemandeAutorisationRepositoryTest.class);

	@Inject
    private DemandeAutorisationRepository demandeAutorisationRepository;

	@Inject
    private DemandeurRepository demandeurRepository;

	@Inject
	private JpaTransactionManager transactionManagerDemaut;

	@Before
    public void setUp() throws Exception {
        
		assertThat(demandeAutorisationRepository).isNotNull();
        assertThat(demandeurRepository).isNotNull();

        assertThat(transactionManagerDemaut).isNotNull();
    }
    
	@Ignore
	@Test
	public void sauvegarderUneDemande() {
    	TransactionStatus transaction = beginTransaction();

    	Demandeur demandeur = créerDemandeur();
		
		//Sauvegarder la demande
    	DemandeAutorisation d = new DemandeAutorisation(demandeur, ProfessionDeLaSante.Medecin, null);
		assertThat(d.getId()).isNull();
		demandeAutorisationRepository.store(d);
    	assertThat(d.getId()).isNotNull();
    	
    	commitTransaction(transaction);
	}
	
    
	@Test
	public void sauvegarderUneDemandeAvecAnnexes() {
    	TransactionStatus transaction = beginTransaction();

    	Demandeur demandeur = créerDemandeur();
		
		//Sauvegarder la demande
    	DemandeAutorisation d = new DemandeAutorisation(demandeur, ProfessionDeLaSante.Medecin, null);
		Annexe annexe = new Annexe(TypeAnnexe.CV, "test.pdf", new byte[1]);
    	d.validerEtAttacherAnnexe(annexe);
		assertThat(d.getId()).isNull();
		demandeAutorisationRepository.store(d);
    	assertThat(d.getId()).isNotNull();
    	Collection<Annexe> annexes = d.listerLesAnnexes();
    	assertThat(annexes).isNotEmpty();

    	//Recuperer la demande
    	DemandeAutorisation memeDemande = demandeAutorisationRepository.findBy(d.getId());
    	assertThat(memeDemande).isEqualTo(d);
    	assertThat(memeDemande.listerLesAnnexes()).isNotEmpty();
    	
    	commitTransaction(transaction);
	}

	
	private Demandeur créerDemandeur() {
		//Créer et sauvegarder un Demandeur
    	Login login1 = new Login("login1");
		Demandeur demandeur = new Demandeur(login1);
		demandeurRepository.store(demandeur);
		assertThat(demandeur.getId()).isNotNull();
		demandeur = demandeurRepository.findBy(demandeur.getId());
		return demandeur;
	}

	private void commitTransaction(TransactionStatus transaction) {
		transactionManagerDemaut.commit(transaction);
	}

	private TransactionStatus beginTransaction() {
		//Voir http://elnur.pro/programmatic-transaction-management-in-tests-with-spring/
    	//http://stackoverflow.com/questions/6864574/openjpa-lazy-fetching-does-not-work
    	DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
    	TransactionStatus transaction = transactionManagerDemaut.getTransaction(definition);
		return transaction;
	}
	

    @Ignore
    @Test
    public void shouldStoreDemandeAutorisation() throws Exception {
    	LOGGER.info("info log");
    	LOGGER.debug("debug log");
    	LOGGER.trace("trace log");
    	DemandeAutorisation demandeAutorisation = new DemandeAutorisation();
    	demandeAutorisationRepository.store(demandeAutorisation);
    	assertThat(demandeAutorisation.getId()).isNotNull();
    }

    @Ignore
    @Test
    public void shouldFindByDemandeAutorisation() throws Exception {
        DemandeAutorisation demandeAutorisation = demandeAutorisationRepository.findBy(100L);
        assertThat(demandeAutorisation).isNotNull();
    }

}