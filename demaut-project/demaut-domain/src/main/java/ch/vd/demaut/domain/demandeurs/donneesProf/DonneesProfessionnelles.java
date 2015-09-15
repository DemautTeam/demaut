package ch.vd.demaut.domain.demandeurs.donneesProf;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;

@ValueObject
public class DonneesProfessionnelles extends BaseValueObject {

    final private CodeGLN codeGLN;

    public DonneesProfessionnelles(CodeGLN codeGLN) {
        this.codeGLN = codeGLN;
    }

    public CodeGLN getCodeGLN() {
        return codeGLN;
    }
}
