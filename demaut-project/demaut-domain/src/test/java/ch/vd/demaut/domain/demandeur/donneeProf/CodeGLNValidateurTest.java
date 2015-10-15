package ch.vd.demaut.domain.demandeur.donneeProf;

import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLNValidator;
import ch.vd.demaut.domain.exception.CodeGlnNonValideException;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

public class CodeGLNValidateurTest {

    private CodeGLNValidator validateur;

    private String codeGLNVide = "";
    private String codeGLNValide1 = "4719512002889";
    private String codeGLNValide2 = "7601000000125";
    private String codeGLNInvalideLongueur = StringUtils.leftPad(codeGLNValide1, CodeGLNValidator.getTailleMax() + 1, '0');
    private String codeGLNInvalide = "4719512002885";

    @Before
    public void setUp() {
        validateur = new CodeGLNValidator();
    }

    @Test
    public void codeGLNVideEstInvalide() {
        CodeGLN codeGLN = new CodeGLN(codeGLNVide);
        validerCodeGLNEtTesterInvalide(codeGLN);
    }

    @Test
    public void codeGLNInvalide() {
        CodeGLN codeGLN = new CodeGLN(codeGLNInvalide);
        validerCodeGLNEtTesterInvalide(codeGLN);
    }

    @Test
    public void codeGLNAvecTailleSuperieurAMaxEstInvalide() {
        CodeGLN codeGLN = new CodeGLN(codeGLNInvalideLongueur);
        validerCodeGLNEtTesterInvalide(codeGLN);
    }

    @Test
    public void codeGLNValide() {
        CodeGLN codeGLN = new CodeGLN(codeGLNValide1);
        validerCodeGLNValide(codeGLN);
        codeGLN = new CodeGLN(codeGLNValide2);
        validerCodeGLNValide(codeGLN);
    }

    private void validerCodeGLNValide(CodeGLN codeGLN) {
        try {
            validateur.valider(codeGLN);
        } catch (CodeGlnNonValideException e) {
            fail("Failed because exception codeGLNNonValideException was thrown", e);
        }
    }

    private void validerCodeGLNEtTesterInvalide(CodeGLN codeGLN) {
        try {
            validateur.valider(codeGLN);
            failBecauseExceptionWasNotThrown(CodeGlnNonValideException.class);
        } catch (CodeGlnNonValideException e) {
        }
    }
}
