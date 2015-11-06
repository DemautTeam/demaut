package ch.vd.demaut.cucumber.steps.definitions;

import ch.vd.demaut.cucumber.converteurs.commons.TelephoneConverter;
import ch.vd.demaut.domain.demandeur.Telephone;
import org.apache.commons.lang3.StringUtils;

import ch.vd.demaut.commons.bdd.AccepteOuRefuse;
import ch.vd.demaut.cucumber.converteurs.donneesperso.NomConverter;
import ch.vd.demaut.cucumber.converteurs.donneesperso.PrenomConverter;
import ch.vd.demaut.cucumber.steps.DonneesPersonnellesSteps;
import ch.vd.demaut.domain.demandeur.Pays;
import ch.vd.demaut.domain.demandeur.donneesPerso.Nom;
import ch.vd.demaut.domain.demandeur.donneesPerso.Permis;
import ch.vd.demaut.domain.demandeur.donneesPerso.Prenom;
import ch.vd.demaut.domain.demandeur.donneesPerso.TypePermis;
import cucumber.api.Transform;
import cucumber.api.java.fr.Alors;
import cucumber.api.java.fr.Etantdonné;
import cucumber.api.java.fr.Lorsque;

/**
 * Step definitions pour la fonctionnalité "Saisir les DonneePerso"
 */
public class SaisirDonneePersoStepDefinitions extends StepDefinitions {

    // ********************************************************* Fields
    private DonneesPersonnellesSteps donneesPersonnellesSteps;

    private Prenom prenomValid = new Prenom("Adams");


    // ********************************************************* Technical methods

    public void setDonneesPersonnellesSteps(DonneesPersonnellesSteps donneesPersonnellesSteps) {
        this.donneesPersonnellesSteps = donneesPersonnellesSteps;
    }

    // ********************************************************* Before

    // ********************************************************* Given


    @Etantdonné("^la taille maximale du nom configurée à (\\d+) caractères$")
    public void la_taille_maximale_du_nom_configurée_à_caractères(int arg1) throws Throwable {
        //TODO : Tester la valeur max de @Size(min = 1, max = 120)
    }

    // ********************************************************* When

    @Lorsque("^l´utilisateur saisit un nom d´une longueur de (\\d+) caractères$")
    public void l_utilisateur_saisit_un_nom_d_une_longueur_de_caractères(int longueurN) throws Throwable {
        String nomStr = StringUtils.repeat("*", longueurN);
        donneesPersonnellesSteps.initNomEtPrenom(new Nom(nomStr), new Prenom("prenom"));
        donneesPersonnellesSteps.attacheDonneesPersonellesALaDemandeDAutorisation();
    }    
    
    @Lorsque("^le demandeur saisit la nationalité \"([^\"]*)\" et le permis \"([^\"]*)\"$")
    public void le_demandeur_saisi_la_et_le(Pays nationalité, TypePermis typePermis) throws Throwable {
        donneesPersonnellesSteps.initNationaliteEtPermis(nationalité, new Permis(typePermis));
        donneesPersonnellesSteps.attacheDonneesPersonellesALaDemandeDAutorisation();
    }

    

    @Lorsque("^l´utilisateur saisit ses donnees personnelles: nom=\"([^\"]*)\", prenom=\"([^\"]*)\"$")
    public void l_utilisateur_saisit_ses_donnees_personnelles_nom_prenom(@Transform(NomConverter.class) Nom nom,
                                                                         @Transform(PrenomConverter.class) Prenom prenom) throws Throwable {
        donneesPersonnellesSteps.initNomEtPrenom(nom, prenom);
        donneesPersonnellesSteps.attacheDonneesPersonellesALaDemandeDAutorisation();

    }

    @Lorsque("^l´utilisateur saisit son nom \"([^\"]*)\"$")
    public void l_utilisateur_saisit_son_nom(@Transform(NomConverter.class) Nom nom) {
        donneesPersonnellesSteps.initNomEtPrenom(nom, prenomValid);
        donneesPersonnellesSteps.attacheDonneesPersonellesALaDemandeDAutorisation();
    }

    @Lorsque("^l´utilisateur saisit ses donnees personnelles: téléphone privé=\"([^\"]*)\", Téléphone Mobile=\"([^\"]*)\"$")
    public void l_utilisateur_saisit_ses_donnees_personnelles_téléphone_fixe_Téléphone_Mobile(@Transform(TelephoneConverter.class) Telephone telephonePrive,
                                                                                              @Transform(TelephoneConverter.class) Telephone telephoneMobile) {
        donneesPersonnellesSteps.initTels(telephonePrive, telephoneMobile);
        donneesPersonnellesSteps.attacheDonneesPersonellesALaDemandeDAutorisation();

    }

    // ********************************************************* Then

    @Alors("^le système Demaut \"([^\"]*)\" les données personnelles$")
    public void le_système_Demaut_les_données_personnelles(AccepteOuRefuse action) {
        AccepteOuRefuse.verifieAcceptation(action,donneesPersonnellesSteps.getActionActuelle());

    }


}
