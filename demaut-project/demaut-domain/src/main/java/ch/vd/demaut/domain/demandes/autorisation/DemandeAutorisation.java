package ch.vd.demaut.domain.demandes.autorisation;

import ch.vd.demaut.commons.annotations.Aggregate;
import ch.vd.demaut.domain.annexes.*;
import ch.vd.demaut.domain.config.ConfigDemaut;
import ch.vd.demaut.domain.demandes.Demande;
import ch.vd.demaut.domain.demandes.DemandeFK;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandeur.donneesPerso.DonneesPersonnelles;
import ch.vd.demaut.domain.demandeur.donneesProf.DonneesProfessionnelles;
import ch.vd.demaut.domain.demandeur.donneesProf.DonnessProfessionnellesValidateur;
import ch.vd.demaut.domain.utilisateurs.Login;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Aggregate
public class DemandeAutorisation extends Demande {

    // ********************************************************* Fields
    private Profession profession;

    private Login login;

    private StatutDemandeAutorisation statutDemandeAutorisation;

    private DonneesPersonnelles donneesPersonnelles;

    private List<Annexe> annexes;

    private transient ConfigDemaut config;

    private DonneesProfessionnelles donneesProfessionnelles;

    // ********************************************************* Constructor

    //Used for OpenJPA only
    protected DemandeAutorisation() {
        super();
        this.annexes = new ArrayList<>();
    }

    //Ne pas utiliser ce constructeur mais uniquement la Factory
    public DemandeAutorisation(Login login, Profession profession, ConfigDemaut config) {
        this();
        this.referenceDeDemande = new ReferenceDeDemande();
        this.statutDemandeAutorisation = StatutDemandeAutorisation.Brouillon;
        this.login = login;
        this.profession = profession;
        this.config = config;
    }

    // ********************************************************* Business Methods

    /**
     * Attache une annexe à la demande.
     *
     * @param annexeALier Annexe
     */
    public void validerEtAttacherAnnexe(Annexe annexeALier) {
        new AnnexeValidateur().valider(annexeALier);
        getListeDesAnnexes().ajouterAnnexe(annexeALier);
    }

    public void supprimerUneAnnexeParNomFichier(NomFichier nomFichier) {
        getListeDesAnnexes().supprimerUneAnnexeParNomFichier(nomFichier);
    }

    /**
     * Retourne la liste des type d'annexe qui sont obligatoires pour la
     * complétude de cette demande
     */
    public AnnexesObligatoires extraireAnnexesObligatoiresConfigures() {
        return config.getAnnexesObligatoires(profession);
    }

    /**
     * Détermine si tous les annexes obligatoires sont remplis
     *
     * @return boolean
     */
    public boolean annexesObligatoiresCompletes() {
        AnnexesObligatoires annexesObligatoires = extraireAnnexesObligatoiresConfigures();
        for (TypeAnnexe typeAnnexe : annexesObligatoires.listerTypesAnnexe()) {
            Collection<Annexe> annexesParType = extraireAnnexesDeType(typeAnnexe);
            if (annexesParType.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public Collection<Annexe> extraireAnnexesDeType(TypeAnnexe typeAnnexe) {
        return getListeDesAnnexes().extraireAnnexesDeType(typeAnnexe);
    }

    public Collection<Annexe> listerLesAnnexes() {
        return getListeDesAnnexes().listerAnnexes();
    }


    /**
     * Renvoie la liste des annexeMetadatas
     */
    public Collection<AnnexeMetadata> listerLesAnnexeMetadatas() {
        return getListeDesAnnexes().listerAnnexesMetadata();
    }

    public AnnexeMetadata extraireAnnexeMetadata(NomFichier nomFichier) {
        return getListeDesAnnexes().extraireAnnexeMetadata(nomFichier);
    }

    public ContenuAnnexe extraireContenuAnnexe(NomFichier nomFichier) {
        return getListeDesAnnexes().extraireContenu(nomFichier);
    }

    public void validerEtAjouterDonneesProfessionnelles(DonneesProfessionnelles donneesProfessionnelles) {
        new DonnessProfessionnellesValidateur().valider(donneesProfessionnelles);
        this.donneesProfessionnelles = donneesProfessionnelles;
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

    // ********************************************************* Technical methods
    @Override
    public DemandeFK getFunctionalKey() {
        return new DemandeFK(this);
    }

}
