package ch.vd.demaut.domain.demandes.autorisation;

import ch.vd.demaut.commons.annotations.Aggregate;
import ch.vd.demaut.domain.annexes.Annexe;
import ch.vd.demaut.domain.demandes.Demande;
import ch.vd.demaut.domain.demandes.DemandeFK;
import ch.vd.demaut.domain.demandeurs.Demandeur;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Aggregate
public class DemandeAutorisation extends Demande {

    // ********************************************************* Fields
    @NotNull
    //TODO: Make it final (be careful with JPA)
    private ProfessionDeLaSante professionDeLaSante;

    //TODO: Make it final (be careful with JPA)
    private Demandeur demandeur;

    @NotNull
    private StatutDemandeAutorisation statutDemandeAutorisation;

    private DateSoumissionDemande dateSoumissionDemande;

    private ListeDesAnnexes annexes = new ListeDesAnnexes();

    // ********************************************************* Constructor
    public DemandeAutorisation(Demandeur demandeur, ProfessionDeLaSante profession) {
        super();
        annexes = new ListeDesAnnexes();
        statutDemandeAutorisation = StatutDemandeAutorisation.Brouillon;
        this.demandeur = demandeur;
        this.professionDeLaSante = profession;
    }

    //TODO : Remove me. Used only for testing currently
    public DemandeAutorisation() {
        super();
        annexes = new ListeDesAnnexes();
        statutDemandeAutorisation = StatutDemandeAutorisation.Brouillon;
        this.demandeur = null;
        this.professionDeLaSante = ProfessionDeLaSante.Medecin;
    }

    // ********************************************************* Business Methods


    /**
     * Attache une annexe à la demande.
     *
     * @param annexeALier
     */
    public void attacherUneAnnexe(Annexe annexeALier) {
        annexeALier.validerContenu();
        annexes.ajouterAnnexe(annexeALier);
    }

    // ********************************************************* Private Methods

    // ********************************************************* Getters
    public Collection<Annexe> listerLesAnnexes() {
        return annexes.listerAnnexes();
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

    // ********************************************************* Technical methods
    @Override
    public DemandeFK getFunctionalKey() {
        return new DemandeFK(this);
    }

    public StatutDemandeAutorisation getStatutDemandeAutorisation() {
        return statutDemandeAutorisation;
    }

    public ListeDesAnnexes getAnnexes() {
        return annexes;
    }
}
