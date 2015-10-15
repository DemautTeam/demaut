package ch.vd.demaut.domain.annexes;

import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandeur.Pays;
import ch.vd.demaut.domain.demandeur.donneesPerso.*;
import ch.vd.demaut.domain.demandeur.donneesProf.DonneesProfessionnelles;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.*;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.*;
import ch.vd.demaut.domain.utilisateurs.Login;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
/**
 * Classe de tests de la classe {@link ListeDesAnnexes}
 */
@RunWith(JUnit4.class)
public class ListeTypeAnnexesObligatoiresTest {

    private ListeTypeAnnexesObligatoires listeTypeAnnexesObligatoires;

    private DonneesPersonnelles donneesPersonnelles;
    private DonneesProfessionnelles donneesProfessionnelles;
    private DemandeAutorisation demandeAutorisation;

    @Before
    public void setUp() throws Exception {
        Login login = new Login("admin@admin");
        Profession profession = Profession.Medecin;
        demandeAutorisation = new DemandeAutorisation(login, profession);

        donneesPersonnelles = demandeAutorisation.getDonneesPersonnelles();
        donneesProfessionnelles = demandeAutorisation.getDonneesProfessionnelles();
        listeTypeAnnexesObligatoires = new ListeTypeAnnexesObligatoires(demandeAutorisation);

        initDonneesPersonnelles();
        initDonneesProfessionnelles();
    }

    @Test
    public void testDeterminerListeTypeAnnexesObligatoires() throws Exception {
        ListeTypeAnnexesObligatoires listeTypeAnnexesObligatoires = this.listeTypeAnnexesObligatoires.determinerListeTypeAnnexesObligatoires();
        assertThat(listeTypeAnnexesObligatoires).isNotNull();
        List<TypeAnnexe> typesAnnexeObligatoires = listeTypeAnnexesObligatoires.listerTypesAnnexeObligatoires();
        assertThat(typesAnnexeObligatoires).isNotNull();
        assertThat(typesAnnexeObligatoires).hasSize(13);

        assertThat(typesAnnexeObligatoires).contains(TypeAnnexe.CV);
        assertThat(typesAnnexeObligatoires).contains(TypeAnnexe.Diplome);
        assertThat(typesAnnexeObligatoires).contains(TypeAnnexe.Titre);
        assertThat(typesAnnexeObligatoires).contains(TypeAnnexe.ExtraitCasierJudiciaire);
        assertThat(typesAnnexeObligatoires).contains(TypeAnnexe.AttestationNiveauFrancais);
        assertThat(typesAnnexeObligatoires).contains(TypeAnnexe.AttestationBonneConduite);
        assertThat(typesAnnexeObligatoires).contains(TypeAnnexe.CertificatMedical);
        assertThat(typesAnnexeObligatoires).contains(TypeAnnexe.ResponsabiliteCivile);
        assertThat(typesAnnexeObligatoires).contains(TypeAnnexe.PieceIdentite);
        assertThat(typesAnnexeObligatoires).contains(TypeAnnexe.Equivalence);
        assertThat(typesAnnexeObligatoires).contains(TypeAnnexe.Originaux);
        assertThat(typesAnnexeObligatoires).contains(TypeAnnexe.Recusation);
        assertThat(typesAnnexeObligatoires).contains(TypeAnnexe.SpecimenSignature);

        assertThat(typesAnnexeObligatoires).doesNotContain(TypeAnnexe.CertificatDeTravail);

    }

    private void initDonneesPersonnelles() {
        Nom nom = new Nom("Doe");
        Prenom prenom = new Prenom("John");
        Localite localite = new Localite("Lausanne");
        NPA npa = new NPA("1000");
        Pays pays = Pays.Suisse;
        Adresse adresse = new Adresse("", "", localite, npa, pays);
        Email email = new Email("john.doe@nobody.com");
        Genre genre = Genre.Feminin;
        DateDeNaissance dateDeNaissance = new DateDeNaissance(new LocalDate());
        TelephonePrive telephonePrive = new TelephonePrive("022222222");
        TelephoneMobile telephoneMobile = new TelephoneMobile("07625225123");
        Fax fax = new Fax("023456789");
        Pays nationalite = Pays.Allemagne;
        Langue langue = Langue.Allemand;
        Permis permis = new Permis(TypePermis.C);

        donneesPersonnelles.renseignerLesDonneesPersonnelles(nom, prenom, null, adresse, email,
                telephonePrive, telephoneMobile, fax, genre, dateDeNaissance, nationalite, langue, permis);
    }

    private void initDonneesProfessionnelles() {
        creerListeDiplomes();
        creerListeActiviteFutures();
    }

    private void creerListeDiplomes() {
        donneesProfessionnelles.validerEtAjouterDiplome(new Diplome(
                new ReferenceDeDiplome(UUID.randomUUID().toString()), TypeDiplomeAccepte.D_FORMATION_APPROFONDIE,
                new TitreFormation(TitreFormationApprofondieProgres.PneumologiePediatrique.name()), null,
                new DateObtention(new LocalDate()), new PaysObtention(Pays.Suisse.name()), null));
        donneesProfessionnelles.validerEtAjouterDiplome(new Diplome(
                new ReferenceDeDiplome(UUID.randomUUID().toString()), TypeDiplomeAccepte.D_FORMATION_INITIALE,
                new TitreFormation(TitreFormationInitialeProgres.CFRDUnDiplomeEtrangerDeMedecin.name()), null,
                new DateObtention(new LocalDate()), new PaysObtention(Pays.Allemagne.name()),
                new DateReconnaissance(new LocalDate())));
        donneesProfessionnelles.validerEtAjouterDiplome(
                new Diplome(new ReferenceDeDiplome(UUID.randomUUID().toString()), TypeDiplomeAccepte.D_POSTGRADE,
                        new TitreFormation(TitreFormationPostgradeProgres.Cardiologie.name()), null,
                        new DateObtention(new LocalDate()), new PaysObtention(Pays.Suisse.name()), null));
    }

    private void creerListeActiviteFutures() {
        donneesProfessionnelles.validerEtAjouterActiviteFuture(
                new ActiviteFuture(new ReferenceDeActivite(UUID.randomUUID().toString()), TypeActivite.Dependant, new Etablissement(), TypePratiqueLamal.Non)
        );
        donneesProfessionnelles.validerEtAjouterActiviteFuture(
                new ActiviteFuture(new ReferenceDeActivite(UUID.randomUUID().toString()), TypeActivite.Independant, new Etablissement(), TypePratiqueLamal.OuiATitreDependant)
        );
        donneesProfessionnelles.validerEtAjouterActiviteFuture(
                new ActiviteFuture(new ReferenceDeActivite(UUID.randomUUID().toString()), TypeActivite.Dependant, new Etablissement(), TypePratiqueLamal.OuiATitreIndependant)
        );
    }

}
