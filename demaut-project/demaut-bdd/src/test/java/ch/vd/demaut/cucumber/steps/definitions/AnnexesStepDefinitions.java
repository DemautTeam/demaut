package ch.vd.demaut.cucumber.steps.definitions;

import ch.vd.demaut.commons.utils.FileMockHelper;
import ch.vd.demaut.cucumber.converteurs.annexes.ListeDesAnnexesConverter;
import ch.vd.demaut.cucumber.converteurs.commons.AccepteOuRefuse;
import ch.vd.demaut.cucumber.converteurs.demandes.ReferenceDeDemandeConverter;
import ch.vd.demaut.cucumber.converteurs.utilisateurs.LoginConverter;
import ch.vd.demaut.cucumber.steps.AnnexesSteps;
import ch.vd.demaut.cucumber.steps.DemandeAutorisationSteps;
import ch.vd.demaut.domain.annexes.*;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.utilisateurs.Login;
import cucumber.api.DataTable;
import cucumber.api.Transform;
import cucumber.api.java.fr.Alors;
import cucumber.api.java.fr.Etantdonné;
import cucumber.api.java.fr.Lorsque;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions pour la fonctionnalité "@annexes"
 */
public class AnnexesStepDefinitions extends StepDefinitions {

    // ********************************************************* Fields

    private DemandeAutorisationSteps demandeAutorisationSteps;

    private AnnexesSteps annexesSteps;

    // ********************************************************* Technical
    // methods

    public DemandeAutorisationSteps getDemandeAutorisationSteps() {
        return demandeAutorisationSteps;
    }

    public void setDemandeAutorisationSteps(DemandeAutorisationSteps demandeAutorisationSteps) {
        this.demandeAutorisationSteps = demandeAutorisationSteps;
    }

    public AnnexesSteps getAnnexesSteps() {
        return annexesSteps;
    }

    public void setAnnexesSteps(AnnexesSteps annexesSteps) {
        this.annexesSteps = annexesSteps;
    }

    // ********************************************************* Before

    // ********************************************************* Given

    @Etantdonné("^l´utilisateur identifié et connecté avec le login \"([^\"]*)\"$")
    public void initialiser_utilisateur(@Transform(LoginConverter.class) Login login) throws Throwable {
        getDemandeAutorisationSteps().initialiserUtilisateur(login);
    }

    @Etantdonné("^une demande de profession \"([^\"]*)\" en cours de saisie ayant la référence \"([^\"]*)\"$")
    public void initialiserUneDemandeEnCours(Profession profession,
                                             @Transform(ReferenceDeDemandeConverter.class) ReferenceDeDemande refDemande) throws Throwable {
        getDemandeAutorisationSteps().initialiserDemandeEnCours(profession);
        getDemandeAutorisationSteps().enregistrerReferenceDemandeEnCours(refDemande);
        getAnnexesSteps().initialiserDemandeEnCours(getDemandeAutorisationSteps().getDemandeEnCours());
    }

    @Etantdonné("^les formats de fichier acceptés:$")
    public void les_formats_de_fichier_acceptés(DataTable dataTable) throws Throwable {
        List<FormatFichierAccepte> formatsAcceptes = dataTable.asList(FormatFichierAccepte.class);
        assertThat(formatsAcceptes).containsExactly(FormatFichierAccepte.values());
    }

    @Etantdonné("^la taille maximale de fichier acceptée \"([^\"]*)\"MB$")
    public void la_taille_maximale_de_fichier_acceptée(Long tailleMaxEnMB) throws Throwable {
        long tailleMaxEnOctets = tailleMaxEnMB * 1024 * 1024;
        assertThat(tailleMaxEnOctets).isEqualTo(AnnexeValidateur.getTailleMax());
    }

    @Etantdonné("^la liste des annexes initiale \"([^\"]*)\" attachées à la demande en cours$")
    public void la_liste_des_annexes_initiale_attachées_à_la_demande_en_cours(
            @Transform(ListeDesAnnexesConverter.class) ListeDesAnnexes listeDesAnnexesInitiales) throws Throwable {
        annexesSteps.ajouterAnnexesADemandeEnCours(listeDesAnnexesInitiales);
    }

