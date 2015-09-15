package ch.vd.demaut.domain.demandes.autorisation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.constraints.NotNull;

import ch.vd.demaut.commons.annotations.Aggregate;
import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.annexes.AnnexeMetadata;
import ch.vd.demaut.domain.annexes.AnnexeValidateur;
import ch.vd.demaut.domain.annexes.AnnexesObligatoires;
import ch.vd.demaut.domain.annexes.ListeDesAnnexes;
import ch.vd.demaut.domain.annexes.NomFichier;
import ch.vd.demaut.domain.annexes.TypeAnnexe;
import ch.vd.demaut.domain.config.ConfigDemaut;
import ch.vd.demaut.domain.demandes.Demande;
import ch.vd.demaut.domain.demandes.DemandeFK;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandeurs.Demandeur;

@Aggregate
public class DemandeAutorisation extends Demande {

    // ********************************************************* Fields
    private ProfessionDeLaSante professionDeLaSante;

    private Demandeur demandeur;

    private StatutDemandeAutorisation statutDemandeAutorisation;

    private List<Annexe> annexes;
    
    private transient ConfigDemaut config;

    // ********************************************************* Constructor

    //Used for OpenJPA only
    protected DemandeAutorisation() {
    	super();
    	this.annexes = new ArrayList<Annexe>();
    }

    //Ne pas utiliser ce constructeur mais uniquement la Factory
    DemandeAutorisation(Demandeur demandeur, ProfessionDeLaSante profession, ConfigDemaut config) {
        this();
        this.referenceDeDemande = new ReferenceDeDemande();
        this.statutDemandeAutorisation = StatutDemandeAutorisation.Brouillon;
        this.demandeur = demandeur;
        this.professionDeLaSante = profession;
        this.config = config;
    }


    // ********************************************************* Business
    // Methods

    /**
     * Attache une annexe à la demande.
     *
     * @param annexeALier
     */
    public void validerEtAttacherAnnexe(Annexe annexeALier) {
        AnnexeValidateur.getInstance().valider(annexeALier);
        getListeDesAnnexes().ajouterAnnexe(annexeALier);
    }

    /**
     * Retourne la liste des type d'annexe qui sont obligatoires pour la
     * complétude de cette demande
     */
    public AnnexesObligatoires extraireAnnexesObligatoiresConfigures() {
        return config.getAnnexesObligatoires(professionDeLaSante);
    }

    /**
     * Détermine si tous les annexes obligatoires sont remplis
     *
     * @return
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
    
    // ********************************************************* Private Methods


    // ********************************************************* Getters

    @NotNull
    public ProfessionDeLaSante getProfessionDeLaSante() {
        return professionDeLaSante;
    }

    @NotNull
    public Demandeur getDemandeur() {
        return demandeur;
    }
    
    @NotNull
    public StatutDemandeAutorisation getStatutDemandeAutorisation() {
    	return statutDemandeAutorisation;
    }
    
    public ListeDesAnnexes getListeDesAnnexes() {
    	return new ListeDesAnnexes(annexes);
    }
    
    // ********************************************************* Setters
    
    
    // ********************************************************* Technical methods
    @Override
    public DemandeFK getFunctionalKey() {
        return new DemandeFK(this);
    }
}
