package ch.vd.demaut.domain.demandes.autorisation;

import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class DemandeAutorisationTest extends TestCase {

    private DemandeAutorisation demandeAutorisation;
    private Annexe annexe;

    private String nomFichier = "Fichier_CV.pdf";
    private byte[] contenu = "Ce fichier doit contenir un content non vide".getBytes();

    @Before
    public void setUp() {
        demandeAutorisation = new DemandeAutorisation();
        assertThat(demandeAutorisation).isNotNull();

        annexe = new Annexe(TypeAnnexe.CV, nomFichier, contenu);
        assertThat(annexe).isNotNull();
    }

    @Test
    public void should_lister_les_annexes() throws Exception {
        assertThat(demandeAutorisation.listerLesAnnexes()).isEmpty();
    }

    @Test
    public void should_attacher_une_annexe() throws Exception {
        demandeAutorisation.attacherUneAnnexe(annexe);
        assertThat(demandeAutorisation.listerLesAnnexes()).isNotEmpty();
    }

    @Test
    public void should_get_profession() throws Exception {
        assertThat(demandeAutorisation.getProfessionDeLaSante()).isEqualTo(ProfessionDeLaSante.Medecin);
    }

    @Test
    public void should_get_date_de_soumission() throws Exception {
        assertThat(demandeAutorisation.getDateSoumissionDemande()).isNull();
    }

    @Test
    public void should_get_demandeur() throws Exception {
        assertThat(demandeAutorisation.getDemandeur()).isNull();
    }

    @Test
    public void should_get_functional_key() throws Exception {
        assertThat(demandeAutorisation.getFunctionalKey()).isNotNull();
    }
}