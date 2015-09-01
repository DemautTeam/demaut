package ch.vd.demaut.domain.annexes;

import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

/**
 * Classe de tests de la classe {@link ListeDesAnnexes}
 *
 */
public class ListeDesAnnexesTest {

	ListeDesAnnexes listeDesAnnexes = new ListeDesAnnexes();

	@Before
	public void setUp() throws Exception {
		listeDesAnnexes.ajouterAnnexe(new Annexe(TypeAnnexe.Certificat,"certificat1.pdf", null));
		listeDesAnnexes.ajouterAnnexe(new Annexe(TypeAnnexe.Certificat,"certificat2.pdf", null));
		listeDesAnnexes.ajouterAnnexe(new Annexe(TypeAnnexe.Certificat,"certificat3.pdf", null));
		listeDesAnnexes.ajouterAnnexe(new Annexe(TypeAnnexe.CV,"cv.pdf", null));
	}

	@Test
	public void testerExtraireAnnexesDeTypeAvecPlusieursAnnexes() {
		Collection<Annexe> annexesDeTypeCertificat = listeDesAnnexes.extraireAnnexesDeType(TypeAnnexe.Certificat);
		assertThat(annexesDeTypeCertificat).hasSize(3);
	}

	@Test
	public void testerExtraireAnnexesDeTypeAvecAucuneAnnexe() {
		Collection<Annexe> annexesDeTypeCertificat = listeDesAnnexes.extraireAnnexesDeType(TypeAnnexe.Diplome);
		assertThat(annexesDeTypeCertificat).hasSize(0);
	}
}
