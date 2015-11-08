package ch.vd.demaut.services.demandeurs.donneesProf.activites;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.vd.demaut.domain.demandeur.Email;
import ch.vd.demaut.domain.demandeur.Localite;
import ch.vd.demaut.domain.demandeur.Telephone;
import ch.vd.demaut.domain.demandeur.donneesPerso.Nom;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.ActiviteFuture;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.Etablissement;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.ListeDesActivitesFutures;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.NPAProfessionnel;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.SiteInternet;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.TypeActivite;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.TypePratiqueLamal;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.Voie;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.envisagee.ActiviteEnvisagee;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.envisagee.DatePrevueDebut;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.envisagee.Superviseur;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.envisagee.TauxActiviteEnDemiJournee;
import ch.vd.demaut.services.test.AbstractServiceTest;

@ContextConfiguration({ "classpath*:/servicesTest-context.xml" })
@ActiveProfiles({ "data" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ActivitesFuturesServiceTest extends AbstractServiceTest {

    @Autowired
    private ActivitesFuturesService activitesFuturesService;

    List<ActiviteFuture> activitesFuturesInit;

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void testerListerActivitesFutures() {
        // Setup fixtures
        creerDemandeEnCoursAvecUneActiviteFuture();

        verifierActivitesFutures(activitesFuturesInit);
    }

    @Test
    public void testerSupprimerUneActiviteFuture() {
        // Setup fixtures
        creerDemandeEnCoursAvecUneActiviteFuture();

        ActiviteFuture activiteFuture0 = activitesFuturesInit.get(0);
        activitesFuturesService.supprimerActiviteFuture(referenceDemandeEnCours, activiteFuture0.getFunctionalKey());

        verifierActivitesFutures(new ArrayList<ActiviteFuture>());
    }

    // ********************************************************* Metodes privees

    private void creerDemandeEnCoursAvecUneActiviteFuture() {
        creerDemandeEnCours();

        activitesFuturesInit = buildActivitesFuturesAvecUneActiviteValide();

        for (ActiviteFuture a : activitesFuturesInit) {
            activitesFuturesService.ajouterActiviteFuture(referenceDemandeEnCours, a);
        }
    }

    private List<ActiviteFuture> buildActivitesFuturesAvecUneActiviteValide() {
        ArrayList<ActiviteFuture> activitesFutures = new ArrayList<ActiviteFuture>();

        Etablissement etablissement = new Etablissement(new Nom("Centre medical"), new Voie("2"), null,
                new Localite("Lausanne"), new NPAProfessionnel("1234"), new Telephone("0123456"),
                new Telephone("0123456"), new Telephone("0123456"), new Email("toto@titi.com"),
                new SiteInternet("www.google.com"));
        ActiviteEnvisagee activiteEnvisagee = new ActiviteEnvisagee(TypeActivite.Dependant,
                new TauxActiviteEnDemiJournee(1), new DatePrevueDebut(new LocalDate(2015, 10, 1)),
                new Superviseur("superviseur"));
        ActiviteFuture activiteFuture = new ActiviteFuture(etablissement, TypePratiqueLamal.Non, activiteEnvisagee);

        activitesFutures.add(activiteFuture);

        return activitesFutures;
    }

    private void verifierActivitesFutures(List<ActiviteFuture> activitesFuturesAttendues) {
        
        ListeDesActivitesFutures listeActivitesFutures = activitesFuturesService
                .listerLesActivitesFutures(referenceDemandeEnCours);
        List<ActiviteFuture>  activitesReelles = listeActivitesFutures.listerActivitesFutures();

        assertThat(activitesReelles).hasSameSizeAs(activitesFuturesAttendues);
        assertThat(activitesReelles).containsAll(activitesFuturesAttendues);
    }

}
