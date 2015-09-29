package ch.vd.demaut.domain.demandes;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.fk.FunctionalKeyAbstract;

/**
 * Représente une clé fonctionnelle d'une demande
 */
@ValueObject
public class DemandeFK<E extends Demande> extends FunctionalKeyAbstract<E> {

    // ********************************************************* Fields
    final private ReferenceDeDemande reference;

    // ********************************************************* Constructor
    public DemandeFK(Demande demande) {
        this.reference = demande.getReferenceDeDemande();
    }

    public DemandeFK(ReferenceDeDemande reference) {
        this.reference = reference;
    }

    // ********************************************************* Getters
    public ReferenceDeDemande getReference() {
        return reference;
    }

}
