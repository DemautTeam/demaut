package ch.vd.demaut.commons.fk;

import ch.vd.demaut.commons.entities.EntityAvecOrdreFK;
import ch.vd.demaut.commons.vo.OrdreVO;

/**
 * Clée fonctionnelle basée sur {@link OrdreVO}
 *
 * @param <T>
 */
abstract public class OrdreFK<T extends EntityAvecOrdreFK> extends FunctionalKeyAbstract<T> {

    private OrdreVO ordre;

    public OrdreFK(T entity) {
        this.ordre = entity.getOrdre();
    }

    public OrdreVO getOrdre() {
        return ordre;
    }
}
