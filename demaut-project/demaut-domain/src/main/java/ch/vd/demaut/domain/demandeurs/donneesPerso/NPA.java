package ch.vd.demaut.domain.demandeurs.donneesPerso;

import ch.vd.demaut.commons.vo.BaseValueObject;

/**
 * Created by mourad on 14.09.15.
 */
public class NPA extends BaseValueObject {
    final private Short value;

    public NPA(short value){
        super();
        this.value = value;
    }

    public Short getValue() {
        return value;
    }
}
