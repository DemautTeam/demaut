package ch.vd.demaut.cucumber.steps;

import static org.assertj.core.api.Assertions.assertThat;

import ch.vd.demaut.domain.demandeur.Fax;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.etablissement.TelephoneMobile;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.etablissement.TelephonePrive;
import org.joda.time.LocalDate;

import ch.vd.demaut.commons.bdd.AccepteOuRefuse;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandeur.Localite;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.ActiviteFuture;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.Complement;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.Etablissement;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.NPAProfessionnel;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.TypeActivite;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.TypePratiqueLamal;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.Voie;
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
    private Voie voie;
    private Complement complement;
    private Localite localite;
    private NPAProfessionnel npa;
    private TelephonePrive telephonePrive;
    private TelephoneMobile telephoneMobile;

    private Etablissement etablissement;

    private AccepteOuRefuse actualAcceptationActiviteFuture;

    // ********************************************************* Injected Fields

    private DemandeAutorisationSteps demandeAutorisationSteps;
    private Fax fax;

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

    public void initActiviteTelephoneProf(TelephonePrive telephonePrive) {
        this.telephonePrive = telephonePrive;
    }

    public void initActiviteTelephoneMobile(TelephoneMobile telephoneMobile) {
        this.telephoneMobile = telephoneMobile;
    }

    public void initActiviteFax(Fax fax) {
        this.fax = fax;
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
        initEtablissement();
        initTypePratiqueLamal();
        initActiviteEnvisagee();

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

    private void initEtablissement() {
        initNPAValideSiNonRenseigne();
        initVoieValideSiNonRenseignee();
        initLocaliteValideSiNonRenseignee();
        initTelephoneProfNonRenseignee();
//        initTelephoneMobileNonRenseignee();
        etablissement = new Etablissement(voie, complement, localite, npa, telephonePrive, null, fax);
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
        if(telephonePrive == null){
            telephonePrive = new TelephonePrive("0123456");
        }
    }

    private void initTelephoneMobileNonRenseignee() {
        if(telephoneMobile == null){
            telephoneMobile = new TelephoneMobile("0123456");
        }
    }

}
