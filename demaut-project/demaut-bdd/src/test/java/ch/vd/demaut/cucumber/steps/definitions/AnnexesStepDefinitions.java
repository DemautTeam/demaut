package ch.vd.demaut.cucumber.steps.definitions;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import ch.vd.demaut.commons.bdd.AccepteOuRefuse;
import ch.vd.demaut.commons.utils.FileMockHelper;
import ch.vd.demaut.cucumber.converteurs.annexes.ListeDesAnnexesConverter;
import ch.vd.demaut.cucumber.converteurs.annexes.NomFichierConverter;
import ch.vd.demaut.cucumber.converteurs.commons.OuiNonConverter;
import ch.vd.demaut.cucumber.converteurs.demandes.ReferenceDeDemandeConverter;
import ch.vd.demaut.cucumber.steps.AnnexesSteps;
import ch.vd.demaut.cucumber.steps.DemandeAutorisationSteps;
import ch.vd.demaut.cucumber.steps.DonneesPersonnellesSteps;
import ch.vd.demaut.cucumber.steps.DonneesProfessionnellesSteps;
import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.AnnexeFK;
import ch.vd.demaut.domain.annexes.AnnexeValidateur;
import ch.vd.demaut.domain.annexes.ContenuAnnexe;
import ch.vd.demaut.domain.annexes.FormatFichierAccepte;
import ch.vd.demaut.domain.annexes.ListeDesAnnexes;
import ch.vd.demaut.domain.annexes.NomFichier;
import ch.vd.demaut.domain.annexes.ProcedureAnnexe;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.CategorieProfession;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandeur.Pays;
import ch.vd.demaut.domain.demandeur.donneesPerso.Permis;
import ch.vd.demaut.domain.demandeur.donneesPerso.TypePermis;
import cucumber.api.DataTable;
import cucumber.api.Transform;
import cucumber.api.java.fr.Alors;
import cucumber.api.java.fr.Etantdonné;
import cucumber.api.java.fr.Etantdonnée;
import cucumber.api.java.fr.Lorsque;

/**
 * Step definitions pour la fonctionnalité "@annexes"
 */
public class AnnexesStepDefinitions extends StepDefinitions {

    // ********************************************************* Fields

    private AnnexesSteps annexesSteps;

    transient List<TypeAnnexe> typesAnnexeObligatoires;

    // ********************************************************* Before

    // ********************************************************* Given

    @Etantdonné("^les formats de fichier acceptés:$")
    public void les_formats_de_fichier_acceptés(DataTable dataTable) {
        List<FormatFichierAccepte> formatsAcceptes = dataTable.asList(FormatFichierAccepte.class);

        assertThat(FormatFichierAccepte.values()).hasSameSizeAs(formatsAcceptes);
        assertThat(FormatFichierAccepte.values()).containsAll(formatsAcceptes);
    }

    @Etantdonné("^la taille maximale de fichier acceptée \"([^\"]*)\"MB$")
    public void la_taille_maximale_de_fichier_acceptée(Long tailleMaxEnMB) {
        long tailleMaxEnOctets = tailleMaxEnMB * 1024 * 1024;
        assertThat(tailleMaxEnOctets).isEqualTo(AnnexeValidateur.getTailleMax());
    }

    @Etantdonné("^la liste des annexes initiale \"([^\"]*)\" attachées à la demande en cours$")
    public void la_liste_des_annexes_initiale_attachées_à_la_demande_en_cours(
            @Transform(ListeDesAnnexesConverter.class) ListeDesAnnexes listeDesAnnexesInitiales) {
        annexesSteps.ajouterAnnexesADemandeEnCours(listeDesAnnexesInitiales);
    }

    @Etantdonné("^les annexes déja saisies:$")
    public void les_annexes_déja_saisies_par_l_utilisateur(DataTable dataTable) {
        ListeDesAnnexes annexesSaisies = buildListeAnnexes(dataTable);
        getAnnexesSteps().ajouterAnnexesADemandeEnCours(annexesSaisies);
    }

    @Etantdonnée("^les catégories universitaires suivantes:$")
    public void les_catégories_universitaires_suivantes(DataTable dataTable) {
        List<Profession> professionsUniversitairesAttendues = dataTable.asList(Profession.class);
        List<Profession> professionsUniversitairesReelles = Profession.listerProfessionsUniversitaires();

        assertThat(professionsUniversitairesReelles).hasSameSizeAs(professionsUniversitairesAttendues);
        assertThat(professionsUniversitairesReelles).containsAll(professionsUniversitairesAttendues);
    }

