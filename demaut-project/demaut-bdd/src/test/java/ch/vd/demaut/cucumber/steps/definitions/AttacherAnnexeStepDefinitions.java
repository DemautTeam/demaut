package ch.vd.demaut.cucumber.steps.definitions;

import ch.vd.demaut.commons.utils.FileMockHelper;
import ch.vd.demaut.cucumber.converters.commons.AccepteOuRefuse;
import ch.vd.demaut.cucumber.converters.commons.OuiNonConverter;
import ch.vd.demaut.cucumber.converters.demandeurs.NomEtPrenomDemandeurConverter;
import ch.vd.demaut.cucumber.converteurs.annexes.ListeDesAnnexesConverter;
import ch.vd.demaut.cucumber.steps.DemandeAutorisationSteps;
import ch.vd.demaut.domain.annexes.*;
import ch.vd.demaut.domain.demandes.autorisation.ProfessionDeLaSante;
import ch.vd.demaut.domain.demandeurs.NomEtPrenomDemandeur;
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
 * Step definitions pour la fonctionnalité "Attacher des annexes"
 */
public class AttacherAnnexeStepDefinitions extends StepDefinitions {

    // ********************************************************* Fields

    private DemandeAutorisationSteps demandeAutorisationSteps;

    // ********************************************************* Technical
    // methods

    public DemandeAutorisationSteps getDemandeAutorisationSteps() {
        return demandeAutorisationSteps;
    }

    public void setDemandeAutorisationSteps(DemandeAutorisationSteps demandeAutorisationSteps) {
        this.demandeAutorisationSteps = demandeAutorisationSteps;
    }

    // ********************************************************* Before

    // ********************************************************* Given

    @Etantdonné("^un demandeur identifié \"([^\"]*)\"$")
    public void le_demandeur_professionnel(
            @Transform(NomEtPrenomDemandeurConverter.class) NomEtPrenomDemandeur nomEtPrenom) throws Throwable {
        demandeAutorisationSteps.initialiserDemandeur(nomEtPrenom);
    }

    @Etantdonné("^une demande de type \"([^\"]*)\" en cours de saisie$")
    public void initialiserDemandeEnCours(ProfessionDeLaSante profession) throws Throwable {
        demandeAutorisationSteps.initialiserDemandeEnCours(profession);
    }

    @Etantdonné("^les formats de fichier acceptés:$")
    public void que_les_formats_de_fichier_acceptés(DataTable arg1) throws Throwable {
        List<FormatFichierAccepte> formatsAcceptes = arg1.asList(FormatFichierAccepte.class);
        assertThat(formatsAcceptes).containsExactly(FormatFichierAccepte.values());
    }

    @Etantdonné("^la taille maximale de fichier acceptée \"([^\"]*)\"$")
    public void la_taille_maximale_de_fichier_acceptée(Long tailleMaxEnMB) throws Throwable {
        long tailleMaxEnOctets = tailleMaxEnMB * 1024 * 1024;
        assertThat(tailleMaxEnOctets).isEqualTo(AnnexeMetadataValidateur.getTailleMax());
    }

    @Etantdonné("^la liste des annexes initiale \"([^\"]*)\" attachées à la demande en cours$")
    public void la_liste_des_annexes_initiale_attachées_à_la_demande_en_cours(
            @Transform(ListeDesAnnexesConverter.class) ListeDesAnnexes listeDesAnnexesInitiales) throws Throwable {
        demandeAutorisationSteps.ajouterAnnexesADemandeEnCours(listeDesAnnexesInitiales);
    }

