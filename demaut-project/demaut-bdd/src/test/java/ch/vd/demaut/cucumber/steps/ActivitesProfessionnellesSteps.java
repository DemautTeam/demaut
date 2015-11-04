package ch.vd.demaut.cucumber.steps;

import ch.vd.demaut.commons.bdd.AccepteOuRefuse;

import ch.vd.demaut.commons.exceptions.DomainException;
import ch.vd.demaut.domain.demandeur.Localite;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.*;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.envisagee.ActiviteEnvisagee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ch.vd.demaut.commons.bdd.AccepteOuRefuse.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ActivitesProfessionnellesSteps {

    // ********************************************************* Static fields
    private final Logger logger = LoggerFactory.getLogger(getClass());

    // ********************************************************* Fields

    private ActiviteFuture currentActivite;

    private TypeActivite typeActivite;

    private Voie voie;
    private Complement complement;
    private Localite localite;
    private NPAProfessionnel npa;
    private Etablissement etablissement;

    private AccepteOuRefuse actionActuelle;

    private DemandeAutorisationSteps demandeAutorisationSteps;

    // *********************************************** Technical Methods

    public DemandeAutorisationSteps getDemandeAutorisationSteps() {
        return demandeAutorisationSteps;
    }

    public void setDemandeAutorisationSteps(DemandeAutorisationSteps demandeAutorisationSteps) {
        this.demandeAutorisationSteps = demandeAutorisationSteps;
    }

    public void initActiviteNPA(NPAProfessionnel npa) {
        this.npa = npa;
    }

    public ActiviteFuture initActiviteDependante() {
        typeActivite = TypeActivite.Dependant;
    }

    public ActiviteFuture initActiviteInDependante() {
        typeActivite = TypeActivite.Independant;
    }

    public ActiviteFuture initActiviteFutureValide() {

        initTypeActiviteValideSiNonRenseigne();
        initEtablissement();

        ActiviteFuture activiteFuture = new ActiviteFuture(new ReferenceDeActivite(), typeActivite, etablissement, null, new ActiviteEnvisagee());
        return activiteFuture;
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

    public AccepteOuRefuse getActionActuelle() {
        assertThat(actionActuelle).isNotNull();
        return actionActuelle;
    }

    public void utilisateurValideActiviteFuture() {
        try {
            demandeAutorisationSteps.getDemandeEnCours().validerDonneesProfessionnelles();
            logger.info("donnees activités acceptées");
            actionActuelle = accepte;
        } catch (DomainException e) {
            logger.info("donnees personnelles refusées", e);
            actionActuelle = refuse;
        }
    }


    //************************************************************


}
