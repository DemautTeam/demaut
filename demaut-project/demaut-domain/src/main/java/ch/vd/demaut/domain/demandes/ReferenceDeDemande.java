package ch.vd.demaut.domain.demandes;

import org.joda.time.LocalDate;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;

/**
 * Référence unique d'une demande dans le système Demaut.<br>
 * 
 * Cette référence a une visibilité client et ne correspond pas à un ID technique en base de données.
 */
@ValueObject
public class ReferenceDeDemande extends BaseValueObject {

    // ********************************************************* Fields
    private String value;

    // ********************************************************* Constructor
    // Used only for JPA
    ReferenceDeDemande() {
    }

    public ReferenceDeDemande(DateDeCreation dateDeCreation, Long sequence) {
        LocalDate localDate = dateDeCreation.getValue();
        String dateStr = localDate.toString("yyyyMM");
        String sequenceStr = String.format("%04d", sequence);
        this.value = dateStr + "-" + sequenceStr;
    }

    public ReferenceDeDemande(String reference) {
        this.value = reference;
    }

    // ********************************************************* Getters

    public String getValue() {
        return value;
    }
}
