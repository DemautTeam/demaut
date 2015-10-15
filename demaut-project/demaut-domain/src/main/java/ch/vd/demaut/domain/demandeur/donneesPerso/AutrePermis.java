package ch.vd.demaut.domain.demandeur.donneesPerso;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.StringVO;

@ValueObject
public class AutrePermis extends StringVO {

    public AutrePermis() {
    }

    public AutrePermis(String value) {
        super(value);
    }

    @Override
    public String getValue() {
        return super.getValue();
    }
}
