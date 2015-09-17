package ch.vd.demaut.domain.demandeurs.donneesPerso;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.StringVO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by mourad on 14.09.15.
 */
@ValueObject
public class Localite extends StringVO {

    public Localite(String value) {
        super(value);
    }

    @NotNull
    @Size(max = 100)
    @Override
    public String getValue() {
        return super.getValue();
    }
}
