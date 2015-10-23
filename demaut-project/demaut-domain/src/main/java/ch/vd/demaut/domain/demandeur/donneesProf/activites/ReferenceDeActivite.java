package ch.vd.demaut.domain.demandeur.donneesProf.activites;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.StringVO;

@ValueObject
public class ReferenceDeActivite extends StringVO {

    // ********************************************************* Fields

    // ********************************************************* Constructor

    public ReferenceDeActivite() {
    }

    public ReferenceDeActivite(String reference) {
        super(reference);
    }

    // ********************************************************* Getters

}
