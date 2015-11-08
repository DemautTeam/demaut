package ch.vd.demaut.cucumber.steps;

import static org.assertj.core.api.Assertions.assertThat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.vd.demaut.commons.bdd.AccepteOuRefuse;
import ch.vd.demaut.domain.demandes.DateDeCreation;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.DemandeAutorisationFactory;
import ch.vd.demaut.domain.demandes.autorisation.Profession;
import ch.vd.demaut.domain.demandes.autorisation.StatutDemandeAutorisation;
import ch.vd.demaut.domain.demandes.autorisation.repo.DemandeAutorisationRepository;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.ActiviteAnterieure;
import ch.vd.demaut.domain.utilisateurs.Login;
import ch.vd.demaut.domain.utilisateurs.Utilisateur;
import ch.vd.demaut.domain.utilisateurs.UtilisateurRepository;

public class DemandeAutorisationSteps {

    // ********************************************************* Static fields

    private static final Logger LOGGER = LoggerFactory.getLogger(DemandeAutorisationSteps.class);

    // ********************************************************* Injected Fields
    private UtilisateurRepository utilisateurRepository;
    private DemandeAutorisationRepository demandeAutorisationRepository;
    private DemandeAutorisationFactory demandeAutorisationFactory;
    private BackgroundSteps backgroundSteps;


    // ********************************************************* Fields
    private CodeGLN codeGlnValide = new CodeGLN("4719512002889");
    private CodeGLN codeGlnVide = null;
    private CodeGLN codeGlnInvalide = new CodeGLN("4719512002885");

    ////////// Données temporaires pour les tests (non-thread safe)
    // FIXME utiliser l'objet ThreadLocal ?
    private Utilisateur utilisateur;
    private DemandeAutorisation demandeEnCours;
    private MappingReferences mappingReferences = new MappingReferences();

    private AccepteOuRefuse acceptation;

    // ********************************************************* Methodes Initialisation

    public void initialiserUtilisateur(Login login) {
        utilisateur = new Utilisateur(login);
        utilisateurRepository.store(utilisateur);
        LOGGER.debug(
                "L'utilisateur " + login + " a été ajouté au repository avec l'id technique:" + utilisateur.getId());
    }

    public void simulerDemandeEnCours(Profession profession, ReferenceDeDemande refScenario) {
        initialiserDemandeEnCours(profession, codeGlnValide);
        ReferenceDeDemande refDomaine = getDemandeEnCours().getReferenceDeDemande();
        mappingReferences.ajouterMapping(refScenario, refDomaine);
    }

    public void initialiserDemandeEnCours(Profession profession, CodeGLN codeGln) {
        try {
            DateDeCreation dateDeCreation = new DateDeCreation(backgroundSteps.getDateHeureCourant().getLocalDate());
            demandeEnCours = demandeAutorisationFactory.initierDemandeAutorisation(utilisateur.getLogin(), profession,
                    codeGln, dateDeCreation);
            demandeAutorisationRepository.store(demandeEnCours);
            accepteInitiliserDemande();
            LOGGER.debug("La demande autorisation " + demandeEnCours + " a été ajoutée au repository avec l'id technique:"
                    + demandeEnCours.getId());
        } catch (Exception e) { // TODO: Catcher les bonnes exceptions
            refuseInitialiserDemande();
            LOGGER.debug("La demande autorisation " + demandeEnCours + " n'a pas été ajoutée au repo");
        }
    }
    
    public void resetReferenceSequence() {
        demandeAutorisationFactory.resetReferenceSequence();
    }

    // ********************************************************* Methodes d'Action
    
    public void ajouterActivitesAnterieuresADemandeEnCours(int nbActivitesAnterieures) {
        for (int n=0; n < nbActivitesAnterieures ; n++) {
            ActiviteAnterieure activiteAnterieure = new ActiviteAnterieure();
            demandeEnCours.validerEtAjouterActiviteAnterieure(activiteAnterieure);
        }
    }    

    // ********************************************************* Methodes de verification
    
    public void verifieDemandeCree(Profession profession, StatutDemandeAutorisation statut, Login login) {
        demandeAutorisationRepository.findBy(demandeEnCours.getId());
        
        assertThat(demandeEnCours.getProfession()).isEqualTo(profession);
        assertThat(demandeEnCours.getLogin()).isEqualTo(login);
        assertThat(demandeEnCours.getStatutDemandeAutorisation()).isEqualTo(statut);
    }

    public void verifieAcceptationAnnexe(AccepteOuRefuse expectedAcceptationAnnexe) {
        assertThat(acceptation).isEqualTo(expectedAcceptationAnnexe);
    }

    public void verifierReferenceDeDemande(ReferenceDeDemande refDemande) {
        assertThat(demandeEnCours.getReferenceDeDemande()).isEqualTo(refDemande);
    }
    
    // ********************************************************* Methodes utilitaires pour Scenario
    public void accepteInitiliserDemande() {
        acceptation = AccepteOuRefuse.accepte;
    }

    public void refuseInitialiserDemande() {
        acceptation = AccepteOuRefuse.refuse;
    }
    

    public DemandeAutorisation getDemandeViaReference(ReferenceDeDemande refScenario) {
        ReferenceDeDemande refDomaine = mappingReferences.trouverDomaineRef(refScenario);
        DemandeAutorisation demande = demandeAutorisationRepository.recupererDemandeParReference(refDomaine);
        return demande;
    }
    // ********************************************************* Getters

    public DemandeAutorisation getDemandeEnCours() {
        return demandeEnCours;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public CodeGLN getCodeGlnInvalide() {
        return codeGlnInvalide;
    }

    public CodeGLN getCodeGlnValide() {
        return codeGlnValide;
    }

    public CodeGLN getCodeGlnVide() {
        return codeGlnVide;
    }

    // ***************************** **************************** Setters pour injection

    public void setUtilisateurRepository(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public void setDemandeAutorisationRepository(DemandeAutorisationRepository demandeAutorisationRepository) {
        this.demandeAutorisationRepository = demandeAutorisationRepository;
    }

    public void setDemandeAutorisationFactory(DemandeAutorisationFactory demandeAutorisationFactory) {
        this.demandeAutorisationFactory = demandeAutorisationFactory;
    }
    
    public void setBackgroundSteps(BackgroundSteps backgroundSteps) {
        this.backgroundSteps = backgroundSteps;
    }

}
