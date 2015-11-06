package ch.vd.demaut.domain.demandes;

import org.joda.time.LocalDate;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;

/**
 * Référence unique d'une demande dans le système Demaut. Cette référence a une
 * visibilité client et ne correspond pas à un ID technique en base de données.
 */
@ValueObject
public class ReferenceDeDemande extends BaseValueObject {

    // ********************************************************* Fields
    private String value;

    // ********************************************************* Constructor
    //Used only for JPA
    ReferenceDeDemande() {
        
    }
    
    public ReferenceDeDemande(DateDeCreation dateDeCreation) {
        LocalDate localDate = dateDeCreation.getValue();
        this.value = localDate.toString("yyyyMM-0001");
    }

    public ReferenceDeDemande(String reference) {
        this.value = reference;
    }

    // ********************************************************* Getters

    public String getValue() {
        return value;
    }
}
