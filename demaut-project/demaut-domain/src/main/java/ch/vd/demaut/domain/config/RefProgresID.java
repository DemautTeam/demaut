package ch.vd.demaut.domain.config;

import ch.vd.demaut.commons.annotations.ValueObject;
import ch.vd.demaut.commons.vo.BaseValueObject;

import javax.validation.constraints.NotNull;

/**
 * ID d'une reference dans Progres
 */
@ValueObject
public class RefProgresID extends BaseValueObject {

    private Integer id;

    public RefProgresID(Integer id) {
        this.id = id;
    }

    @NotNull
    public Integer getId() {
        return id;
    }
}
