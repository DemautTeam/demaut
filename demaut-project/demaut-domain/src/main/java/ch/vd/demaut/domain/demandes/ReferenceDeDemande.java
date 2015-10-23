package ch.vd.demaut.domain.demandes;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.StringVO;

import java.util.UUID;

/**
 * Référence unique d'une demande dans le système Demaut. Cette référence a une
 * visibilité client et ne correspond pas à un ID technique en base de données.
 */
@ValueObject
public class ReferenceDeDemande extends StringVO {

    // ********************************************************* Fields

    // ********************************************************* Constructor
    public ReferenceDeDemande() {
        super(UUID.randomUUID().toString());
    }

    public ReferenceDeDemande(String reference) {
        super(reference);
    }

    // ********************************************************* Getters

}
