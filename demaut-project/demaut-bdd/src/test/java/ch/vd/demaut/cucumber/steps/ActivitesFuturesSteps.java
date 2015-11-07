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

    private TypeActivite typeActivite;
    private TypePratiqueLamal typePratiqueLamal;
    private ActiviteEnvisagee activiteEnvisagee;

    // TODO: Créer VO Adresse avec voie, complement, localite, npa
    private Nom nomEtablissement;
    private Voie voie;
    private Complement complement;
    private Localite localite;
    private NPAProfessionnel npa;
    private Telephone telephoneProf;
    private Telephone telephoneMobile;
    private Telephone fax;
    private Email email;
    private SiteInternet site;

    private Etablissement etablissement;

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

    public void setDemandeAutorisationSteps(DemandeAutorisationSteps demandeAutorisationSteps) {
        this.demandeAutorisationSteps = demandeAutorisationSteps;
    }

    public void initActiviteNPA(NPAProfessionnel npa) {
        this.npa = npa;
    }

    public void initActiviteTelephoneProf(Telephone telephoneProf) {
        this.telephoneProf = telephoneProf;
    }

    public void initActiviteTelephoneMobile(Telephone telephoneMobile) {
        this.telephoneMobile = telephoneMobile;
    }

    public void initActiviteFax(Telephone fax) {
        this.fax = fax;
    }

    public void initActiviteEmail(Email email) {
        this.email = email;
    }

    public void initActiviteNomEtablissement(Nom nomEtablissement) {
        this.nomEtablissement = nomEtablissement;
    }

    public void initActiviteVoie(Voie voie) {
        this.voie = voie;
    }

    public void initActiviteLocalite(Localite localite) {
        this.localite = localite;
    }

    public void initActiviteDependante() {
        typeActivite = TypeActivite.Dependant;
    }

    public void initActiviteInDependante() {
        typeActivite = TypeActivite.Independant;
    }

    public void initActiviteEnvisagee() {
        activiteEnvisagee = new ActiviteEnvisagee(typeActivite, new TauxActiviteEnDemiJournee(1),
                new DatePrevueDebut(new LocalDate(2015, 1, 1)), null);
    }

    public void initActiviteFutureValide() {
        initTypeActiviteValideSiNonRenseigne();
        initValideEtablissement();
        initTypePratiqueLamal();
        initActiviteEnvisagee();
    }

    public void creerActiviteFuture() {
        etablissement = new Etablissement(nomEtablissement, voie, complement, localite, npa, telephoneProf, telephoneMobile, fax, email, site);
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

    // *********************************************** Private Methods

    private void accepteActiviteFuture() {
        actualAcceptationActiviteFuture = AccepteOuRefuse.accepte;
    }

    private void refuseActiviteFuture() {
        actualAcceptationActiviteFuture = AccepteOuRefuse.refuse;
    }

    private void initValideEtablissement() {
        initNPAValideSiNonRenseigne();
        initVoieValideSiNonRenseignee();
        initLocaliteValideSiNonRenseignee();
        initTelephoneProfNonRenseignee();
        initEmailSiNonRenseignee();
        initNomEtablissementSiNonRenseignee();
    }

    private void initTypePratiqueLamal() {
        if (typePratiqueLamal == null) {
            typePratiqueLamal = TypePratiqueLamal.Non;
        }
    }

    private void initLocaliteValideSiNonRenseignee() {
        if (localite == null) {
            localite = new Localite("Lausanne");
        }
    }

    private void initTypeActiviteValideSiNonRenseigne() {
        if (typeActivite == null) {
            typeActivite = TypeActivite.Independant;
        }
    }

    private void initVoieValideSiNonRenseignee() {
        if (voie == null) {
            voie = new Voie("3");
        }
    }

    private void initNPAValideSiNonRenseigne() {
        if (npa == null) {
            npa = new NPAProfessionnel("1234");
        }
    }

    private void initTelephoneProfNonRenseignee() {
        if (telephoneProf == null) {
            telephoneProf = new Telephone("0123456");
        }
    }

    private void initEmailSiNonRenseignee() {
        if (email == null) {
            email = new Email("alice@vd.com");
        }
    }

    private void initNomEtablissementSiNonRenseignee() {
        if (nomEtablissement == null) {
            nomEtablissement = new Nom("Centre medical");
        }
    }
}
