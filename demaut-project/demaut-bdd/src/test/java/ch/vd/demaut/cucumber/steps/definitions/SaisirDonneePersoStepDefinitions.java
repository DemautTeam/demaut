package ch.vd.demaut.cucumber.steps.definitions;

import ch.vd.demaut.commons.bdd.AccepteOuRefuse;
import ch.vd.demaut.cucumber.converteurs.donneesperso.NomConverter;
import ch.vd.demaut.cucumber.converteurs.donneesperso.PrenomConverter;
import ch.vd.demaut.cucumber.steps.DemandeAutorisationSteps;
import ch.vd.demaut.cucumber.steps.DonneesPersonnellesSteps;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandeur.donneesPerso.DonneesPersonnelles;
import ch.vd.demaut.domain.demandeur.donneesPerso.Nom;
import ch.vd.demaut.domain.demandeur.donneesPerso.Prenom;
import cucumber.api.Transform;
import cucumber.api.java.fr.Alors;
import cucumber.api.java.fr.Lorsque;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions pour la fonctionnalité "Saisir les DonneePerso"
 */
public class SaisirDonneePersoStepDefinitions extends StepDefinitions {

    // ********************************************************* Fields
    private DemandeAutorisationSteps demandeAutorisationSteps;

    private DonneesPersonnellesSteps donneesPersonnellesSteps;

    private Prenom prenomValid = new Prenom("Adams");


    // ********************************************************* Technical methods

    public void setDemandeAutorisationSteps(DemandeAutorisationSteps demandeAutorisationSteps) {
        this.demandeAutorisationSteps = demandeAutorisationSteps;
    }

    public void setDonneesPersonnellesSteps(DonneesPersonnellesSteps donneesPersonnellesSteps) {
        this.donneesPersonnellesSteps = donneesPersonnellesSteps;
    }

    // ********************************************************* Before

    // ********************************************************* Given


    // ********************************************************* When

    @Lorsque("^l´utilisateur saisit ses donnees personnelles: nom=\"([^\"]*)\", prenom=\"([^\"]*)\"$")
    public void l_utilisateur_saisit_ses_donnees_personnelles_nom_prenom(@Transform(NomConverter.class) Nom nom,
                                                                         @Transform(PrenomConverter.class) Prenom prenom) throws Throwable {
        DemandeAutorisation demandeAutorisation = demandeAutorisationSteps.getDemandeEnCours();
        donneesPersonnellesSteps.initDonneePersonnelles(nom, prenom);
        donneesPersonnellesSteps.attacheDonneesPersonellesALaDemandeDAutorisation();

    }

    @Lorsque("^l´utilisateur saisit son nom \"([^\"]*)\"$")
    public void l_utilisateur_saisit_son_nom(@Transform(NomConverter.class) Nom nom) throws Throwable {
        DemandeAutorisation demandeAutorisation = demandeAutorisationSteps.getDemandeEnCours();
        donneesPersonnellesSteps.initDonneePersonnelles(nom, prenomValid);
        donneesPersonnellesSteps.attacheDonneesPersonellesALaDemandeDAutorisation();
    }

    // ********************************************************* Then

    @Alors("^le système Demaut \"([^\"]*)\" les données personnelles$")
    public void le_système_Demaut_les_données_personnelles(AccepteOuRefuse action) throws Throwable {
        assertThat(donneesPersonnellesSteps.getActionActuelle()).isNotNull();
        AccepteOuRefuse.verifieAcceptation(action,donneesPersonnellesSteps.getActionActuelle());

    }


}
