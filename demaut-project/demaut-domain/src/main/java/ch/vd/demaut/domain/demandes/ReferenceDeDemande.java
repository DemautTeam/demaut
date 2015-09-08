package ch.vd.demaut.domain.demandes;

import ch.vd.demaut.commons.annotations.ValueObject;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@ValueObject
public class ReferenceDeDemande {

    // ********************************************************* Fields
    @NotNull
    private String reference;

    // ********************************************************* Constructor
    public ReferenceDeDemande() {
        this.reference = UUID.randomUUID().toString();
    }

    // ********************************************************* Getters
    public String getReference() {
        return reference;
    }
}
