package ch.vd.demaut.domain.annexes;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import ch.vd.demaut.domain.exception.AnnexeIntrouvableException;
import ch.vd.demaut.domain.exception.AnnexeNonValideException;

import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

/**
 * Classe de tests de la classe {@link ListeDesAnnexes}
 */
@RunWith(JUnit4.class)
public class ListeDesAnnexesTest {

    private ListeDesAnnexes listeDesAnnexes;

    @Before
    public void setUp() throws Exception {
        listeDesAnnexes = new ListeDesAnnexes(new ArrayList<Annexe>());
        String dateCreation = "01.01.2015 11:00";
        listeDesAnnexes.ajouterAnnexe(new Annexe(TypeAnnexe.CertificatDeTravail, "certificat1.pdf", null, dateCreation));
        listeDesAnnexes.ajouterAnnexe(new Annexe(TypeAnnexe.CertificatDeTravail, "certificat2.pdf", null, dateCreation));
        listeDesAnnexes.ajouterAnnexe(new Annexe(TypeAnnexe.CertificatDeTravail, "certificat3.pdf", null, dateCreation));
        listeDesAnnexes.ajouterAnnexe(new Annexe(TypeAnnexe.CV, "cv.pdf", null, dateCreation));
    }

    @Test
    public void testerExtraireAnnexesDeTypeAvecPlusieursAnnexes() {
        Collection<Annexe> annexes = listeDesAnnexes.extraireAnnexesDeType(TypeAnnexe.CertificatDeTravail);
        assertThat(annexes).hasSize(3);
    }

    @Test
    public void testerExtraireAnnexesDeTypeAvecAucuneAnnexe() {
        Collection<Annexe> annexes = listeDesAnnexes.extraireAnnexesDeType(TypeAnnexe.Diplome);
        assertThat(annexes).hasSize(0);
    }

    @Test
    public void testerExtraireAnnexeMetadatasDeTypeAvecPlusieursAnnexes() {
        Collection<AnnexeMetadata> annexeMetadatas = listeDesAnnexes.extraireAnnexesMetadatasDeType(TypeAnnexe.CertificatDeTravail);
        assertThat(annexeMetadatas).hasSize(3);
    }

    @Test
    public void testerExtraireAnnexeMetadatasDeTypeAvecAucuneAnnexe() {
        Collection<AnnexeMetadata> annexeMetadatas = listeDesAnnexes.extraireAnnexesMetadatasDeType(TypeAnnexe.Diplome);
        assertThat(annexeMetadatas).hasSize(0);
    }

    @Test
    public void supprimerUneAnnexeViaNomDeFichier() {
        AnnexeFK annexeFK = new AnnexeFK(new NomFichier("certificat3.pdf"), TypeAnnexe.CertificatDeTravail);
        listeDesAnnexes.supprimerUneAnnexe(annexeFK);
        assertThat(listeDesAnnexes.listerAnnexes()).hasSize(3);
    }

    @Test
    public void supprimerUneAnnexeViaNomDeFichierQuiNexistePas() {
        try {
            AnnexeFK annexeFK = new AnnexeFK(new NomFichier("certificat5.pdf"), TypeAnnexe.CertificatDeTravail);
            listeDesAnnexes.supprimerUneAnnexe(annexeFK);
            failBecauseExceptionWasNotThrown(AnnexeNonValideException.class);
        } catch (AnnexeIntrouvableException e) {
        }
    }

}
