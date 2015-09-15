package ch.vd.demaut.domain.annexes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Classe de tests de la classe {@link ListeDesAnnexes}
 */
@RunWith(JUnit4.class)
public class ListeDesAnnexesTest {

	ListeDesAnnexes listeDesAnnexes;

	@Before
	public void setUp() throws Exception {
		List<Annexe> annexes = new ArrayList<Annexe>();
		listeDesAnnexes = new ListeDesAnnexes(annexes);
		listeDesAnnexes.ajouterAnnexe(new Annexe(TypeAnnexe.Certificat, "certificat1.pdf", null));
		listeDesAnnexes.ajouterAnnexe(new Annexe(TypeAnnexe.Certificat, "certificat2.pdf", null));
		listeDesAnnexes.ajouterAnnexe(new Annexe(TypeAnnexe.Certificat, "certificat3.pdf", null));
		listeDesAnnexes.ajouterAnnexe(new Annexe(TypeAnnexe.CV, "cv.pdf", null));
	}

	@Test
	public void testerExtraireAnnexesDeTypeAvecPlusieursAnnexes() {
		Collection<Annexe> annexes = listeDesAnnexes.extraireAnnexesDeType(TypeAnnexe.Certificat);
		assertThat(annexes).hasSize(3);
	}

	@Test
	public void testerExtraireAnnexesDeTypeAvecAucuneAnnexe() {
		Collection<Annexe> annexes = listeDesAnnexes.extraireAnnexesDeType(TypeAnnexe.Diplome);
		assertThat(annexes).hasSize(0);
	}

	@Test
	public void testerExtraireAnnexeMetadatasDeTypeAvecPlusieursAnnexes() {
		Collection<AnnexeMetadata> annexeMetadatas = listeDesAnnexes
				.extraireAnnexesMetadatasDeType(TypeAnnexe.Certificat);
		assertThat(annexeMetadatas).hasSize(3);
	}

	@Test
    public void testerExtraireAnnexeMetadatasDeTypeAvecAucuneAnnexe() {
        Collection<AnnexeMetadata> annexeMetadatas = listeDesAnnexes.extraireAnnexesMetadatasDeType(TypeAnnexe.Diplome);
        assertThat(annexeMetadatas).hasSize(0);
    }

	@Test
	public void supprimerUneAnnexeViaNomDeFichier() {
		listeDesAnnexes.supprimerUneAnnexeParNomFichier("certificat3.pdf");
		assertThat(listeDesAnnexes.listerAnnexes()).hasSize(3);
	}

	@Test
	public void supprimerUneAnnexeViaNomDeFichierQuiNexistePas() {
		try {
			listeDesAnnexes.supprimerUneAnnexeParNomFichier("certificat5.pdf");
			failBecauseExceptionWasNotThrown(AnnexeNonValideException.class);
		} catch (AnnexeIntrouvableException e) {
		}
	}

}