    @Etantdonné("^les annexes déja saisies:$")
    public void les_annexes_déja_saisies_par_l_utilisateur(DataTable dataTable) throws Throwable {
        ListeDesAnnexes annexesSaisies = buildListeAnnexes(dataTable);
        getAnnexesSteps().ajouterAnnexesADemandeEnCours(annexesSaisies);
    }


    // ********************************************************* When

    @Lorsque("^l´utilisateur attache le fichier \"([^\"]*)\" de taille (\\d+)M de type \"([^\"]*)\"$")
    public void utilisateur_attache_le_fichier_certificat_exe(String nomFichier, Integer tailleFichierEnMB,
                                                              TypeAnnexe typeAnnexe) throws Throwable {

        creerEtAttacherAnnexe(nomFichier, tailleFichierEnMB, typeAnnexe);
    }

    // ********************************************************* Then

    @Alors("^le système Demaut \"(accepte|refuse)\" (?:d´attacher|de supprimer) cette annexe$")
    public void le_système_Demaut_accepte_ou_refuse_cette_annexe(AccepteOuRefuse action) throws Throwable {
        annexesSteps.verifieAcceptationAnnexe(action);
    }

    @Alors("^les annexes attachées à la demande sont \"([^\"]*)\"$")
    public void le_système_Demaut_annexe_le_fichier_à_la_demande(
            @Transform(ListeDesAnnexesConverter.class) ListeDesAnnexes listeDesAnnexesAttendues) throws Throwable {
        DemandeAutorisation demandeEnCours = getDemandeAutorisationSteps().getDemandeEnCours();
        Collection<Annexe> annexesDemandeEnCours = demandeEnCours.listerLesAnnexes();
        Collection<Annexe> annexesAttendues = listeDesAnnexesAttendues.listerAnnexes();
        assertThat(annexesDemandeEnCours).hasSameSizeAs(annexesAttendues);
    }

    @Alors("^les annexes attachées à la demande \"([^\"]*)\" sont:$")
    public void les_annexes_attachées_sont(@Transform(ReferenceDeDemandeConverter.class) ReferenceDeDemande refScenario, DataTable dataTable)
            throws Throwable {
        DemandeAutorisation demande = getDemandeAutorisationSteps().getDemandeViaReference(refScenario);
        List<Annexe> annexesAttendues = buildListeAnnexes(dataTable).listerAnnexes();
        List<Annexe> annexesDemande = demande.listerLesAnnexes();
        assertThat(annexesDemande).containsExactlyElementsOf(annexesAttendues);
    }

    // ******************************************************* Méthodes privées

    private void creerEtAttacherAnnexe(String nomFichier, Integer tailleFichier, TypeAnnexe typeAnnexe) {

        byte[] contenuFichier = FileMockHelper.buildContenuFichier(tailleFichier);

        Annexe annexe = new Annexe(typeAnnexe, nomFichier, contenuFichier, "01.01.2015 11:00");

        annexesSteps.attacherUneAnnexe(annexe);
    }

    private ListeDesAnnexes buildListeAnnexes(DataTable dataTable) {

        ListeDesAnnexes annexes = new ListeDesAnnexes();

        List<Map<String, String>> mappingTypesNomFichiersAnnexesSaisies = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> mappingUnTypeNomFichierAnnexesSaisies : mappingTypesNomFichiersAnnexesSaisies) {
            String typeDeLAnnexe = mappingUnTypeNomFichierAnnexesSaisies.get("Type d´annexe");
            TypeAnnexe typeAnnexe = TypeAnnexe.valueOf(typeDeLAnnexe);
            String nomFichier = mappingUnTypeNomFichierAnnexesSaisies.get("Nom du fichier");
            byte[] contenuFichier = FileMockHelper.buildContenuFichier(2);
            Annexe annexe = new Annexe(typeAnnexe, nomFichier, contenuFichier, "01.01.2015 11:00");
            annexes.ajouterAnnexe(annexe);
        }

        return annexes;
    }

}