    @Etantdonné("^les annexes obligatoires par type de demande:$")
    public void les_annexes_obligatoires_par_type_de_demande(DataTable arg1) throws Throwable {
        List<Map<String, String>> mappingProfessionsAvecTypesAnnexeObligatoires = arg1.asMaps(String.class,
                String.class);
        for (Map<String, String> mappingUneProfessionAvecTypesAnnexeObligatoires : mappingProfessionsAvecTypesAnnexeObligatoires) {

            String professionStr = mappingUneProfessionAvecTypesAnnexeObligatoires.get("Type de demande");
            ProfessionDeLaSante profession = ProfessionDeLaSante.valueOf(professionStr);

            String annexesObligatoiresStr = mappingUneProfessionAvecTypesAnnexeObligatoires
                    .get("Types d´annexe obligatoires");
            String[] annexesObligatoiresArray = annexesObligatoiresStr.split(",");
            AnnexesObligatoires.Builder builder = new AnnexesObligatoires.Builder();
            for (String annexeObligatoireStr : annexesObligatoiresArray) {
                TypeAnnexe annexeObligatoire = TypeAnnexe.valueOf(annexeObligatoireStr);
                builder.ajouterAnnexeObligatoire(annexeObligatoire);
            }
            AnnexesObligatoires annexesObligatoires = builder.build();
            demandeAutorisationSteps.ajouterAnnexesObligatoires(profession, annexesObligatoires);
        }
    }

    // ********************************************************* When

    @Lorsque("^le demandeur attache le fichier \"([^\"]*)\" de taille (\\d+)M de type \"([^\"]*)\"$")
    public void le_demandeur_attache_le_fichier_certificat_exe(String nomFichier, Integer tailleFichierEnMB,
                                                               TypeAnnexe typeAnnexe) throws Throwable {

        creerEtAttacherAnnexe(nomFichier, tailleFichierEnMB, typeAnnexe);
    }

    @Lorsque("^le demandeur attache les annexes de type \"([^\"]*)\"$")
    public void le_demandeur_attache_les_annexes_de_type(String typesAnnexe) throws Throwable {
        String[] typesAnnexeArray = typesAnnexe.split(",");
        for (String typeAnnexeStr : typesAnnexeArray) {
            TypeAnnexe typeAnnexe = TypeAnnexe.valueOf(typeAnnexeStr);
            creerEtAttacherAnnexe("annexe.pdf", 5, typeAnnexe);
        }
    }

    // ********************************************************* Méthodes
    // privées

    private void creerEtAttacherAnnexe(String nomFichier, Integer tailleFichier, TypeAnnexe typeAnnexe) {

        byte[] contenuFichier = FileMockHelper.buildContenuFichier(tailleFichier);

        Annexe annexe = new Annexe(typeAnnexe, nomFichier, contenuFichier);

        demandeAutorisationSteps.attacherUneAnnexe(annexe);
    }

    // ********************************************************* Then

    @Alors("^le système Demaut \"(accepte|refuse)\" d´attacher cette annexe$")
    public void le_système_Demaut_accepte_ou_refuse_cette_annexe(AccepteOuRefuse action) throws Throwable {
        demandeAutorisationSteps.verifieAcceptationAnnexe(action);
    }

    @Alors("^les annexes attachées à la demande sont \"([^\"]*)\"$")
    public void le_système_Demaut_annexe_le_fichier_à_la_demande(
            @Transform(ListeDesAnnexesConverter.class) ListeDesAnnexes listeDesAnnexesAttendues) throws Throwable {
        Collection<Annexe> annexesDeLaDemande = demandeAutorisationSteps.getDemandeEnCours().listerLesAnnexes();
        Collection<Annexe> annexesAttendues = listeDesAnnexesAttendues.listerAnnexes();
        assertThat(annexesDeLaDemande).hasSameSizeAs(annexesAttendues);
        // TODO : vérifier les noms de fichier
    }

    @Alors("^toutes les annexes obligatoires sont validés: \"([^\"]*)\"$")
    public void la_liste_des_annexes_obligatoires_est(@Transform(OuiNonConverter.class) Boolean complete)
            throws Throwable {
        assertThat(demandeAutorisationSteps.getDemandeEnCours().annexesObligatoiresCompletes()).isEqualTo(complete);
    }
}
