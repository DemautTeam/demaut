package ch.vd.demaut.domain.demandeur.donneesProf;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.StringVO;

@ValueObject
public class CodeGLN extends StringVO {

    // ********************************************************* Constructor
    public CodeGLN(String value) {
        super(value);
    }

}