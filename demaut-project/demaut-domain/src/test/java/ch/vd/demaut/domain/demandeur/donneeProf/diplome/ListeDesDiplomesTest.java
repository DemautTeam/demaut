package ch.vd.demaut.domain.demandeur.donneeProf.diplome;

import ch.vd.demaut.domain.demandeur.Pays;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.*;
import ch.vd.demaut.domain.exception.DiplomeIntrouvableException;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

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
        listeDesDiplomes.ajouterUnDiplome(
                new Diplome(new ReferenceDeDiplome(UUID.randomUUID().toString()), TypeDiplomeAccepte.D_FORMATION_APPROFONDIE,
                        new TitreFormation(TitreFormationApprofondieProgres.PneumologiePediatrique.name()), null,
                        new DateObtention(new LocalDate()), Pays.Suisse, null));
        listeDesDiplomes.ajouterUnDiplome(
                new Diplome(new ReferenceDeDiplome(UUID.randomUUID().toString()), TypeDiplomeAccepte.D_FORMATION_INITIALE,
                        new TitreFormation(TitreFormationInitialeProgres.CFRDUnDiplomeEtrangerDeMedecin.name()), null,
                        new DateObtention(new LocalDate()), Pays.Allemagne, new DateReconnaissance(new LocalDate())));
        listeDesDiplomes.ajouterUnDiplome(
                new Diplome(new ReferenceDeDiplome(UUID.randomUUID().toString()), TypeDiplomeAccepte.D_POSTGRADE,
                        new TitreFormation(TitreFormationPostgradeProgres.Cardiologie.name()), null,
                        new DateObtention(new LocalDate()), Pays.Suisse, null));
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
        listeDesDiplomes.supprimerUnDiplomeParTypeEtTitre(TypeDiplomeAccepte.D_FORMATION_APPROFONDIE, new TitreFormation(TitreFormationApprofondieProgres.PneumologiePediatrique.name()));
        assertThat(listeDesDiplomes.listerDiplomes()).hasSize(2);
    }

    @Test
    public void supprimerUneAnnexeViaNomDeFichierQuiNexistePas() {
        try {
            listeDesDiplomes.supprimerUnDiplomeParTypeEtTitre(TypeDiplomeAccepte.D_FORMATION_APPROFONDIE, new TitreFormation("PneumologieAdulte"));
            failBecauseExceptionWasNotThrown(DiplomeIntrouvableException.class);
        } catch (DiplomeIntrouvableException e) {
        }
    }

}
