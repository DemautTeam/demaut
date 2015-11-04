package ch.vd.demaut.domain.demandes.autorisation;

import ch.vd.demaut.commons.annotations.Aggregate;
import ch.vd.demaut.domain.annexes.*;
import ch.vd.demaut.domain.demandes.Demande;
import ch.vd.demaut.domain.demandes.DemandeFK;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandeur.donneesPerso.DonneesPersonnelles;
import ch.vd.demaut.domain.demandeur.donneesPerso.DonneesPersonnellesValidateur;
import ch.vd.demaut.domain.demandeur.donneesProf.DonneesProfessionnelles;
import ch.vd.demaut.domain.demandeur.donneesProf.DonneesProfessionnellesValidateur;
import ch.vd.demaut.domain.demandeur.donneesProf.activites.ActiviteAnterieure;
import ch.vd.demaut.domain.utilisateurs.Login;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Demande d'autorisation associée à un utilisateur <br>
 */
@Aggregate
public class DemandeAutorisation extends Demande {

    private static final AnnexeValidateur ANNEXE_VALIDATEUR = new AnnexeValidateur();

    private static final DonneesPersonnellesValidateur DONNEES_PERSONNELLES_VALIDATEUR = new DonneesPersonnellesValidateur();

    private static final DonneesProfessionnellesValidateur DONNEES_PROFESSIONNELLES_VALIDATEUR = new DonneesProfessionnellesValidateur();

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
        this.annexes = new ArrayList<>();
        this.donneesPersonnelles = new DonneesPersonnelles();
        this.donneesProfessionnelles = new DonneesProfessionnelles();
    }

    // Ne pas utiliser ce constructeur mais uniquement la Factory
    public DemandeAutorisation(Login login, Profession profession) {
        this();
        this.referenceDeDemande = new ReferenceDeDemande();
        this.statutDemandeAutorisation = StatutDemandeAutorisation.Brouillon;
        this.login = login;
        this.profession = profession;
        this.donneesPersonnelles = new DonneesPersonnelles();
        this.donneesProfessionnelles = new DonneesProfessionnelles();
    }

    // ********************************************************* Business
    // Methods

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
        List<TypeAnnexe> typesAnnexeObligatoires = MoteurReglesPourAnnexesObligatoires.calculerTypesAnnexeObligatoires(criteres);
        return typesAnnexeObligatoires;
    }

    private CriteresAnnexeObligatoire buildCriteresAnnexeObligatoire() {
        ProcedureAnnexe procedureAnnexe = calculerProcedureAnnnexe();
        boolean contientDiplomesEtrangers = getDonneesProfessionnelles().contientDiplomesEtrangers();
        boolean estEtranger = getDonneesPersonnelles().estEtranger();
        CriteresAnnexeObligatoire criteres = new CriteresAnnexeObligatoire(procedureAnnexe, getProfession(),
                contientDiplomesEtrangers, estEtranger);
        return criteres;
    }

    public ProcedureAnnexe calculerProcedureAnnnexe() {
        return getDonneesProfessionnelles().calculerProcedureAnnexe();
    }

    public ContenuAnnexe extraireContenuAnnexe(AnnexeFK annexeFK) {
        return getListeDesAnnexes().extraireContenu(annexeFK);
    }

    // ******** Donnees Personnelles

    public void validerEtAttacherLesDonneesPersonnelles(DonneesPersonnelles donneesPersonnelles) {
        DONNEES_PERSONNELLES_VALIDATEUR.valider(donneesPersonnelles);
        this.donneesPersonnelles = donneesPersonnelles;
    }

    // ******* Donnees Professionnelles
    public void validerDonneesProfessionnelles() {
        DONNEES_PROFESSIONNELLES_VALIDATEUR.valider(donneesProfessionnelles);
    }

    public void validerDonneesPersonnelles() {
        new DonneesPersonnellesValidateur().valider(donneesPersonnelles);
    }

    public void ajouterActiviteAnterieure(ActiviteAnterieure activiteAnterieure) {
        getDonneesProfessionnelles().ajouterActiviteAnterieure(activiteAnterieure);
    }

    // ******* Donnees Activités Professionnelles
    public void validerActivitesProfessionnelles() {
        DONNEES_PROFESSIONNELLES_VALIDATEUR.valider(donneesProfessionnelles);
    }



    // ********************************************************* Private Methods

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