    @Etantdonné("^que le demandeur a (\\d+) activité\\(s\\) antérieure\\(s\\)$")
    public void que_le_demandeur_a_activité_s_antérieure_s(int nbActivitesAnterieures) {
        getDemandeAutorisationSteps().ajouterActivitesAnterieuresADemandeEnCours(nbActivitesAnterieures);
    }

    @Etantdonné("^que le demandeur est de nationalité \"([^\"]*)\"$")
    public void que_le_demandeur_est_de_nationalité(Pays nationalite) {
        getDonneesPersonnellesSteps().initNationaliteEtPermis(nationalite, new Permis(TypePermis.B));
        getDonneesPersonnellesSteps().attacheDonneesPersonellesALaDemandeDAutorisation();
    }

    @Etantdonné("^que l´utilisateur a (\\d+) diplome\\(s\\) étranger\\(s\\)$")
    public void que_l_utilisateur_a_diplome_étranger(int nbDiplomesEtrangers) {
        if (nbDiplomesEtrangers > 0) {
            getDonneesProfessionnellesSteps().initialiserDiplomeEnCours();
            getDonneesProfessionnellesSteps().validerEtAjouterDiplome();
        }
    }

    // ********************************************************* When

    @Lorsque("^l´utilisateur attache le fichier \"([^\"]*)\" de taille (\\d+)M$")
    public void utilisateur_attache_le_fichier(@Transform(NomFichierConverter.class) NomFichier nomFichier,
            Integer tailleFichierEnMB) {

        creerEtAttacherAnnexe(nomFichier, tailleFichierEnMB, TypeAnnexe.Aucun);
    }

    @Lorsque("^l´utilisateur supprime le fichier \"([^\"]*)\"$")
    public void l_utilisateur_supprime_le_fichier_de_type(@Transform(NomFichierConverter.class) NomFichier nomFichier) {
        supprimerAnnexe(nomFichier);
    }

    // ********************************************************* Then

    @Alors("^le système Demaut \"(accepte|refuse)\" (?:d´attacher|de supprimer) cette annexe$")
    public void le_système_Demaut_accepte_ou_refuse_cette_annexe(AccepteOuRefuse action) {
        annexesSteps.verifieAcceptationAnnexe(action);
    }

    @Alors("^les annexes attachées à la demande \"([^\"]*)\" sont:$")
    public void les_annexes_attachées_sont(@Transform(ReferenceDeDemandeConverter.class) ReferenceDeDemande refScenario,
            DataTable dataTable) {
        DemandeAutorisation demande = getDemandeAutorisationSteps().getDemandeViaReference(refScenario);
        List<Annexe> annexesAttendues = buildListeAnnexes(dataTable).listerAnnexes();
        List<Annexe> annexesDemande = demande.listerLesAnnexes();

        assertThat(annexesDemande).hasSameSizeAs(annexesAttendues);
        assertThat(annexesDemande).containsAll(annexesAttendues);
    }

    @Lorsque("^le système Demaut calcule les annexes obligatoires à fournir$")
    public void le_système_Demaut_calcule_les_annexes_obligatoires_à_fournir() {
        DemandeAutorisation demandeEnCours = getDemandeAutorisationSteps().getDemandeEnCours();
        typesAnnexeObligatoires = demandeEnCours.calculerTypesAnnexeObligatoires();
    }

    @Alors("^cette demande est dans le cas d´une procédure \"([^\"]*)\"$")
    public void cette_demande_est_dans_le_cas_d_une_procédure(ProcedureAnnexe procedure) {
        DemandeAutorisation demandeEnCours = getDemandeAutorisationSteps().getDemandeEnCours();
        ProcedureAnnexe procedureAnnnexeReelle = demandeEnCours.calculerProcedureAnnnexe();
        assertThat(procedureAnnnexeReelle).isEqualTo(procedure);
    }

    @Alors("^cette profession est de catégorie \"([^\"]*)\"$")
    public void cette_profession_est_de_catégorie(CategorieProfession categorie) {
        DemandeAutorisation demandeEnCours = getDemandeAutorisationSteps().getDemandeEnCours();
        CategorieProfession categorieProf = demandeEnCours.getProfession().getCategorie();
        assertThat(categorieProf).isEqualTo(categorie);
    }

