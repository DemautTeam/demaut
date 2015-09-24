package ch.vd.demaut.cucumber.steps.definitions;

import ch.vd.demaut.commons.utils.FileMockHelper;
import ch.vd.demaut.cucumber.converteurs.commons.AccepteOuRefuse;
import ch.vd.demaut.cucumber.steps.AnnexesSteps;
import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.demandes.autorisation.ProfessionDeLaSante;
import cucumber.api.DataTable;
import cucumber.api.java.fr.Alors;
import cucumber.api.java.fr.Etantdonné;
import cucumber.api.java.fr.Lorsque;

import java.util.List;
import java.util.Map;

/**
 * Step definitions pour la fonctionnalité "Supprimer des Annexes"
 */
public class SupprimerAnnexeStepDefinitions {

    // ********************************************************* Fields

    private AnnexesSteps annexesSteps;

    // ********************************************************* Technical methods

    public AnnexesSteps getAnnexesSteps() {
        return annexesSteps;
    }

    public void setAnnexesSteps(AnnexesSteps annexesSteps) {
        this.annexesSteps = annexesSteps;
    }

    // ********************************************************* Before

    @Etantdonné("^une demande de profession \"([^\"]*)\" en cours de saisie par l´utilisateur$")
    public void initialiserLaDemandeEnCours(ProfessionDeLaSante profession) throws Throwable {
        annexesSteps.getDemandeAutorisationSteps().initialiserDemandeEnCours(profession);
    }

    @Etantdonné("^les annexes déja saisies par l'utilisateur:$")
    public void les_annexes_déja_saisies_par_l_utilisateur(DataTable dataTable) throws Throwable {
        List<Map<String, String>> mappingTypesNomFichiersAnnexesSaisies = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> mappingUnTypeNomFichierAnnexesSaisies : mappingTypesNomFichiersAnnexesSaisies) {

            String typeDeLAnnexe = mappingUnTypeNomFichierAnnexesSaisies.get("Type d'annexe");
            TypeAnnexe typeAnnexe = TypeAnnexe.valueOf(typeDeLAnnexe);
            String nomFichier = mappingUnTypeNomFichierAnnexesSaisies.get("Nom du fichier");
            byte[] contenuFichier = FileMockHelper.buildContenuFichier(2);
            Annexe annexe = new Annexe(typeAnnexe, nomFichier, contenuFichier);
            annexesSteps.attacherUneAnnexe(annexe);
        }
    }

    @Lorsque("^l´utilisateur supprime le fichier \"([^\"]*)\" de type \"([^\"]*)\"$")
    public void l_utilisateur_supprime_le_fichier_nom_fichier_de_type(String nomFichier, String typeAnnexe) throws Throwable {
        annexesSteps.supprimerAnnexe(nomFichier, typeAnnexe);
    }

    @Alors("^le système Demaut \"(accepte|refuse)\" de supprimer cette annexe$")
    public void le_système_Demaut_action_de_supprimer_cette_annexe(AccepteOuRefuse action) throws Throwable {
        annexesSteps.verifieAcceptationAnnexe(action);
    }
}
