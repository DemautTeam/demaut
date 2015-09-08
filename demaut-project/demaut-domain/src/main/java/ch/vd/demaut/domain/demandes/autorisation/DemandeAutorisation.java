package ch.vd.demaut.domain.demandes.autorisation;

import ch.vd.demaut.commons.annotations.Aggregate;
import ch.vd.demaut.domain.annexes.*;
import ch.vd.demaut.domain.config.ConfigDemaut;
import ch.vd.demaut.domain.demandes.Demande;
import ch.vd.demaut.domain.demandes.DemandeFK;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;
import ch.vd.demaut.domain.demandeurs.Demandeur;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Aggregate
public class DemandeAutorisation extends Demande {

    // ********************************************************* Fields
    @NotNull
    // TODO: Make it final (be careful with JPA)
    private ProfessionDeLaSante professionDeLaSante;

    // TODO: Make it final (be careful with JPA)
    private Demandeur demandeur;

    private transient ConfigDemaut config;

    @NotNull
    private StatutDemandeAutorisation statutDemandeAutorisation;

    private DateSoumissionDemande dateSoumissionDemande;

    private ListeDesAnnexes listeDesAnnexes = new ListeDesAnnexes();

    // ********************************************************* Constructor
    //TODO: Remove me...but needs to refactor test and tests jpa
    public DemandeAutorisation() {
        this(null, ProfessionDeLaSante.Medecin, null);
    }

    public DemandeAutorisation(Demandeur demandeur, ProfessionDeLaSante profession, ConfigDemaut config) {
        super();
        this.referenceDeDemande = new ReferenceDeDemande();
        this.listeDesAnnexes = new ListeDesAnnexes();
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
    public void attacherUneAnnexe(Annexe annexeALier) {
        AnnexeValidateur.getInstance().valider(annexeALier);
        listeDesAnnexes.ajouterAnnexe(annexeALier);
    }

    public Annexe afficherUneAnnexe(String annexeFileName) {
        return listeDesAnnexes.afficherUneAnnexe(annexeFileName);
    }

    public boolean supprimerUneAnnexe(final String supprimerUneAnnexe) {
        return listeDesAnnexes.supprimerUneAnnexe(supprimerUneAnnexe);
    }

    /**
     * Retourne la liste des type d'annexe qui sont obligatoires pour la
     * complétude de cette demande
     */
    public AnnexesObligatoires getAnnexesObligatoires() {
        return config.getAnnexesObligatoires(professionDeLaSante);
    }

    /**
     * Détermine si tous les aannexes obligatoires sont remplis
     *
     * @return
     */
    public boolean annexesObligatoiresCompletes() {
        AnnexesObligatoires annexesObligatoires = getAnnexesObligatoires();
        for (TypeAnnexe typeAnnexe : annexesObligatoires.listerTypesAnnexe()) {
            Collection<Annexe> annexesParType = listeDesAnnexes.extraireAnnexesDeType(typeAnnexe);
            if (annexesParType.isEmpty()) {
                return false;
            }
        }
        return true;
    }


    // ********************************************************* Private Methods

    // ********************************************************* Getters
    public Collection<Annexe> listerLesAnnexes() {
        return listeDesAnnexes.listerAnnexes();
    }

    public ProfessionDeLaSante getProfessionDeLaSante() {
        return professionDeLaSante;
    }

    public DateSoumissionDemande getDateSoumissionDemande() {
        return dateSoumissionDemande;
    }

    public Demandeur getDemandeur() {
        return demandeur;
    }

    // ********************************************************* Technical
    // methods
    @Override
    public DemandeFK getFunctionalKey() {
        return new DemandeFK(this);
    }

    public StatutDemandeAutorisation getStatutDemandeAutorisation() {
        return statutDemandeAutorisation;
    }

    public ListeDesAnnexes getListeDesAnnexes() {
        return listeDesAnnexes;
    }
}