    @Alors("^les types d´annexes obligatoires à fournir sont: CV=\"([^\"]*)\";Diplome=\"([^\"]*)\";Titre=\"([^\"]*)\";Equivalence=\"([^\"]*)\";CertificatDeTravail=\"([^\"]*)\";AttestationBonneConduite=\"([^\"]*)\";CertificatMedical=\"([^\"]*)\";AutorisationPratiquer=\"([^\"]*)\";ResponsabiliteCivile=\"([^\"]*)\";PieceIdentite=\"([^\"]*)\"$")
    public void les_types_d_annexes_obligatoires_à_fournir_sont(@Transform(OuiNonConverter.class) Boolean cv, //
            @Transform(OuiNonConverter.class) Boolean diplome, //
            @Transform(OuiNonConverter.class) Boolean titre, //
            @Transform(OuiNonConverter.class) Boolean equivalence, //
            @Transform(OuiNonConverter.class) Boolean certificatDeTravail, //
            @Transform(OuiNonConverter.class) Boolean attestationBonneConduite, //
            @Transform(OuiNonConverter.class) Boolean certificatMedical, //
            @Transform(OuiNonConverter.class) Boolean autorisationPratiquer, //
            @Transform(OuiNonConverter.class) Boolean responsabiliteCivile, //
            @Transform(OuiNonConverter.class) Boolean pieceIdentite) {
        
        assertThat(typesAnnexeObligatoires.contains(TypeAnnexe.CV)).isEqualTo(cv);
        assertThat(typesAnnexeObligatoires.contains(TypeAnnexe.Diplome)).isEqualTo(diplome);
        assertThat(typesAnnexeObligatoires.contains(TypeAnnexe.Titre)).isEqualTo(titre);
        assertThat(typesAnnexeObligatoires.contains(TypeAnnexe.Equivalence)).isEqualTo(equivalence);
        assertThat(typesAnnexeObligatoires.contains(TypeAnnexe.CertificatDeTravail)).isEqualTo(certificatDeTravail);
        assertThat(typesAnnexeObligatoires.contains(TypeAnnexe.AttestationBonneConduite))
                .isEqualTo(attestationBonneConduite);
        assertThat(typesAnnexeObligatoires.contains(TypeAnnexe.AutorisationPratiquer)).isEqualTo(autorisationPratiquer);
        assertThat(typesAnnexeObligatoires.contains(TypeAnnexe.ResponsabiliteCivile)).isEqualTo(responsabiliteCivile);
        assertThat(typesAnnexeObligatoires.contains(TypeAnnexe.PieceIdentite)).isEqualTo(pieceIdentite);
    }

    // ******************************************************* Méthodes privées

    private void creerEtAttacherAnnexe(NomFichier nomFichier, Integer tailleFichier, TypeAnnexe typeAnnexe) {

        byte[] contenuFichier = FileMockHelper.buildContenuFichier(tailleFichier);

        Annexe annexe = new Annexe(nomFichier, new ContenuAnnexe(contenuFichier));

        annexesSteps.attacherUneAnnexe(annexe);
    }

    private void supprimerAnnexe(NomFichier nomFichier) {

        AnnexeFK annexeFK = new AnnexeFK(nomFichier);

        annexesSteps.supprimerAnnexe(annexeFK);

    }

    private ListeDesAnnexes buildListeAnnexes(DataTable dataTable) {

        ListeDesAnnexes annexes = new ListeDesAnnexes();

        List<Map<String, String>> mappingTypesNomFichiersAnnexesSaisies = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> mappingUnTypeNomFichierAnnexesSaisies : mappingTypesNomFichiersAnnexesSaisies) {
            String nomFichier = mappingUnTypeNomFichierAnnexesSaisies.get("Nom du fichier");
            byte[] bytes = FileMockHelper.buildContenuFichier(2);
            Annexe annexe = new Annexe(new NomFichier(nomFichier), new ContenuAnnexe(bytes));
            annexes.ajouterAnnexe(annexe);
        }

        return annexes;
    }

    // ********************************************************* Technical
    // methods

    public DemandeAutorisationSteps getDemandeAutorisationSteps() {
        return annexesSteps.getDemandeAutorisationSteps();
    }

    public DonneesPersonnellesSteps getDonneesPersonnellesSteps() {
        return annexesSteps.getDonneesPersonnellesSteps();
    }

    public DonneesProfessionnellesSteps getDonneesProfessionnellesSteps() {
        return annexesSteps.getDonneesProfessionnellesSteps();
    }

    public AnnexesSteps getAnnexesSteps() {
        return annexesSteps;
    }

    public void setAnnexesSteps(AnnexesSteps annexesSteps) {
        this.annexesSteps = annexesSteps;
    }

}
