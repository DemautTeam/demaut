package ch.vd.demaut.cucumber.steps.definitions;

import ch.vd.demaut.commons.bdd.AccepteOuRefuse;
import ch.vd.demaut.cucumber.converteurs.diplomes.ListeDesFormationsConverter;
import ch.vd.demaut.cucumber.steps.DonneesProfessionnellesSteps;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.ListeDesFormations;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.TitreFormation;
import ch.vd.demaut.domain.demandeur.donneesProf.diplome.TypeDiplomeAccepte;
import cucumber.api.DataTable;
import cucumber.api.Transform;
import cucumber.api.java.fr.Alors;
import cucumber.api.java.fr.Etantdonné;
import cucumber.api.java.fr.Lorsque;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions pour la fonctionnalité "Saisir les Dipômes"
 */
public class SaisirDiplomeStepDefinitions extends StepDefinitions {

    // ********************************************************* Fields
    private DonneesProfessionnellesSteps donneesProfessionnellesSteps;

    // ********************************************************* Technical methods

    public DonneesProfessionnellesSteps getDonneesProfessionnellesSteps() {
        return donneesProfessionnellesSteps;
    }

    public void setDonneesProfessionnellesSteps(DonneesProfessionnellesSteps donneesProfessionnellesSteps) {
        this.donneesProfessionnellesSteps = donneesProfessionnellesSteps;
    }

    // ********************************************************* Before

    // ********************************************************* Given

    @Etantdonné("^les types de diplômes acceptés non vide:$")
    public void les_types_de_diplômes_acceptés_non_vide(DataTable dataTable) throws Throwable {
        List<TypeDiplomeAccepte> typeDiplomeAcceptes = dataTable.asList(TypeDiplomeAccepte.class);
        assertThat(typeDiplomeAcceptes).isNotEmpty();
        assertThat(typeDiplomeAcceptes).containsExactly(TypeDiplomeAccepte.values());
        this.donneesProfessionnellesSteps.setTypeDiplomeAcceptes(typeDiplomeAcceptes);
    }

    @Etantdonné("^l´utilisateur a déjà saisit un type de \"([^\"]*)\" dans la liste$")
    public void l_utilisateur_a_deja_saisit_un_type_de_diplome_dans_la_liste(String diplome) throws Throwable {
        assertThat(diplome).isNotEmpty();
        this.donneesProfessionnellesSteps.setTypeDiplomeSelectionne(TypeDiplomeAccepte.valueOf(diplome));

    }

    @Etantdonné("^l´utilisateur a déjà saisit le titre de formation \"([^\"]*)\"$")
    public void l_utilisateur_a_deja_saisit_le_titre_de_formation_formation(String formation) throws Throwable {
        try {
            assertThat(formation).isNotEmpty();
            this.donneesProfessionnellesSteps.setTitreFormation(new TitreFormation(formation));
        } catch (Exception | AssertionError e) {
            // Normal si l'utilisateur oublie de selectionner la formation, alors demaut refuse le diplôme
        }
    }

    @Etantdonné("^l´utilisateur a déjà saisit la date d´obtention \"([^\"]*)\" du diplôme$")
    public void l_utilisateur_a_deja_saisit_la_date_d_obtention_date_obtention_du_diplôme(String dateObtentionStr) throws Throwable {
        assertThat(dateObtentionStr).isNotEmpty();
        this.donneesProfessionnellesSteps.verifierEtRenseignerDateObtention(dateObtentionStr);
    }

    @Etantdonné("^l´utilisateur a déjà saisit la date de reconnaissance \"([^\"]*)\" du diplôme \"([^\"]*)\"$")
    public void l_utilisateur_a_deja_saisit_la_date_d_reconnaissance_du_diplôme(String dateReconnaissanceStr, String etrangerCritere) throws Throwable {
        assertThat(etrangerCritere).isNotEmpty();
        if (!StringUtils.isEmpty(dateReconnaissanceStr)) {
            this.donneesProfessionnellesSteps.verifierEtRenseignerDateReconnaissance(dateReconnaissanceStr);
        }
    }


    @Etantdonné("^l´utilisateur a déjà saisit ou pas le complément de formation \"([^\"]*)\"$")
    public void l_utilisateur_a_déjà_saisit_ou_pas_le_complément_de_formation_complement(String complement) throws Throwable {
        if (!StringUtils.isEmpty(complement)) {
            this.donneesProfessionnellesSteps.renseignerComplement(complement);
        }
    }

    // ********************************************************* When

