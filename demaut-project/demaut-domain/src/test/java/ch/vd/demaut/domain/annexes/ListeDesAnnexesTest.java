package ch.vd.demaut.domain.annexes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import ch.vd.demaut.commons.exceptions.EntityNotFoundException;

/**
 * Classe de tests de la classe {@link ListeDesAnnexes}
 */
@RunWith(JUnit4.class)
public class ListeDesAnnexesTest {

    private ListeDesAnnexes listeDesAnnexes;

    @Before
    public void setUp() throws Exception {
        listeDesAnnexes = new ListeDesAnnexes(new ArrayList<Annexe>());
        listeDesAnnexes.ajouterAnnexe(new Annexe(new NomFichier("certificat1.pdf"), null));
        listeDesAnnexes.ajouterAnnexe(new Annexe(new NomFichier("certificat2.pdf"), null));
        listeDesAnnexes.ajouterAnnexe(new Annexe(new NomFichier("certificat3.pdf"), null));
        listeDesAnnexes.ajouterAnnexe(new Annexe(new NomFichier("cv.pdf"), null));
    }

    @Test
    public void supprimerUneAnnexeViaNomDeFichier() {
        AnnexeFK annexeFK = new AnnexeFK(new NomFichier("certificat3.pdf"));
        listeDesAnnexes.supprimerUneAnnexe(annexeFK);
        assertThat(listeDesAnnexes.listerAnnexes()).hasSize(3);
    }

    @Test
    public void supprimerUneAnnexeViaNomDeFichierQuiNexistePas() {
        try {
            AnnexeFK annexeFK = new AnnexeFK(new NomFichier("certificat5.pdf"));
            listeDesAnnexes.supprimerUneAnnexe(annexeFK);
            failBecauseExceptionWasNotThrown(EntityNotFoundException.class);
        } catch (EntityNotFoundException e) {
        }
    }
}
