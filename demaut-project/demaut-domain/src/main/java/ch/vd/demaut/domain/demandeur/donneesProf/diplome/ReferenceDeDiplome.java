package ch.vd.demaut.domain.demandeur.donneesProf.diplome;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;

// TODO: Supprimer cette classe qui n'a aucun sens
@ValueObject
public class ReferenceDeDiplome extends BaseValueObject {

    // ********************************************************* Fields
    private String value;

    // ********************************************************* Constructor

    public ReferenceDeDiplome() {
    }

    public ReferenceDeDiplome(String value) {
        this.value = value;
    }

    // ********************************************************* Getters

    public String getValue() {
        return value;
    }
}
