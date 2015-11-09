package ch.vd.demaut.cucumber.steps;

import static org.assertj.core.api.Assertions.assertThat;

import ch.vd.demaut.domain.demandeur.Email;
import ch.vd.demaut.domain.demandeur.Telephone;
import ch.vd.demaut.domain.demandeur.donneesPerso.Nom;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.*;
import org.joda.time.LocalDate;

import ch.vd.demaut.commons.bdd.AccepteOuRefuse;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandeur.Localite;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.envisagee.ActiviteEnvisagee;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.envisagee.DatePrevueDebut;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.envisagee.TauxActiviteEnDemiJournee;
import ch.vd.demaut.domain.exception.ActiviteFutureNonValideException;

/**
 * Steps pour la "Saisie d'activités futures"
 */
public class ActivitesFuturesSteps {

    // ********************************************************* Static fields

    // ********************************************************* Temporary
    // Stateful Fields

    private DemandeAutorisation demandeEnCours;

    private ActiviteFuture activiteFuture;

    private TypePratiqueLamal typePratiqueLamal;
    private ActiviteEnvisagee activiteEnvisagee;
    private Etablissement etablissement;

    // TODO: reafctorer les VO Adresse avec voie, complement, localite, npa après mutilaiser ses champs dans une
    // classe Adresse
    private TypeActivite typeActivite;
    private Nom nomEtablissement;
    private Voie voie;
    private Complement complement;
    private Localite localite;
    private NPAProfessionnel npa;
    private Telephone telephoneProf;
    private Telephone telephoneMobile;
    private Telephone fax;
    private Email email;
    private SiteInternet site; //
    private TauxActiviteEnDemiJournee tauxActivite;
    private DatePrevueDebut datePrevue;

    private AccepteOuRefuse actualAcceptationActiviteFuture;

    // ********************************************************* Injected Fields

    private DemandeAutorisationSteps demandeAutorisationSteps;

    // *********************************************** Technical Methods

    public DemandeAutorisationSteps getDemandeAutorisationSteps() {
        return demandeAutorisationSteps;
    }

    public DemandeAutorisation getDemandeEnCours() {
        if (demandeEnCours == null) {
            this.demandeEnCours = demandeAutorisationSteps.getDemandeEnCours();
        }
        return demandeEnCours;
    }

    public void renseignerNPA(NPAProfessionnel npa) {
        this.npa = npa;
    }

    public void renseignerTelephoneProf(Telephone telephoneProf) {
        this.telephoneProf = telephoneProf;
    }

    public void renseignerTelephoneMobile(Telephone telephoneMobile) {
        this.telephoneMobile = telephoneMobile;
    }

    public void renseignerFax(Telephone fax) {
        this.fax = fax;
    }

    public void renseignerEmail(Email email) {
        this.email = email;
    }

    public void renseignerNomEtablissement(Nom nomEtablissement) {
        this.nomEtablissement = nomEtablissement;
    }

    public void renseignerVoie(Voie voie) {
        this.voie = voie;
    }

    public void renseignerLocalite(Localite localite) {
        this.localite = localite;
    }

    //TODO implementer la partie activités dependante et independante
    public void renseignerTypeDActivite(TypeActivite typeActivite) {
        this.typeActivite = typeActivite;
    }

    // ************ Initialisation Activite Future Valide

    public void initValeursValidesPourActiviteFuture() {
        typeActivite = TypeActivite.Independant;

        nomEtablissement = new Nom("Centre medical");
        npa = new NPAProfessionnel("1234");
        voie = new Voie("3");
        localite = new Localite("Lausanne");
        telephoneProf = new Telephone("0123456");
        email = new Email("admin@vd.ch");
        nomEtablissement = new Nom("CHU Lausanne");

        typePratiqueLamal = TypePratiqueLamal.Non;

        tauxActivite = new TauxActiviteEnDemiJournee(1);
        datePrevue = new DatePrevueDebut(new LocalDate(2015, 1, 1));

    }

    public void creerActiviteFuture() {
        etablissement = new Etablissement(nomEtablissement, voie, complement, localite, npa, telephoneProf, telephoneMobile, fax,
                email, site);
        activiteEnvisagee = new ActiviteEnvisagee(typeActivite, tauxActivite, datePrevue, null);
        activiteFuture = new ActiviteFuture(etablissement, typePratiqueLamal, activiteEnvisagee);
    }

    public void ajouterActiviteFutureCourante() {
        try {
            getDemandeEnCours().validerEtAjouterActiviteFuture(activiteFuture);
            accepteActiviteFuture();
        } catch (ActiviteFutureNonValideException e) {
            refuseActiviteFuture();
        }
    }

    // TODO: Mutualiser cela avec AnnexeSteps et autres
    public void verifieAcceptationActiviteFuture(AccepteOuRefuse expectedAcceptationActiviteFuture) {
        assertThat(actualAcceptationActiviteFuture).isEqualTo(expectedAcceptationActiviteFuture);
    }

    // *********************************************** Getters

    public AccepteOuRefuse getActualAcceptationActiviteFuture() {
        return actualAcceptationActiviteFuture;
    }

    // *********************************************** Setters for Injection

    public void setDemandeAutorisationSteps(DemandeAutorisationSteps demandeAutorisationSteps) {
        this.demandeAutorisationSteps = demandeAutorisationSteps;
    }

    // *********************************************** Private Methods

    private void accepteActiviteFuture() {
        actualAcceptationActiviteFuture = AccepteOuRefuse.accepte;
    }

    private void refuseActiviteFuture() {
        actualAcceptationActiviteFuture = AccepteOuRefuse.refuse;
    }

}
