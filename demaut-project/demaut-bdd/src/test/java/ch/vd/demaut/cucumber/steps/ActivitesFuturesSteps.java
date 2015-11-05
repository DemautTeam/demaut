package ch.vd.demaut.cucumber.steps;

import org.joda.time.LocalDate;

import ch.vd.demaut.commons.bdd.AccepteOuRefuse;
import ch.vd.demaut.commons.exceptions.DomainException;
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

    public ActiviteFuture initActiviteFutureValide() {

        initTypeActiviteValideSiNonRenseigne();
        initEtablissement();
        initActiviteEnvisagee();

        ActiviteFuture activiteFuture = new ActiviteFuture(typeActivite, etablissement, typePratiqueLamal,
                activiteEnvisagee);
        
        return activiteFuture;
    }

    public void ajouterActiviteFutureCourante() {
        try {
            getDemandeEnCours().ajouterActiviteFuture(activiteFuture);
            accepteActiviteFuture();
        } catch (DomainException e) {
            refuseActiviteFuture();
        }
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
        etablissement = new Etablissement(voie, complement, localite, npa);
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

}
