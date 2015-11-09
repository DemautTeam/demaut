package ch.vd.demaut.rest.json;

import java.util.ArrayList;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

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

public class ActivitesFuturesJSonConversionTest  extends AbstractJSonConversionTest  {

    @Before
    public void setUp() throws Exception {
        super.setUp();;
    }

    @Test
    public void testActiviteFutureJson() {
        //Build a valid activite future
        ActiviteFuture activiteFuture = buildActiviteFutureValide();

        String jsonStrExpected = "{\"id\":null,\"version\":0,\"ordre\":1," +
                "\"etablissement\":{\"nomEtablissement\":\"Centre medical\",\"voie\":\"2\",\"complement\":null," +
                "\"localite\":\"Lausanne\",\"npaProfessionnel\":\"1234\",\"telephoneProf\":\"0123456\"," +
                "\"telephoneMobile\":\"0123456\",\"fax\":\"0123456\",\"email\":\"toto@titi.com\"," +
                "\"siteInternet\":\"www.google.com\"},\"typePratiqueLamal\":\"Non\"," +
                "\"activiteEnvisagee\":{\"typeActivite\":\"Dependant\",\"nombreJourParSemaine\":1," +
                "\"datePrevueDebut\":1443650400000,\"superviseur\":\"superviseur\"},\"functionalKey\":{\"ordre\":1}}";
        
        assertJsonStr(activiteFuture, jsonStrExpected);
    }


    private ActiviteFuture buildActiviteFutureValide() {

        ListeDesActivitesFutures listeDesActivitesFutures = new ListeDesActivitesFutures(new ArrayList<ActiviteFuture>());

        Etablissement etablissement = new Etablissement(new Nom("Centre medical"), new Voie("2"), null,
                new Localite("Lausanne"), new NPAProfessionnel("1234"), new Telephone("0123456"),
                new Telephone("0123456"), new Telephone("0123456"), new Email("toto@titi.com"),
                new SiteInternet("www.google.com"));
        ActiviteEnvisagee activiteEnvisagee = new ActiviteEnvisagee(TypeActivite.Dependant,
                new TauxActiviteEnDemiJournee(1), new DatePrevueDebut(new LocalDate(2015, 10, 1)),
                new Superviseur("superviseur"));
        ActiviteFuture activiteFuture = new ActiviteFuture(etablissement, TypePratiqueLamal.Non, activiteEnvisagee);

        activiteFuture.genererOrdre(listeDesActivitesFutures);

        return activiteFuture;
    }


}
