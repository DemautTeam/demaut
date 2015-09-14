package ch.vd.demaut.domain.dummy.repo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.demandes.autorisation.ProfessionDeLaSante;
import ch.vd.demaut.domain.demandeurs.Demandeur;
import ch.vd.demaut.domain.demandeurs.DemandeurRepository;
import ch.vd.demaut.domain.demandeurs.Login;
import ch.vd.demaut.domain.dummy.DummyDemandeAut;
import ch.vd.demaut.domain.dummy.DummyEntity;
import ch.vd.demaut.domain.dummy.DummyRef;

@ContextConfiguration({"classpath:dummy/dummy-jpa-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class DummyDemandeAutRepositoryTest {
	
	@Inject
    private DemandeurRepository demandeurRepository;

	@Inject
    private DummyEntityRepository dummyEntityRepository;

	@Inject
    private DummyDemAutRepository dummyDemAutRepository;
	
	@Inject
	private JpaTransactionManager transactionManagerDemaut;

	@Before
    public void setUp() throws Exception {
        
        assertThat(demandeurRepository).isNotNull();

        assertThat(dummyEntityRepository).isNotNull();
        assertThat(dummyDemAutRepository).isNotNull();
        
        assertThat(transactionManagerDemaut).isNotNull();
    }
    
    @Ignore
	@Test
	public void testDemandeurRepo() {
    	Login login1 = new Login("login1");
		Demandeur demandeur = new Demandeur(login1);
		demandeurRepository.store(demandeur);
		assertThat(demandeur.getId()).isNotNull();
	}
	
    @Ignore
    @Test
    public void testConfigOpenJPA() {
    	//Test 1 
    	DummyEntity dummy = new DummyEntity();
    	DummyRef dummyRef = new DummyRef(1L);
		dummy.setDummyRef(dummyRef);
    	assertThat(dummy.getId()).isNull();
    	dummyEntityRepository.store(dummy);
    	assertThat(dummy.getId()).isNotNull();
    	assertThat(dummy.getDummyRef().getRef()).isEqualTo(dummyRef.getRef());
    }
    
    @Ignore
    @Test
    public void test2() {
    	//Voir http://elnur.pro/programmatic-transaction-management-in-tests-with-spring/
    	//http://stackoverflow.com/questions/6864574/openjpa-lazy-fetching-does-not-work
    	DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
    	TransactionStatus transaction = transactionManagerDemaut.getTransaction(definition);
    	
    	//Demandeur
    	Login login1 = new Login("login1");
		Demandeur demandeur = new Demandeur(login1);
		demandeurRepository.store(demandeur);
		assertThat(demandeur.getId()).isNotNull();
		demandeur = demandeurRepository.findBy(demandeur.getId());
		
    	DummyDemandeAut d = new DummyDemandeAut(ProfessionDeLaSante.Medecin, demandeur);
		assertThat(d.getId()).isNull();
		Annexe annexe = new Annexe(TypeAnnexe.CV, null, null);
		d.ajouterAnnexe(annexe);
		dummyDemAutRepository.store(d);
    	assertThat(d.getId()).isNotNull();

    	DummyDemandeAut d2 = dummyDemAutRepository.findBy(d.getId());
    	List<Annexe> annexes = d2.getAnnexes();
    	assertThat(annexes).isNotEmpty();
    	
    	transactionManagerDemaut.commit(transaction);
    }
}