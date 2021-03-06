package ch.vd.demaut.cucumber.steps.definitions;

import ch.vd.demaut.commons.bdd.AccepteOuRefuse;
import ch.vd.demaut.cucumber.converteurs.demandes.ReferenceDeDemandeConverter;
import ch.vd.demaut.cucumber.converteurs.donneespro.CodeGLNConverter;
import ch.vd.demaut.cucumber.converteurs.utilisateurs.LoginConverter;
import ch.vd.demaut.cucumber.steps.DemandeAutorisationSteps;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandes.autorisation.StatutDemandeAutorisation;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.domain.utilisateurs.Login;
import cucumber.api.Transform;
import cucumber.api.java.fr.Alors;
import cucumber.api.java.fr.Etantdonné;
import cucumber.api.java.fr.Lorsque;

/**
 * Step definitions pour la fonctionnalité "Creation d'une Demande"
 */
public class CreationDemandeStepDefinitions extends StepDefinitions {

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

    // ********************************************************* Given

    @Etantdonné("^l´utilisateur identifié et connecté avec le login \"([^\"]*)\"$")
    public void initialiser_utilisateur(@Transform(LoginConverter.class) Login login) {
        getDemandeAutorisationSteps().initialiserUtilisateur(login);
    }

    @Etantdonné("^une demande de profession \"([^\"]*)\" en cours de saisie ayant la référence \"([^\"]*)\"$")
    public void simulerDemandeEnCours(Profession profession,
            @Transform(ReferenceDeDemandeConverter.class) ReferenceDeDemande refDemande) {
        getDemandeAutorisationSteps().simulerDemandeEnCours(profession, refDemande);
    }

    // ********************************************************* When
    @Lorsque("^l´utilisateur initialise une demande de profession \"([^\"]*)\" avec un code GLN valide$")
    public void lutilisateur_initialise_une_demande_de_profession(Profession profession) {
        getDemandeAutorisationSteps().initialiserDemandeEnCours(profession,
                demandeAutorisationSteps.getCodeGlnValide());
    }

    @Lorsque("^l´utilisateur initialise une demande de profession \"([^\"]*)\" sans code GLN$")
    public void lutilisateur_initialise_une_demande_de_profession_sans_codeGln(Profession profession) {
        getDemandeAutorisationSteps().initialiserDemandeEnCours(profession, demandeAutorisationSteps.getCodeGlnVide());
    }

    @Lorsque("^l´utilisateur initialise une demande de profession \"([^\"]*)\" avec un code GLN \"([^\"]*)\"$")
    public void lutilisateur_initialise_une_demande_de_profession_avec_codeGLN(Profession profession,
            @Transform(CodeGLNConverter.class) CodeGLN gln) throws Throwable {
        getDemandeAutorisationSteps().initialiserDemandeEnCours(profession, gln);
    }
    
    @Lorsque("^la sequence de référence de la demande est initialisée$")
    public void init_sequence_ref() {
        getDemandeAutorisationSteps().resetReferenceSequence();
    }

    // ********************************************************* Then

    @Alors("^le système Demaut crée la demande avec les caractéristiques \\[état: \"([^\"]*)\", utilisateur: \"([^\"]*)\", type: \"([^\"]*)\"\\]$")
    public void le_système_Demaut_crée_la_demande_avec_les_caractéristiques_état_utilisateur_type(
            StatutDemandeAutorisation statut, @Transform(LoginConverter.class) Login login, Profession profession)
                    throws Throwable {
        getDemandeAutorisationSteps().verifieAcceptationAnnexe(AccepteOuRefuse.accepte);
        getDemandeAutorisationSteps().verifieDemandeCree(profession, statut, login);
    }

    @Alors("^le système Demaut \"(accepte|refuse)\" de créer la demande$")
    public void le_système_Demaut_refuse_de_créer_la_demande(AccepteOuRefuse action) {
        getDemandeAutorisationSteps().verifieAcceptationAnnexe(action);
    }

    @Alors("^cette demande a la référence \"([^\"]*)\"$")
    public void verifier_reference(@Transform(ReferenceDeDemandeConverter.class) ReferenceDeDemande refDemande) {
        getDemandeAutorisationSteps().verifierReferenceDeDemande(refDemande);
    }

}
