package ch.vd.demaut.domain.demandeur.donneesProf;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.validation.AbstractDataValidateur;
import ch.vd.demaut.domain.exception.CodeGLNNonValideException;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import java.util.Set;

@ValueObject
public class CodeGLNValidator extends AbstractDataValidateur<CodeGLN> {
    // ********************************************************* Static fields
    static private final int tailleMin = 13;
    static private final int tailleMax = 13;

    // ********************************************************* Constructeur


    // ********************************************************* Méthodes métier

    public static int getTailleMin() {
        return tailleMin;
    }

    public static int getTailleMax() {
        return tailleMax;
    }

    @Override
    public void valider(CodeGLN codeGLN) {

        // 1. Valide la structure d'un codeGLN
        validerStructure(codeGLN);

        // 2. Valide taille codeGLN
        validerTaille(codeGLN);
    }

    public void validerStructure(CodeGLN codeGLN) {
        Set<ConstraintViolation<CodeGLN>> constraintViolationsResult = validateData(codeGLN);

        if (constraintViolationsResult.size() > 0) {
            throw new CodeGLNNonValideException();
        }
    }

    public void validerTaille(CodeGLN codeGLN) {
        String value = codeGLN.getValue();
        if (StringUtils.isEmpty(value)) {
            throw new CodeGLNNonValideException();
        }
        if (value.length() < getTailleMin()) {
            throw new CodeGLNNonValideException();
        }

        if (value.length() > getTailleMax()) {
            throw new CodeGLNNonValideException();
        }
    }
}
