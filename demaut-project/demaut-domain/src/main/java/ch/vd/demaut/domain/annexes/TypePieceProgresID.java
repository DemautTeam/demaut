package ch.vd.demaut.domain.annexes;

import javax.validation.constraints.NotNull;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;

/**
 * ID d'un {@link TypeAnnexe} dans Progres
 *
 */
@ValueObject
public class TypePieceProgresID extends BaseValueObject {

    private Integer id;
    
    public TypePieceProgresID(Integer id) {
        this.id = id;
    }
    
    @NotNull
    public Integer getId() {
        return id;
    }
}