    @Lorsque("^l´utilisateur selectionne un type de \"([^\"]*)\" dans la liste$")
    public void l_utilisateur_selectionne_un_type_de_diplome_dans_la_liste(TypeDiplomeAccepte typeDiplomeSelectionne) throws Throwable {
        assertThat(typeDiplomeSelectionne).isNotNull();
        assertThat(TypeDiplomeAccepte.values()).contains(typeDiplomeSelectionne);
        this.donneesProfessionnellesSteps.setTypeDiplomeSelectionne(typeDiplomeSelectionne);
    }

    @Lorsque("^l´utilisateur saisit la date d´obtention \"([^\"]*)\" du diplôme$")
    public void l_utilisateur_saisit_la_date_obtention_du_diplôme(String dateObtention) throws Throwable {
        this.donneesProfessionnellesSteps.verifierEtRenseignerDateObtention(dateObtention);
    }

    @Lorsque("^l´utilisateur selectionne un type de formation obtenue à l´\"([^\"]*)\" dans la liste$")
    public void l_utilisateur_selectionne_un_type_de_diplome_dans_la_liste(String etrangerCritere) throws Throwable {
        assertThat(etrangerCritere).isNotEmpty();
        this.donneesProfessionnellesSteps.setCritereDiplomeEtranger(etrangerCritere);
    }

    @Lorsque("^l´utilisateur initialise le diplôme en cours$")
    public void l_utilisateur_ajoute_le_diplôme_à_la_liste_des_diplômes() throws Throwable {
        this.donneesProfessionnellesSteps.initialiserDiplomeEnCours();
    }

    // ********************************************************* Then

    @Alors("^le système Demaut retourne la liste des \"([^\"]*)\" liéé au type de diplôme$")
    public void le_système_Demaut_retourne_la_liste_des_type_formation_liéé_au_type_de_diplôme(
            @Transform(ListeDesFormationsConverter.class) ListeDesFormations listeDesFormations) throws Throwable {
        assertThat(listeDesFormations).isNotNull();
        assertThat(listeDesFormations.listerTitreFormations()).isNotEmpty();
        this.donneesProfessionnellesSteps.setListeDesFormations(listeDesFormations);
    }

    @Alors("^le système Demaut \"(accepte|refuse)\" la date d´obtention \"([^\"]*)\" avec un \"([^\"]*)\" en cas d´échec$")
    public void le_système_Demaut_action_la_date_d_obtention_avec_un_message_en_cas_d_échec(AccepteOuRefuse action, String dateObtentionStr, String message) throws Throwable {
        try {
            this.donneesProfessionnellesSteps.verifierEtRenseignerDateObtention(dateObtentionStr);
            assertThat(action).isEqualTo(AccepteOuRefuse.accepte);
            this.donneesProfessionnellesSteps.setAcceptationDateObtention(action);
        } catch (Exception | AssertionError e) {
            assertThat(message).isNotEmpty();
            assertThat(action).isEqualTo(AccepteOuRefuse.refuse);
            this.donneesProfessionnellesSteps.setAcceptationDateObtention(AccepteOuRefuse.refuse);
        }
    }

    @Alors("^le système Demaut \"(accepte|refuse)\" la date de reconnaissance \"([^\"]*)\" avec un \"([^\"]*)\" en cas d´échec$")
    public void le_système_Demaut_action_la_date_de_reconnaissance_avec_un_message_en_cas_d_échec(AccepteOuRefuse action, String dateReconnaissanceStr, String message) throws Throwable {
        assertThat(action).isNotNull();
        assertThat(this.donneesProfessionnellesSteps.getCritereDiplomeEtranger()).isNotEmpty();
        try {
            this.donneesProfessionnellesSteps.verifierEtRenseignerDateReconnaissance(dateReconnaissanceStr);
            assertThat(action).isEqualTo(AccepteOuRefuse.accepte);
            this.donneesProfessionnellesSteps.setAcceptationDateReconnaissance(action);
        } catch (Exception | AssertionError e) {
            assertThat(message).isNotEmpty();
            assertThat(action).isEqualTo(AccepteOuRefuse.refuse);
            this.donneesProfessionnellesSteps.setAcceptationDateReconnaissance(AccepteOuRefuse.refuse);
        }
    }

    @Alors("^le système Demaut \"(accepte|refuse)\" le diplome avec un \"([^\"]*)\" en cas d´échec$")
    public void le_système_Demaut_action_le_diplome_avec_un_message_en_cas_d_échec(AccepteOuRefuse action, String message) throws Throwable {
        assertThat(action).isNotNull();
        this.donneesProfessionnellesSteps.validerEtAjouterDiplome();
    }
}
