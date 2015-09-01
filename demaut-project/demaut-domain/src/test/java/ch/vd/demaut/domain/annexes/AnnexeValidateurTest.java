package ch.vd.demaut.domain.annexes;

import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

import org.junit.Before;
import org.junit.Test;

import ch.vd.demaut.commons.utils.FileMockHelper;

public class AnnexeValidateurTest {

	private AnnexeValidateur validateur;

	@Before
	public void setUp() throws Exception {
		validateur = AnnexeValidateur.getInstance();
	}

	@Test
	public void annexeVideEstInvalide() {
		Annexe annexe = buildAnnexe(0);
		validerAnnexeEtTesterInvalide(annexe);
	}

	@Test
	public void annexeAvecTailleSuperieurAMaxEstInvalide() {
		Annexe annexe = buildAnnexe(12);
		validerAnnexeEtTesterInvalide(annexe);
	}

	@Test
	public void annexeAvecTailleEntreMinEtMaxEstValide() {
		Annexe annexe = buildAnnexe(5);
		validerAnnexeEtTesterValide(annexe);
	}
	
	private void validerAnnexeEtTesterValide(Annexe annexe) {
		try {
			validateur.valider(annexe);
		} catch (AnnexeNonValideException e) {
			fail("Failed because exception AnnexeNonValideException was thrown");
		}
	}

	private void validerAnnexeEtTesterInvalide(Annexe annexe) {
		try {
			validateur.valider(annexe);
			failBecauseExceptionWasNotThrown(AnnexeNonValideException.class);
		} catch (AnnexeNonValideException e) {
		}
	}

	private Annexe buildAnnexe(int tailleFichierEnMB) {
		byte[] contenu = FileMockHelper.buildContenuFichier(tailleFichierEnMB);
		Annexe annexe = new Annexe(TypeAnnexe.Certificat, "test.pdf", contenu);
		return annexe;
	}


}
