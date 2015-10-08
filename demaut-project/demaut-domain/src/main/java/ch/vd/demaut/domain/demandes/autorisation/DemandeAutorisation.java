package ch.vd.demaut.domain.demandes.autorisation;

import ch.vd.demaut.commons.annotations.Aggregate;
import ch.vd.demaut.domain.annexes.*;
import ch.vd.demaut.domain.demandes.Demande;
import ch.vd.demaut.domain.demandes.DemandeFK;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandeur.donneesPerso.DonneesPersonnelles;
import ch.vd.demaut.domain.demandeur.donneesProf.DonneesProfessionnelles;
import ch.vd.demaut.domain.demandeur.donneesProf.DonneesProfessionnellesValidateur;
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

    // ********************************************************* Fields
    private Profession profession;

    private Login login;

    private StatutDemandeAutorisation statutDemandeAutorisation;

    private DonneesPersonnelles donneesPersonnelles;

    private DonneesProfessionnelles donneesProfessionnelles;

    private List<Annexe> annexes;

    // ********************************************************* Constructor

    //Used for OpenJPA only
    protected DemandeAutorisation() {
        super();
        this.annexes = new ArrayList<>();
        this.donneesPersonnelles = new DonneesPersonnelles();
        this.donneesProfessionnelles = new DonneesProfessionnelles();
    }

    //Ne pas utiliser ce constructeur mais uniquement la Factory
    public DemandeAutorisation(Login login, Profession profession) {
        this();
        this.referenceDeDemande = new ReferenceDeDemande();
        this.statutDemandeAutorisation = StatutDemandeAutorisation.Brouillon;
        this.login = login;
        this.profession = profession;
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

    public void supprimerUneAnnexe(AnnexeFK annexeFK) {
        getListeDesAnnexes().supprimerUneAnnexe(annexeFK);
    }

    public Collection<Annexe> extraireAnnexesDeType(TypeAnnexe typeAnnexe) {
        return getListeDesAnnexes().extraireAnnexesDeType(typeAnnexe);
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

    public List<TypeAnnexe> listerLesTypeAnnexesObligatoires() {
        return getListeTypeAnnexesObligatoires().listerTypesAnnexe();
    }

    public ContenuAnnexe extraireContenuAnnexe(AnnexeFK annexeFK) {
        return getListeDesAnnexes().extraireContenu(annexeFK);
    }

    public void validerDonneesProfessionnelles() {
        new DonneesProfessionnellesValidateur().valider(donneesProfessionnelles);
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

    public ListeTypeAnnexesObligatoires getListeTypeAnnexesObligatoires() {
        return new ListeTypeAnnexesObligatoires();
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
    public DemandeFK<DemandeAutorisation> getFunctionalKey() {
        return new DemandeFK<DemandeAutorisation>(this);
    }

}
