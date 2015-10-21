package ch.vd.demaut.domain.demandeur.donneesProf.diplome;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.StringVO;

@ValueObject
public class ReferenceDeDiplome extends StringVO {

    // ********************************************************* Fields

    // ********************************************************* Constructor

    public ReferenceDeDiplome() {
    }

    public ReferenceDeDiplome(String reference) {
        super(reference);
    }

    // ********************************************************* Getters

}
