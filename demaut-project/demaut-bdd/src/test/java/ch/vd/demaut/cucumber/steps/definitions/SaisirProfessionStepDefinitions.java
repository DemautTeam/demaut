package ch.vd.demaut.cucumber.steps.definitions;

import ch.vd.demaut.commons.bdd.AccepteOuRefuse;
import ch.vd.demaut.cucumber.steps.DemandeAutorisationSteps;
import ch.vd.demaut.cucumber.steps.DonneesProfessionnellesSteps;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandes.autorisation.StatutDemandeAutorisation;
import cucumber.api.java.fr.Alors;
import cucumber.api.java.fr.Etantdonné;
import cucumber.api.java.fr.Lorsque;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions pour la fonctionnalité "Saisir la Profession"
 */
public class SaisirProfessionStepDefinitions extends StepDefinitions {

    // ********************************************************* Fields
    private DonneesProfessionnellesSteps donneesProfessionnellesSteps;

    // ********************************************************* Technical
    // methods

    public DonneesProfessionnellesSteps getDonneesProfessionnellesSteps() {
        return donneesProfessionnellesSteps;
    }

    public void setDonneesProfessionnellesSteps(DonneesProfessionnellesSteps donneesProfessionnellesSteps) {
        this.donneesProfessionnellesSteps = donneesProfessionnellesSteps;
    }

    public DemandeAutorisationSteps getDemandeAutorisationSteps() {
        return donneesProfessionnellesSteps.getDemandeAutorisationSteps();
    }

    // ********************************************************* Before

    // ********************************************************* Given

    @Etantdonné("^aucune demande de profession \"([^\"]*)\" en cours de saisie à l´état \"([^\"]*)\"$")
    public void aucune_demande_de_profession_en_cours_de_saisie_à_l_état(Profession profession, StatutDemandeAutorisation statut) throws Throwable {
        //TODO Decider si a virer ou pas?
    }

    @Etantdonné("^un demandeur de profession \"([^\"]*)\"$")
    public void un_demandeur_de_profession(String profession) throws Throwable {
        // TODO
    }

    @Etantdonné("^une demande de profession \"([^\"]*)\" en cours de saisie à l´état \"([^\"]*)\"$")
    public void une_demande_de_profession_en_cours_de_saisie_à_l_état(Profession profession, StatutDemandeAutorisation etat) throws Throwable {
        donneesProfessionnellesSteps.getDemandeAutorisationSteps().initialiserDemandeEnCours(profession,
                getDemandeAutorisationSteps().getCodeGlnValide());

        // Verifie si initilisation ok
        DemandeAutorisation demandeEnCours = donneesProfessionnellesSteps.getDemandeAutorisationSteps()
                .getDemandeEnCours();
        assertThat(demandeEnCours).isNotNull();
        assertThat(demandeEnCours.getStatutDemandeAutorisation()).isEqualTo(etat);
    }

    // ********************************************************* When

    @Lorsque("^l´utilisateur saisit son code \"([^\"]*)\" de \"([^\"]*)\" catactères$")
    public void l_utilisateur_saisit_son_code_gln_de_catactères(String gln, String nbChars) throws Throwable {
        // TODO
    }

    // ********************************************************* Then

    @Alors("^le système Demaut \"(accepte|refuse)\" les données professionnelles avec un \"([^\"]*)\" en cas d´échec$")
    public void le_système_Demaut_les_données_professionnelles(AccepteOuRefuse action, String message) throws Throwable {
        // TODO
    }

    @Alors("^le système Demaut crée la demande avec les données professionnelles$")
    public void le_système_Demaut_crée_la_demande_avec_les_données_professionnelles() throws Throwable {
        // TODO
    }

    @Alors("^le système Demaut crée la demande avec la donnée de la profession$")
    public void le_système_Demaut_crée_la_demande_avec_la_donnée_de_la_profession() throws Throwable {
        // TODO
    }
}
