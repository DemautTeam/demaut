package ch.vd.demaut.domain.demandes;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;

import java.util.UUID;

/**
 * Référence unique d'une demande dans le système Demaut. Cette référence a une
 * visibilité client et ne correspond pas à un ID technique en base de données.
 */
@ValueObject
public class ReferenceDeDemande extends BaseValueObject {

    // ********************************************************* Fields
    private String value;

    // ********************************************************* Constructor
    public ReferenceDeDemande() {
        this.value = UUID.randomUUID().toString();
    }

    public ReferenceDeDemande(String reference) {
        this.value = reference;
    }

    // ********************************************************* Getters

    public String getValue() {
        return value;
    }
}
