package ch.vd.demaut.cucumber.converteurs.donneespro;

import ch.vd.demaut.domain.demandeur.donneesProf.CodeGLN;
import cucumber.api.Transformer;

/**
 * Converter Cucumber pour le {@link CodeGLN}
 *
 */
public class CodeGLNConverter extends Transformer<CodeGLN> {

    @Override
    public CodeGLN transform(String str) {
        return new CodeGLN(str);
    }

}
