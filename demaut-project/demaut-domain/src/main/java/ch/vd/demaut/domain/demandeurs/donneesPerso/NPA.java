package ch.vd.demaut.domain.demandeurs.donneesPerso;

import ch.vd.demaut.commons.vo.StringVO;

import javax.validation.constraints.Size;

/**
 * Created by mourad on 14.09.15.
 */
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
