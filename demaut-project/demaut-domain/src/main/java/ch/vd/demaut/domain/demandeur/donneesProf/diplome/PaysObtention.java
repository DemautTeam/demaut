package ch.vd.demaut.domain.demandeur.donneesProf.diplome;

import ch.vd.demaut.commons.vo.StringVO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PaysObtention extends StringVO {

    public PaysObtention() {
    }

    public PaysObtention(String value) {
        super(value);
    }

    @NotNull
    @Size(min = 1, max = 100)
    @Override
    public String getValue() {
        return super.getValue();
    }
}
