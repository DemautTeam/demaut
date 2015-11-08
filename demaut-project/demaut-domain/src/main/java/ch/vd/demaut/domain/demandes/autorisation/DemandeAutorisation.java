package ch.vd.demaut.domain.demandes.autorisation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.constraints.NotNull;

import ch.vd.demaut.commons.annotations.Aggregate;
import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.AnnexeFK;
import ch.vd.demaut.domain.annexes.AnnexeMetadata;
import ch.vd.demaut.domain.annexes.AnnexeValidateur;
import ch.vd.demaut.domain.annexes.ContenuAnnexe;
import ch.vd.demaut.domain.annexes.CriteresAnnexeObligatoire;
import ch.vd.demaut.domain.annexes.ListeDesAnnexes;
import ch.vd.demaut.domain.annexes.MoteurReglesPourAnnexesObligatoires;
import ch.vd.demaut.domain.annexes.ProcedureAnnexe;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.demandes.DateDeCreation;
import ch.vd.demaut.domain.demandes.Demande;
import ch.vd.demaut.domain.demandes.DemandeFK;
import ch.vd.demaut.domain.demandes.donneesProf.activites.ActiviteFutureValidateur;
import ch.vd.demaut.domain.demandeur.donneesPerso.DonneesPersonnelles;
import ch.vd.demaut.domain.demandeur.donneesPerso.DonneesPersonnellesValidateur;
import ch.vd.demaut.domain.demandeur.donneesProf.DonneesProfessionnelles;
import ch.vd.demaut.domain.demandeur.donneesProf.DonneesProfessionnellesValidateur;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.ActiviteAnterieure;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.ActiviteFuture;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.ListeDesActivitesAnterieures;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.ListeDesActivitesFutures;
import ch.vd.demaut.domain.utilisateurs.Login;

/**
 * Demande d'autorisation associée à un utilisateur <br>
 */
@Aggregate
public class DemandeAutorisation extends Demande {

    private static final AnnexeValidateur ANNEXE_VALIDATEUR = new AnnexeValidateur();

    private static final DonneesPersonnellesValidateur DONNEES_PERSONNELLES_VALIDATEUR = new DonneesPersonnellesValidateur();

    private static final DonneesProfessionnellesValidateur DONNEES_PROFESSIONNELLES_VALIDATEUR = new DonneesProfessionnellesValidateur();

    private static final ActiviteFutureValidateur ACTIVITE_FUTURE_VALIDATEUR = new ActiviteFutureValidateur();

    // ********************************************************* Fields
    private Profession profession;

    private Login login;

    private StatutDemandeAutorisation statutDemandeAutorisation;

    private DonneesPersonnelles donneesPersonnelles;

    private DonneesProfessionnelles donneesProfessionnelles;

    private List<Annexe> annexes;

    // ********************************************************* Constructor

    // Used for OpenJPA only
    protected DemandeAutorisation() {
        super();
        initListes();
    }

    // Ne pas utiliser ce constructeur mais uniquement la Factory
    public DemandeAutorisation(Login login, Profession profession, DateDeCreation dateDeCreation) {
        super(dateDeCreation);
        initListes();
        this.statutDemandeAutorisation = StatutDemandeAutorisation.Brouillon;
        this.login = login;
        this.profession = profession;
    }

    private void initListes() {
        this.annexes = new ArrayList<>();
        this.donneesPersonnelles = new DonneesPersonnelles();
        this.donneesProfessionnelles = new DonneesProfessionnelles();

    }

    // ********************************************************* Business
    // Methods

    // ******* Annexes
    /**
     * Attache une annexe à la demande.
     *
     * @param annexeALier
     *            Annexe
     */
    public void validerEtAttacherAnnexe(Annexe annexeALier) {
        ANNEXE_VALIDATEUR.valider(annexeALier);
        getListeDesAnnexes().ajouterAnnexe(annexeALier);
    }

    public void supprimerUneAnnexe(AnnexeFK annexeFK) {
        getListeDesAnnexes().supprimerUneAnnexe(annexeFK);
    }

    public List<Annexe> listerLesAnnexes() {
        return getListeDesAnnexes().listerAnnexes();
    }

    /**
     * Renvoie la liste des annexeMetadatas
     */
    public Collection<AnnexeMetadata> listerLesAnnexeMetadatas() {
        return getListeDesAnnexes().listerAnnexesMetadata();
    }

    public List<TypeAnnexe> calculerTypesAnnexeObligatoires() {
        CriteresAnnexeObligatoire criteres = buildCriteresAnnexeObligatoire();
        List<TypeAnnexe> typesAnnexeObligatoires = MoteurReglesPourAnnexesObligatoires
                .calculerTypesAnnexeObligatoires(criteres);
        return typesAnnexeObligatoires;
    }

