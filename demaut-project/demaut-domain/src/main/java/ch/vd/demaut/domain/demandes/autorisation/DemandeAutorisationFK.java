package ch.vd.demaut.domain.demandes.autorisation;

import ch.vd.demaut.commons.fk.FunctionalKeyAbstract;
import ch.vd.demaut.domain.demandes.Demande;
import ch.vd.demaut.domain.demandes.ReferenceDeDemande;

public class DemandeAutorisationFK extends FunctionalKeyAbstract<DemandeAutorisation> {

    // ********************************************************* Fields
    final private ReferenceDeDemande reference;

    // ********************************************************* Constructor
    public DemandeAutorisationFK(ReferenceDeDemande reference) {
        this.reference = reference;
    }

    public DemandeAutorisationFK(Demande demande) {
        this.reference = demande.getReferenceDeDemande();
    }

    // ********************************************************* Getters
    public ReferenceDeDemande getReference() {
        return reference;
    }

}
