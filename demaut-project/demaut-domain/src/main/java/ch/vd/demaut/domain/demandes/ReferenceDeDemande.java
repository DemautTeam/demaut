package ch.vd.demaut.domain.demandes;

import java.util.UUID;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.StringVO;

/**
 * Référence unique d'une demande dans le système Demaut. Cette référence a une
 * visibilité client et ne correspond pas à un ID technique en base de données.
 *
 */
@ValueObject
public class ReferenceDeDemande extends StringVO {

    // ********************************************************* Fields

    // ********************************************************* Constructor
    public ReferenceDeDemande() {
        super(UUID.randomUUID().toString());
    }
    
    // ********************************************************* Getters
    
}