    public ContenuAnnexe extraireContenuAnnexe(AnnexeFK annexeFK) {
        return getListeDesAnnexes().extraireContenu(annexeFK);
    }

    // ******* Activites Futures

    /**
     * Ajoute une {@link ActiviteFuture} à la liste des activites futures de cette demande
     * 
     * @param activiteFuture
     *            Activite future Valide
     */
    public void validerEtAjouterActiviteFuture(ActiviteFuture activiteFuture) {
        //0- Generer ordre au sein de ListeDesActivitesFutures
        activiteFuture.genererOrdre(getActivitesFutures());
        
        //1- Valider activiteFuture
        ACTIVITE_FUTURE_VALIDATEUR.valider(activiteFuture);
        
        //2- Ajouter activiteFuture
        getActivitesFutures().ajouterUneActiviteFuture(activiteFuture);
    }

    public ListeDesActivitesFutures getActivitesFutures() {
        return getDonneesProfessionnelles().getListeDesActivitesFutures();
    }

    public ProcedureAnnexe calculerProcedureAnnnexe() {
        return getDonneesProfessionnelles().calculerProcedureAnnexe();
    }

    // ******* Activites Antérieures
    
    public void validerEtAjouterActiviteAnterieure(ActiviteAnterieure activiteAnterieure) {
        //0- Generer ordre au sein de ListeDesActivitesFutures
        ListeDesActivitesAnterieures activitesAnterieures = getListeDesActivitesAnterieures();
        
        activiteAnterieure.genererOrdre(activitesAnterieures);
        
        //1- Valider activiteAnterieure
        //TODO : ACTIVITE_ANTERIEURE_VALIDATEUR.valider(activiteAnterieure);
        
        //2- Ajouter activiteFuture
        activitesAnterieures.ajouterActivite(activiteAnterieure);
    }

    public ListeDesActivitesAnterieures getListeDesActivitesAnterieures() {
        return getDonneesProfessionnelles().getListeDesActivitesAnterieures();
    }
    
    
    // ******* Donnees Personnelles

    public void validerEtAttacherLesDonneesPersonnelles(DonneesPersonnelles donneesPersonnelles) {
        DONNEES_PERSONNELLES_VALIDATEUR.valider(donneesPersonnelles);
        this.donneesPersonnelles = donneesPersonnelles;
    }

    // ******* Donnees Professionnelles
    //TODO : A revoir apres le refactoring des activites futures
    public void validerDonneesProfessionnelles() {
        DONNEES_PROFESSIONNELLES_VALIDATEUR.valider(donneesProfessionnelles);
    }

    public void validerDonneesPersonnelles() {
        new DonneesPersonnellesValidateur().valider(donneesPersonnelles);
    }

    // ******* Donnees Activités Professionnelles
    public void validerActivitesProfessionnelles() {
        DONNEES_PROFESSIONNELLES_VALIDATEUR.valider(donneesProfessionnelles);
    }

    // ********************************************************* Private Methods

    private CriteresAnnexeObligatoire buildCriteresAnnexeObligatoire() {
        ProcedureAnnexe procedureAnnexe = calculerProcedureAnnnexe();
        boolean contientDiplomesEtrangers = getDonneesProfessionnelles().contientDiplomesEtrangers();
        boolean estEtranger = getDonneesPersonnelles().estEtranger();
        CriteresAnnexeObligatoire criteres = new CriteresAnnexeObligatoire(procedureAnnexe, getProfession(),
                contientDiplomesEtrangers, estEtranger);
        return criteres;
    }

    // ********************************************************* Getters

    @NotNull
    public Profession getProfession() {
        return profession;
    }

    @NotNull
    public Login getLogin() {
        return login;
    }

    @NotNull
    public StatutDemandeAutorisation getStatutDemandeAutorisation() {
        return statutDemandeAutorisation;
    }

    public ListeDesAnnexes getListeDesAnnexes() {
        return new ListeDesAnnexes(annexes);
    }

    public DonneesProfessionnelles getDonneesProfessionnelles() {
        return donneesProfessionnelles;
    }

    public DonneesPersonnelles getDonneesPersonnelles() {
        return donneesPersonnelles;
    }

    // ********************************************************* Setters

    // ********************************************************* Technical
    // methods
    @Override
    public DemandeFK<DemandeAutorisation> getFunctionalKey() {
        return new DemandeFK<DemandeAutorisation>(this);
    }

}
