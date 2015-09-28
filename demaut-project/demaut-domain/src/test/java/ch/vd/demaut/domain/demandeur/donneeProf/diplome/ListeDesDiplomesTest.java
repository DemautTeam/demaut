package ch.vd.demaut.domain.demandeur.donneeProf.diplome;

import ch.vd.demaut.domain.demandeur.donneesProf.diplome.*;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

/**
 * Classe de tests de la classe {@link ListeDesDiplomes}
 */
@RunWith(JUnit4.class)
public class ListeDesDiplomesTest {

    private ListeDesDiplomes listeDesDiplomes;

    @Before
    public void setUp() throws Exception {
        listeDesDiplomes = new ListeDesDiplomes(new ArrayList<Diplome>());
        listeDesDiplomes.ajouterDiplome(
                new Diplome(TypeDiplomeAccepte.D_FORMATION_APPROFONDIE, new TitreFormation("Pneumologie pédiatrique /118"),
                        new DateObtention(new LocalDate()), new PaysObtention("Suisse"), null));
        listeDesDiplomes.ajouterDiplome(
                new Diplome(TypeDiplomeAccepte.D_FORMATION_INITIALE, new TitreFormation("CFR d'un diplôme étranger de médecin /8"),
                        new DateObtention(new LocalDate()), new PaysObtention("Tunisie"), new DateReconnaissance(new LocalDate())));
        listeDesDiplomes.ajouterDiplome(
                new Diplome(TypeDiplomeAccepte.D_POSTGRADE, new TitreFormation("Cardiologie /83"),
                        new DateObtention(new LocalDate()), new PaysObtention("Suisse"), null));
    }

    @Test
    public void testerExtraireDiplomesDeTypeAvecPlusieursAnnexes() {
        Collection<Diplome> diplomes = listeDesDiplomes.extraireDiplomesDeType(TypeDiplomeAccepte.D_FORMATION_APPROFONDIE);
        assertThat(diplomes).hasSize(1);
    }

    @Test
    public void testerExtraireAnnexesDeTypeAvecAucuneAnnexe() {
        Collection<Diplome> diplomes = listeDesDiplomes.extraireDiplomesDeType(TypeDiplomeAccepte.D_FORMATION_COMPLEMENTAIRE);
        assertThat(diplomes).hasSize(0);
    }

    @Test
    public void supprimerUneAnnexeViaNomDeFichier() {
        listeDesDiplomes.supprimerUnDiplomeParTypeEtTitre(TypeDiplomeAccepte.D_FORMATION_APPROFONDIE, new TitreFormation("Pneumologie pédiatrique /118"));
        assertThat(listeDesDiplomes.listerDiplomes()).hasSize(2);
    }

    @Test
    public void supprimerUneAnnexeViaNomDeFichierQuiNexistePas() {
        try {
            listeDesDiplomes.supprimerUnDiplomeParTypeEtTitre(TypeDiplomeAccepte.D_FORMATION_APPROFONDIE, new TitreFormation("Pneumologie adulte /118"));
            failBecauseExceptionWasNotThrown(DiplomeIntrouvableException.class);
        } catch (DiplomeIntrouvableException e) {
        }
    }

}
