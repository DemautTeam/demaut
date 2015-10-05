package ch.vd.demaut.domain.demandeur.donneesProf;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.StringVO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@ValueObject
public class CodeGLN extends StringVO {

    // ********************************************************* Constructor

    public CodeGLN() {
    }

    public CodeGLN(String value) {
        super(value);
    }

    @NotNull
    @Size(min = 1, max = 13)
    @Pattern(regexp = ".*[^0-9].*")
    @Override
    public String getValue() {
        return super.getValue();
    }

}
