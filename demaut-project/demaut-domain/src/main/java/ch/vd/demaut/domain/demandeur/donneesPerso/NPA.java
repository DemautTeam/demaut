package ch.vd.demaut.domain.demandeur.donneesPerso;

import ch.vd.demaut.commons.vo.StringVO;

import javax.validation.constraints.Size;

public class NPA extends StringVO {

    public NPA(String value) {
        super(value);
    }

    @Size(max = 15)
    @Override
    public String getValue() {
        return super.getValue();
    }
}
